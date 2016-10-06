package com.codeaftercode.common;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class WindowComponentAdapter extends ComponentAdapter {
	private Window window;

	public WindowComponentAdapter(Window window) {
		super();
		this.window = window;
	}
	public void componentResized(ComponentEvent e) {
		// 窗体大小改变时,设置垂直分隔(左右分栏)比例
		window.getWorkplace().getVerticalJSplitPane().setDividerLocation(window.getWorkplace().getVerticalDividerLocation());
		// 窗体大小改变时,设置水平分隔(上下分栏)比例
		window.getWorkplace().getHorizontalJSplitPane().setDividerLocation(window.getWorkplace().getHorizontalDividerLocation());
	}
	
}
