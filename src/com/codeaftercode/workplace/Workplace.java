package com.codeaftercode.workplace;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

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
	private JPanel leftPanel;
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
		// 左侧:
		leftPanel = new JPanel();
		// 分栏:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// 上下分栏,上为文本编辑区,下为命令行
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, verticalJSplitPane);// 左右分栏,左为左侧,右为jSplitPane1
		
		// 设置分栏
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		showLeft();
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

	public void showLeft() {
		//显示:文件夹
		if(!getLeftPanel().isVisible()) {
			//设置为左侧可见
			getLeftPanel().setVisible(true);
			//设置左右分栏比例
			getHorizontalJSplitPane().setDividerLocation(getHorizontalDividerLocation());
			//设置分隔线宽度
			getHorizontalJSplitPane().setDividerSize(getDividerSize());
		}else if(!window.getViewsModular().getShowLeftCheckBoxMenuItem().getState()) {
			//设置为左侧不可见
			getLeftPanel().setVisible(false);
			getHorizontalJSplitPane().setDividerSize(0);
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

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
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
}
