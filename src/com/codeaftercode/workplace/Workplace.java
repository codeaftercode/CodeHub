package com.codeaftercode.workplace;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import com.codeaftercode.common.Window;

public class Workplace {
	private Window window;
	// 默认分栏分隔符宽度
	private int DividerSize = 5;
	// 默认垂直分栏(左右分栏)比例
	private double verticalDividerLocation = 0.8;
	// 默认水平分栏(上下分栏)比例
	private double horizontalDividerLocation = 0.2;
	
	
	// 文本编辑区:
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
		sTabbedPane = new STabbedPane(window);
		// 命令行:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		// 左侧:树组件实现文件夹结构
		//leftPanel =new FileTreePanel(window,new File("D:\\Data"));
		//leftPanel = new JPanel();//new FileTreePanel(window);
		viewGroups = new ViewGroups(window);
		// 分栏:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// 上下分栏,上为文本编辑区,下为命令行
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewGroups, verticalJSplitPane);// 左右分栏,左为左侧,右为jSplitPane1
		
		// 设置分栏
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		closeViewGroups();
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
	
	public void showViewGroups() {
		// 打开视图组
		viewGroups.setVisible(true);
		horizontalJSplitPane.setDividerLocation(horizontalDividerLocation);
		horizontalJSplitPane.setDividerSize(DividerSize);
	}
	public void closeViewGroups() {
		// 关闭视图组
		viewGroups.setVisible(false);
		horizontalJSplitPane.setDividerSize(0);
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
}
