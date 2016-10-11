package com.codeaftercode.file;

import javax.swing.ImageIcon;

import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.MyJMenuItem;
import com.codeaftercode.common.Window;
import com.codeaftercode.workplace.STabbedPane;

public class FileModular {
	private Window window;
	private MyJMenu filesMenu;
	private MyJMenu newFileMenu;
	protected MyJMenuItem newTextMenuItem;
	protected MyJMenuItem openMenuItem;
	protected MyJMenuItem openFolderMenuItem;
	protected MyJMenu openRecentMenu;
	protected MyJMenuItem saveMenuItem;
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
		saveAsMenuItem.addActionListener(fileModularActionListener);
		saveAllMenuItem.addActionListener(fileModularActionListener);
		closeFileMenuItem.addActionListener(fileModularActionListener);
		closeAllFileMenuItem.addActionListener(fileModularActionListener);
		exitMenuItem.addActionListener(fileModularActionListener);
	}

	/**
	 * �½����ı��ļ�
	 */
	protected void newText() {
		// ѡ���ʾ���ļ�����
		window.getWorkplace().getsTabbedPane().newScrollTextPane();
	}

	/**
	 * ���ļ�
	 * ����ϵͳ�ļ�ѡ����ѡ���ļ�,�����ļ����͵��ö�Ӧ�ķ������ļ�
	 */
	protected void openFile(){
		window.getWorkplace().getsTabbedPane().openFile();
	}

	public MyJMenu getFilesMenu() {
		return filesMenu;
	}

	public void openFolder() {
		window.getWorkplace().getViewGroups().openFolder();
	}

	public void saveFile() {
		window.getWorkplace().getsTabbedPane().saveSelectedTab();
	}

	public void saveAs() {
		window.getWorkplace().getsTabbedPane().saveSelectedTabAs();
	}
	
	public void savaAll() {
		window.getWorkplace().getsTabbedPane().saveAllTab();
	}
	
	public void closeFile() {
		STabbedPane sTabbedPane = window.getWorkplace().getsTabbedPane();
		sTabbedPane.removeTabAt(sTabbedPane.getSelectedIndex());
	}

	public void closeAllFile() {
		window.getWorkplace().getsTabbedPane().removeAll();
	}

	public void exit() {
		// �˳�ǰ�ر������ļ�
		closeAllFile();
		// ��������ļ�δ�ر�,������˳�����
		if(window.getWorkplace().getsTabbedPane().getTabCount() > 0) {
			return;
		}
		
		// �ȴ������߳�(��IO)���������˳�
		System.exit(0);
	}
}
