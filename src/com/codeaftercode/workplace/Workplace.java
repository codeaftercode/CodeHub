package com.codeaftercode.workplace;

import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import com.codeaftercode.ui.MyJCheckBoxMenuItem;
import com.codeaftercode.ui.STabbedPane;
import com.codeaftercode.ui.Window;

public class Workplace {
	private Window window;
	// 文本编辑区:
	// JTextPane jTextPane;
	// JScrollPane jScrollPane;
	// 默认分栏分隔符宽度
	private int DividerSize = 5;
	// 默认垂直分栏(左右分栏)比例
	private double verticalDividerLocation = 0.8;
	// 默认水平分栏(上下分栏)比例
	private double horizontalDividerLocation = 0.2;
	
	
	private STabbedPane sTabbedPane;
	// 命令行:
	private JTextPane consoleJPanel;
	private JScrollPane consoleJScrollPane;
	// 左侧:
	private ViewGroups viewGroups; 
	// 分栏:
	private JSplitPane verticalJSplitPane;// 上下分栏,上为文本编辑区,下为命令行
	
	private JSplitPane horizontalJSplitPane;// 左右分栏,左为左侧,右为verticalJSplitPane
	
	public Workplace(Window window) {
		super();
		this.window = window;
		
		
		// 文本编辑区:
		sTabbedPane = new STabbedPane();
		// 命令行:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		// 左侧:树组件实现文件夹结构
		//leftPanel =new FileTreePanel(window,new File("D:\\Data"));
		//leftPanel = new JPanel();//new FileTreePanel(window);
		viewGroups = new ViewGroups(window);
		// 分栏:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// 上下分栏,上为文本编辑区,下为命令行
		//horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, verticalJSplitPane);// 左右分栏,左为左侧,右为jSplitPane1
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewGroups, verticalJSplitPane);// 左右分栏,左为左侧,右为jSplitPane1
		
		// 设置分栏
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		showViewGroups();
		showConsole();
	}
	
	@Override
	public String toString() {
		return "Workplace [DividerSize=" + DividerSize + ", verticalDividerLocation=" + verticalDividerLocation
				+ ", horizontalDividerLocation=" + horizontalDividerLocation + "]";
	}

	public void showConsole() {
		//显示:命令行
		if(!getConsoleJScrollPane().isVisible()) {
			//设置为命令行可见
			getConsoleJScrollPane().setVisible(true);
			//设置上下分栏比例
			getVerticalJSplitPane().setDividerLocation(getVerticalDividerLocation());
			//设置分隔线宽度
			getVerticalJSplitPane().setDividerSize(getDividerSize());
		}else if(!window.getViewsModular().getShowConsoleCheckBoxMenuItem().getState()) {
			//设置为命令行不可见
			getConsoleJScrollPane().setVisible(false);
			getVerticalJSplitPane().setDividerSize(0);
		}
	}
	
	public void showFolder() {
		showFolder(null);
	}
	public void showFolder(File folder) {
		MyJCheckBoxMenuItem showFolderCheckBoxMenuItem = window.getViewsModular().getShowFolderCheckBoxMenuItem();
		if(showFolderCheckBoxMenuItem != null && showFolderCheckBoxMenuItem.getState()) {
			// 显示文件夹
			// 如果视图组被关闭,应先打开视图组
			window.getViewsModular().getShowViewGroupsCheckBoxMenuItem().setState(true);
			showViewGroups();
			if(viewGroups.getArrayList().contains(showFolderCheckBoxMenuItem)) {
				// 如果存在已经打开的文件夹,不要重复打开,确保viewGroups.arrayList中不含重复项,否则关闭时可能出错
				// 好像不用删除也会自动去除重复
				viewGroups.removeTab(viewGroups.getArrayList().indexOf(showFolderCheckBoxMenuItem));
				viewGroups.addTab("文件夹", null, new FileTreePanel(window, folder), null, true, showFolderCheckBoxMenuItem);
				return;
			} else {
				// 如果不存在已经打开的文件夹
				viewGroups.addTab("文件夹", null, new FileTreePanel(window, folder), null, true, showFolderCheckBoxMenuItem);
			}
		}else {
			// 关闭文件夹
			if(viewGroups.getArrayList().contains(showFolderCheckBoxMenuItem)) {
				// 如果存在已经打开的文件夹,则从viewGroups中移除
				int index = viewGroups.getArrayList().indexOf(showFolderCheckBoxMenuItem);
				viewGroups.removeTab(index);
			}
			if(viewGroups.getComponentCount() < 1) {
				// 如果视图组为空,则关闭视图组
				viewGroups.setVisible(false);
				horizontalJSplitPane.setDividerSize(0);
			}
		}
	}
	public void showViewGroups() {
		// 显示:视图组
		// 根据window.ViewsModular.showViewGroupsCheckBoxMenuItem的状态判断打开或关闭视图组
		if(window.getViewsModular().getShowViewGroupsCheckBoxMenuItem().getState()) {
			// 打开视图组
			viewGroups.setVisible(true);
			horizontalJSplitPane.setDividerLocation(horizontalDividerLocation);
			horizontalJSplitPane.setDividerSize(DividerSize);
		}else {
			// 关闭视图组
			viewGroups.setVisible(false);
			horizontalJSplitPane.setDividerSize(0);
			// 如果左侧视图组内存在内容,应全部关闭
			viewGroups.removeAllTab();
		}
	}
	
	
	public int getDividerSize() {
		return DividerSize;
	}

	public void setDividerSize(int dividerSize) {
		DividerSize = dividerSize;
	}

	public double getVerticalDividerLocation() {
		return verticalDividerLocation;
	}

	public void setVerticalDividerLocation(double verticalDividerLocation) {
		this.verticalDividerLocation = verticalDividerLocation;
	}

	public double getHorizontalDividerLocation() {
		return horizontalDividerLocation;
	}

	public void setHorizontalDividerLocation(double horizontalDividerLocation) {
		this.horizontalDividerLocation = horizontalDividerLocation;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public STabbedPane getsTabbedPane() {
		return sTabbedPane;
	}

	public void setsTabbedPane(STabbedPane sTabbedPane) {
		this.sTabbedPane = sTabbedPane;
	}

	public JTextPane getConsoleJPanel() {
		return consoleJPanel;
	}

	public void setConsoleJPanel(JTextPane consoleJPanel) {
		this.consoleJPanel = consoleJPanel;
	}

	public JScrollPane getConsoleJScrollPane() {
		return consoleJScrollPane;
	}

	public void setConsoleJScrollPane(JScrollPane consoleJScrollPane) {
		this.consoleJScrollPane = consoleJScrollPane;
	}

	public JSplitPane getVerticalJSplitPane() {
		return verticalJSplitPane;
	}

	public void setVerticalJSplitPane(JSplitPane verticalJSplitPane) {
		this.verticalJSplitPane = verticalJSplitPane;
	}

	public JSplitPane getHorizontalJSplitPane() {
		return horizontalJSplitPane;
	}

	public void setHorizontalJSplitPane(JSplitPane horizontalJSplitPane) {
		this.horizontalJSplitPane = horizontalJSplitPane;
	}

	public ViewGroups getLeftSTabbedPanel() {
		return viewGroups;
	}

	public void setLeftSTabbedPanel(ViewGroups viewGroups) {
		this.viewGroups = viewGroups;
	}

	public ViewGroups getViewGroups() {
		return viewGroups;
	}

	public void setViewGroups(ViewGroups viewGroups) {
		this.viewGroups = viewGroups;
	}
}
