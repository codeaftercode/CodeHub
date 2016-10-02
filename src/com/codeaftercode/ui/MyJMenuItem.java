package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class MyJMenuItem extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJMenuItem() {
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	MyJMenuItem(String str) {
		//设置菜单图标
		super(str);
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	MyJMenuItem(String string, ImageIcon imageIcon) {
		//设置菜单图标
		super(string,imageIcon);
		//设置菜单字体
		//setFont(Window.menuFont);
		//注册监听.必须先创建监听器,再创建本对象,否则注册无效.有时好用,有时不好用,why?
		//addActionListener(Window.windowActionListener);
	}
}
