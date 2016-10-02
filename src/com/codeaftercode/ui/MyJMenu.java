package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

public class MyJMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJMenu() {
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	MyJMenu(String str) {
		super(str);
		//设置菜单字体
		//setFont(Window.menuFont);
	}
	public MyJMenu(String string, ImageIcon imageIcon) {
		super(string);
		//设置菜单图标
		setIcon(imageIcon);
		//设置菜单字体
		//setFont(Window.menuFont);
	}
}
