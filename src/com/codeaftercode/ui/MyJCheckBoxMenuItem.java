package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;

public class MyJCheckBoxMenuItem extends JCheckBoxMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJCheckBoxMenuItem() {
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	MyJCheckBoxMenuItem(String string) {
		//设置菜单图标
		super(string);
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	MyJCheckBoxMenuItem(String string, ImageIcon imageIcon) {
		//设置菜单图标
		super(string,imageIcon);
		//设置菜单字体
		//setFont(Window.menuFont);
		//注册监听无效
		//addActionListener(Window.myActionListener);
	}
}
