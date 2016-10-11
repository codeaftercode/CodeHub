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
		saveAsMenuItem.addActionListener(fileModularActionListener);
		saveAllMenuItem.addActionListener(fileModularActionListener);
		closeFileMenuItem.addActionListener(fileModularActionListener);
		closeAllFileMenuItem.addActionListener(fileModularActionListener);
		exitMenuItem.addActionListener(fileModularActionListener);
	}

	/**
	 * 新建纯文本文件
	 */
	protected void newText() {
		// 选项卡显示的文件名称
		window.getWorkplace().getsTabbedPane().newScrollTextPane();
	}

	/**
	 * 打开文件
	 * 调用系统文件选择器选择文件,根据文件类型调用对应的方法打开文件
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
		// 退出前关闭所有文件
		closeAllFile();
		// 如果仍有文件未关闭,则放弃退出操作
		if(window.getWorkplace().getsTabbedPane().getTabCount() > 0) {
			return;
		}
		
		// 等待其他线程(如IO)结束后再退出
		System.exit(0);
	}
}
