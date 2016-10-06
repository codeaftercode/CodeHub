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
		// �����С�ı�ʱ,���ô�ֱ�ָ�(���ҷ���)����
		window.getWorkplace().getVerticalJSplitPane().setDividerLocation(window.getWorkplace().getVerticalDividerLocation());
		// �����С�ı�ʱ,����ˮƽ�ָ�(���·���)����
		window.getWorkplace().getHorizontalJSplitPane().setDividerLocation(window.getWorkplace().getHorizontalDividerLocation());
	}
	
}
