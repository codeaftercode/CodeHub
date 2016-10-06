package com.codeaftercode.file;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.codeaftercode.common.Global;
import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.MyJMenuItem;
import com.codeaftercode.common.Window;
import com.codeaftercode.workplace.STabbedPane;
import com.codeaftercode.workplace.ScrollTextPane;

public class FileModular {
	private Window window;
	private MyJMenu filesMenu;
	private MyJMenu newFileMenu;
	protected MyJMenuItem newTextMenuItem;
	protected MyJMenuItem openMenuItem;
	protected MyJMenuItem openFolderMenuItem;
	protected MyJMenu openRecentMenu;
	protected MyJMenuItem saveMenuItem;
	protected MyJMenuItem saveWithEncodingMenuItem;
	protected MyJMenuItem saveAsMenuItem;
	protected MyJMenuItem saveAllMenuItem;
	protected MyJMenuItem closeFileMenuItem;
	protected MyJMenuItem closeAllFileMenuItem;
	protected MyJMenuItem exitMenuItem;

	public FileModular(Window window) {
		this.window = window;
		// 注册监听
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		// 初始化菜单
		filesMenu = new MyJMenu("文件(F)");

		newFileMenu = new MyJMenu("新建(N)", new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("普通纯文本文件", new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem = new MyJMenuItem("打开文件(O)...");
		openFolderMenuItem = new MyJMenuItem("打开文件夹...");
		openRecentMenu = new MyJMenu("Open recent...");
		saveMenuItem = new MyJMenuItem("保存(S)");
		saveWithEncodingMenuItem = new MyJMenuItem("Save with Encoding");
		saveAsMenuItem = new MyJMenuItem("另存为(A)...");
		saveAllMenuItem = new MyJMenuItem("全部保存");
		closeFileMenuItem = new MyJMenuItem("关闭");
		closeAllFileMenuItem = new MyJMenuItem("全部关闭");
		exitMenuItem = new MyJMenuItem("退出(X)");

		// 助记符
		filesMenu.setMnemonic('F');

		// 菜单绑定菜单项
		filesMenu.add(newFileMenu);
		newFileMenu.add(newTextMenuItem);
		filesMenu.add(openMenuItem);
		filesMenu.add(openFolderMenuItem);
		filesMenu.add(openRecentMenu);
		filesMenu.add(saveMenuItem);
		filesMenu.add(saveWithEncodingMenuItem);
		filesMenu.add(saveAsMenuItem);
		filesMenu.add(saveAllMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(closeFileMenuItem);
		filesMenu.add(closeAllFileMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(exitMenuItem);

		// 向监听器注册事件
		newTextMenuItem.addActionListener(fileModularActionListener);
		openMenuItem.addActionListener(fileModularActionListener);
		openFolderMenuItem.addActionListener(fileModularActionListener);
		saveMenuItem.addActionListener(fileModularActionListener);
		saveWithEncodingMenuItem.addActionListener(fileModularActionListener);
		saveAsMenuItem.addActionListener(fileModularActionListener);
		saveAllMenuItem.addActionListener(fileModularActionListener);
		closeFileMenuItem.addActionListener(fileModularActionListener);
		closeAllFileMenuItem.addActionListener(fileModularActionListener);
		exitMenuItem.addActionListener(fileModularActionListener);

		// 向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(filesMenu);

	}

	/**
	 * 新建纯文本文件
	 */
	protected void newText() {
		// 选项卡显示的文件名称
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "新的文档" + window.getNewFileCounter() + ".txt";
		//ScrollTextPane scrollTextPane = new ScrollTextPane(new File(Global.currentFolder + title));
		ScrollTextPane scrollTextPane = new ScrollTextPane(null);
		// 新建选项卡
		window.getWorkplace().getsTabbedPane().addTab(title, null, scrollTextPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(scrollTextPane);
	}


	/**
	 * 打开文件
	 * 调用系统文件选择器选择文件,根据文件类型调用对应的方法打开文件
	 */
	protected void openFile(){
		// 选择文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// 取消打开操作
		if (file == null || !file.isFile()) {
			return;
		}
		
		// 选择的文件已经打开
		// 检测window.getWorkplace().getsTabbedPane()中有没有这个文件,如果有,则不要重复打开,只需切换到此选项卡即可
		STabbedPane sTabbedPane = window.getWorkplace().getsTabbedPane();
		int count = sTabbedPane.getComponentCount();
		for(int i = 0;i < count;i++) {
			ScrollTextPane scrollTextPane =(ScrollTextPane)sTabbedPane.getComponentAt(i);
			if(scrollTextPane.getFile() != null && scrollTextPane.getFile().equals(file)) {
				sTabbedPane.setSelectedComponent(scrollTextPane);
				return;
			}
		}
		
		// 创建文本显示容器,读取文件由该容器负责
		//ScrollTextPane scrollTextPane = new ScrollTextPane(file, fileType, Global.textFont);
		ScrollTextPane scrollTextPane = new ScrollTextPane(file);
		// 配置选项卡,切换到新选项卡
		sTabbedPane.addTab(file.getName(), null, scrollTextPane, file.getName(), true);
		sTabbedPane.setSelectedComponent(scrollTextPane);
		
	}

	public MyJMenu getFilesMenu() {
		return filesMenu;
	}

	public void setFilesMenu(MyJMenu filesMenu) {
		this.filesMenu = filesMenu;
	}

	public void openFolder() {
		// 打开文件夹
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();

		// 取消打开操作
		if (folder == null || !folder.isDirectory()) {
			return;
		}

		// 状态栏:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());

		// 打开并显示文件夹
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}

	public void saveFile() {
		// 保存文件
		
	}

	public void saveAs() {
		// 文件另存为
		// 写入准备:字符串,编码
		ScrollTextPane scrollTextPane =(ScrollTextPane)window.getWorkplace().getsTabbedPane().getSelectedComponent();
		if(scrollTextPane == null) {
			return;
		}
		String text = scrollTextPane.getTextPane().getText();
		String charset = scrollTextPane.getCharset();
		// 写入准备:确定文件路径,文件名
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Global.currentFolder));
		String fileName = scrollTextPane.getFile() == null?"新建文件":scrollTextPane.getFile().getName();
		jfc.setSelectedFile(new File(fileName));
		jfc.setDialogTitle("保存文件");
		jfc.setMultiSelectionEnabled(false);
		int result=jfc.showDialog(window, "保存文件");
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
		}
		// 写入文件
		
	}
}
