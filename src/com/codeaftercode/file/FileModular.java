package com.codeaftercode.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.codeaftercode.ui.LineNumberHeaderView;
import com.codeaftercode.ui.MyJMenu;
import com.codeaftercode.ui.MyJMenuItem;
import com.codeaftercode.ui.STabbedPane;
import com.codeaftercode.ui.Window;
import com.codeaftercode.workplace.FileTreePanel;
import com.codeaftercode.workplace.ViewGroups;

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
		//注册监听
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		//初始化菜单
		filesMenu=new MyJMenu("文件(F)");
		
		newFileMenu=new MyJMenu("新建(N)",new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("普通纯文本文件",new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("打开文件(O)...");
		openFolderMenuItem=new MyJMenuItem("打开文件夹...");
		openRecentMenu=new MyJMenu("Open recent...");
		saveMenuItem=new MyJMenuItem("保存(S)");
		saveWithEncodingMenuItem=new MyJMenuItem("Save with Encoding");
		saveAsMenuItem=new MyJMenuItem("另存为(A)...");
		saveAllMenuItem=new MyJMenuItem("全部保存");
		closeFileMenuItem = new MyJMenuItem("关闭");
		closeAllFileMenuItem = new MyJMenuItem("全部关闭");
		exitMenuItem=new MyJMenuItem("退出(X)");

		//助记符
		filesMenu.setMnemonic('F');
		
		//菜单绑定菜单项
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
		

		//向监听器注册事件
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
		
		//向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(filesMenu);
		
	}
	
	protected void newText() {
		//新建纯文本文件					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		//选项卡显示的文件名称
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "新的文档" + window.getNewFileCounter() + ".txt";
		//新建选项卡
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		//显示行号
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());
	}
	
	protected void openFile() throws IOException {
		// 打开文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// 取消打开操作
		if(file == null) {
			return;
		}
		
		String title = file.getName();
		// 处理.txt文件
		if(file.isFile() && title.toLowerCase().endsWith(".txt")) {
			JTextArea jTextArea = new JTextArea();
			jTextArea.setFont(window.getFont());
			JScrollPane jScrollPane = new JScrollPane(jTextArea);
			window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
			// 切换到新选项卡
			window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
			// 显示行号--考虑创建JScrollPane的子类,把显示行号功能集成进去,就不用总这样调用方法显示等号
			jScrollPane.setRowHeaderView(new LineNumberHeaderView());
			openTxt(file, jTextArea);
			return;
		}
		
		// 处理非.txt文件
		// 创建文本区
		JTextPane jTextPane = new JTextPane();
		// 设置文本区字体
		jTextPane.setFont(window.getFont());
		// 创建滚动面板
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		// 添加选项卡
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		// 切换到新选项卡
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		// 显示行号--考虑创建JScrollPane的子类,把显示行号功能集成进去,就不用总这样调用方法显示等号
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());

		// 读取文件
		if(file.isFile() && title.toLowerCase().endsWith(".java")) {
			openJava(file, jTextPane);
			return;
		}
	}
	
	public void openTxt(File file, JTextArea jTextArea) throws FileNotFoundException, IOException {
		// 打开.txt文件
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			br.read(cbuf);
			jTextArea.replaceSelection(new String(cbuf,0,cbuf.length));
			// 设置光标停留位置
			jTextArea.setCaretPosition(0);
		}
	}
	
	public void openJava(File file, JTextPane jTextPane) throws FileNotFoundException, IOException {
		// 打开.java文件
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			br.read(cbuf);
			jTextPane.replaceSelection(new String(cbuf,0,cbuf.length));
			// 设置光标停留位置
			jTextPane.setCaretPosition(0);
		}
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
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();
		
		// 取消打开操作
		if(folder == null || !folder.isDirectory()) {
			return;
		}
		//test:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());
		
		/*ViewGroups viewGroups = window.getWorkplace().getViewGroups();
		if(viewGroups.getTabCount() > 0) {
			// 已存在打开的文件夹
			viewGroups.removeAll();
			viewGroups.addTab(file.getName(), null, new FileTreePanel(window, file), null, true, window.getViewsModular().getShowLeftCheckBoxMenuItem());
		} else {
			// 不存在已打开的文件夹
			viewGroups.addTab(file.getName(), null, new FileTreePanel(window, file), null, true, window.getViewsModular().getShowLeftCheckBoxMenuItem());
		}
		window.getViewsModular().getShowLeftCheckBoxMenuItem().setState(true);
		window.getWorkplace().showViewGroups();
		
		// 显示文件夹
		window.getWorkplace().setLeftPanel(new FileTreePanel(window));*/
		
		// 打开并显示文件夹
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}	
}
