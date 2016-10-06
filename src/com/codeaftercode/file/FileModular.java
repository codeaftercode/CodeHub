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
		// ע�����
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		// ��ʼ���˵�
		filesMenu = new MyJMenu("�ļ�(F)");

		newFileMenu = new MyJMenu("�½�(N)", new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("��ͨ���ı��ļ�", new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem = new MyJMenuItem("���ļ�(O)...");
		openFolderMenuItem = new MyJMenuItem("���ļ���...");
		openRecentMenu = new MyJMenu("Open recent...");
		saveMenuItem = new MyJMenuItem("����(S)");
		saveWithEncodingMenuItem = new MyJMenuItem("Save with Encoding");
		saveAsMenuItem = new MyJMenuItem("���Ϊ(A)...");
		saveAllMenuItem = new MyJMenuItem("ȫ������");
		closeFileMenuItem = new MyJMenuItem("�ر�");
		closeAllFileMenuItem = new MyJMenuItem("ȫ���ر�");
		exitMenuItem = new MyJMenuItem("�˳�(X)");

		// ���Ƿ�
		filesMenu.setMnemonic('F');

		// �˵��󶨲˵���
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

		// �������ע���¼�
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

		// �����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(filesMenu);

	}

	/**
	 * �½����ı��ļ�
	 */
	protected void newText() {
		// ѡ���ʾ���ļ�����
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "�µ��ĵ�" + window.getNewFileCounter() + ".txt";
		//ScrollTextPane scrollTextPane = new ScrollTextPane(new File(Global.currentFolder + title));
		ScrollTextPane scrollTextPane = new ScrollTextPane(null);
		// �½�ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, scrollTextPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(scrollTextPane);
	}


	/**
	 * ���ļ�
	 * ����ϵͳ�ļ�ѡ����ѡ���ļ�,�����ļ����͵��ö�Ӧ�ķ������ļ�
	 */
	protected void openFile(){
		// ѡ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// ȡ���򿪲���
		if (file == null || !file.isFile()) {
			return;
		}
		
		// ѡ����ļ��Ѿ���
		// ���window.getWorkplace().getsTabbedPane()����û������ļ�,�����,��Ҫ�ظ���,ֻ���л�����ѡ�����
		STabbedPane sTabbedPane = window.getWorkplace().getsTabbedPane();
		int count = sTabbedPane.getComponentCount();
		for(int i = 0;i < count;i++) {
			ScrollTextPane scrollTextPane =(ScrollTextPane)sTabbedPane.getComponentAt(i);
			if(scrollTextPane.getFile() != null && scrollTextPane.getFile().equals(file)) {
				sTabbedPane.setSelectedComponent(scrollTextPane);
				return;
			}
		}
		
		// �����ı���ʾ����,��ȡ�ļ��ɸ���������
		//ScrollTextPane scrollTextPane = new ScrollTextPane(file, fileType, Global.textFont);
		ScrollTextPane scrollTextPane = new ScrollTextPane(file);
		// ����ѡ�,�л�����ѡ�
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
		// ���ļ���
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();

		// ȡ���򿪲���
		if (folder == null || !folder.isDirectory()) {
			return;
		}

		// ״̬��:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());

		// �򿪲���ʾ�ļ���
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}

	public void saveFile() {
		// �����ļ�
		
	}

	public void saveAs() {
		// �ļ����Ϊ
		// д��׼��:�ַ���,����
		ScrollTextPane scrollTextPane =(ScrollTextPane)window.getWorkplace().getsTabbedPane().getSelectedComponent();
		if(scrollTextPane == null) {
			return;
		}
		String text = scrollTextPane.getTextPane().getText();
		String charset = scrollTextPane.getCharset();
		// д��׼��:ȷ���ļ�·��,�ļ���
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Global.currentFolder));
		String fileName = scrollTextPane.getFile() == null?"�½��ļ�":scrollTextPane.getFile().getName();
		jfc.setSelectedFile(new File(fileName));
		jfc.setDialogTitle("�����ļ�");
		jfc.setMultiSelectionEnabled(false);
		int result=jfc.showDialog(window, "�����ļ�");
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
		}
		// д���ļ�
		
	}
}
