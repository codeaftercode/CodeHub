package com.codeaftercode.common;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;

public class MyJCheckBoxMenuItem extends JCheckBoxMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyJCheckBoxMenuItem() {
		super();
	}
	public MyJCheckBoxMenuItem(String string) {
		//设置菜单图标
		super(string);
	}
	public MyJCheckBoxMenuItem(String string, ImageIcon imageIcon) {
		//设置菜单图标
		super(string,imageIcon);
		//注册监听无效
		//addActionListener(Window.myActionListener);
	}
}
