package com.codeaftercode.workplace;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import com.codeaftercode.common.Window;

/**
 * @author Kirill Grouchnikov
 */
public class FileTreePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	* File system view.
	*/
	protected static FileSystemView fsv = FileSystemView.getFileSystemView();

	/**
	* Renderer for the file tree.
	* 
	* @author Kirill Grouchnikov
	*/
	private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		* Icon cache to speed the rendering.
		*/
		private Map<String, Icon> iconCache = new HashMap<String, Icon>();

		/**
		* Root name cache to speed the rendering.
		*/
		private Map<File, String> rootNameCache = new HashMap<File, String>();

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
		*      java.lang.Object, boolean, boolean, boolean, int, boolean)
		*/
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			FileTreeNode ftn = (FileTreeNode) value;
			File file = ftn.file;
			String filename = "";
			if (file != null) {
				if (ftn.isFileSystemRoot) {
					filename = this.rootNameCache.get(file);
					if (filename == null) {
						filename = fsv.getSystemDisplayName(file);
						this.rootNameCache.put(file, filename);
					}
				} else {
					filename = file.getName();
				}
			}
			/*if(file != null) {
				filename = file.getName();
			}*/
			JLabel result = (JLabel) super.getTreeCellRendererComponent(tree,
					filename, sel, expanded, leaf, row, hasFocus);
			if (file != null) {
				Icon icon = this.iconCache.get(filename);
				if (icon == null) {
					// System.out.println("Getting icon of " + filename);
					icon = fsv.getSystemIcon(file);
					this.iconCache.put(filename, icon);
				}
				result.setIcon(icon);
			}
			return result;
		}
	}

	/**
	* A node in the file tree.
	* 
	* @author Kirill Grouchnikov
	*/
	private static class FileTreeNode implements TreeNode {
		/**
		* Node file.
		*/
		private File file;

		/**
		* Children of the node file.
		*/
		private File[] children;

		/**
		* Parent node.
		*/
		private TreeNode parent;

		/**
		* Indication whether this node corresponds to a file system root.
		*/
		private boolean isFileSystemRoot;

		/**
		* Creates a new file tree node.
		* 
		* @param file
		*            Node file
		* @param isFileSystemRoot
		*            Indicates whether the file is a file system root.
		* @param parent
		*            Parent node.
		*/
		public FileTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
			this.file = file;
			this.isFileSystemRoot = isFileSystemRoot;
			this.parent = parent;
			this.children = this.file.listFiles();
			if (this.children == null)
				this.children = new File[0];
		}

		/**
		* Creates a new file tree node.
		* 
		* @param children
		*            Children files.
		*/
		public FileTreeNode(File[] children) {
			this.file = null;
			this.parent = null;
			this.children = children;
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#children()
		*/
		public Enumeration<?> children() {
			final int elementCount = this.children.length;
			return new Enumeration<File>() {
				int count = 0;

				/*
				* (non-Javadoc)
				* 
				* @see java.util.Enumeration#hasMoreElements()
				*/
				public boolean hasMoreElements() {
					return this.count < elementCount;
				}

				/*
				* (non-Javadoc)
				* 
				* @see java.util.Enumeration#nextElement()
				*/
				public File nextElement() {
					if (this.count < elementCount) {
						return FileTreeNode.this.children[this.count++];
					}
					throw new NoSuchElementException("Vector Enumeration");
				}
			};

		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#getAllowsChildren()
		*/
		public boolean getAllowsChildren() {
			return true;
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#getChildAt(int)
		*/
		public TreeNode getChildAt(int childIndex) {
			return new FileTreeNode(this.children[childIndex],
					this.parent == null, this);
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#getChildCount()
		*/
		public int getChildCount() {
			return this.children.length;
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
		*/
		public int getIndex(TreeNode node) {
			FileTreeNode ftn = (FileTreeNode) node;
			for (int i = 0; i < this.children.length; i++) {
				if (ftn.file.equals(this.children[i]))
					return i;
			}
			return -1;
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#getParent()
		*/
		public TreeNode getParent() {
			return this.parent;
		}

		/*
		* (non-Javadoc)
		* 
		* @see javax.swing.tree.TreeNode#isLeaf()
		*/
		public boolean isLeaf() {
			return (this.getChildCount() == 0);
		}
	}

	/**
	* The file tree.
	*/
	private JTree tree;

	/**
	* Creates the file tree panel.
	*/
	/**
	* Creates the file tree panel.
	*/
	public FileTreePanel() {
		this.setLayout(new BorderLayout());

		File[] roots = File.listRoots();
		FileTreeNode rootTreeNode = new FileTreeNode(roots);
		this.tree = new JTree(rootTreeNode);
		this.tree.setCellRenderer(new FileTreeCellRenderer());
		this.tree.setRootVisible(false);
		final JScrollPane jsp = new JScrollPane(this.tree);
		jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(jsp, BorderLayout.CENTER);
	}
	
	
	public FileTreePanel(Window window) {
		this.setLayout(new BorderLayout());
		//获取系统所有硬盘根目录
		File[] roots = File.listRoots();
		FileTreeNode rootTreeNode = new FileTreeNode(roots);
		this.tree = new JTree(rootTreeNode);
		this.tree.setCellRenderer(new FileTreeCellRenderer());
		this.tree.setRootVisible(false);
		final JScrollPane jsp = new JScrollPane(this.tree);
		jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(jsp, BorderLayout.CENTER);
		
		this.tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				FileTreeNode node = (FileTreeNode) tree.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}
				// 在状态栏显示文件名
				window.getStatusBar().getLabel().setText(node.file.getName());
				/*if (node.isLeaf()) {
					
				}*/

			}
		});
	}
	
	public FileTreePanel(Window window, File root) {
		this.setLayout(new BorderLayout());
		// root为传入的根目录,若为空则设置为从系统根目录打开
		FileTreeNode rootTreeNode;
		if(root == null) {
			rootTreeNode = new FileTreeNode(File.listRoots());
		}else {
			rootTreeNode = new FileTreeNode(root, true, null);
		}
		this.tree = new JTree(rootTreeNode);
		this.tree.setCellRenderer(new FileTreeCellRenderer());
		this.tree.setRootVisible(true);
		final JScrollPane jsp = new JScrollPane(this.tree);
		jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.add(jsp, BorderLayout.CENTER);
		
		this.tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				FileTreeNode node = (FileTreeNode) tree.getLastSelectedPathComponent();

				if (node == null) {
					return;
				}
				// 在状态栏显示文件名
				if (node.isLeaf()) {
					window.getStatusBar().getLabel().setText(node.file.getName());
				}

			}
		});
	}
}
