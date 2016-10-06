package com.codeaftercode.common;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class MyJMenuItem extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyJMenuItem() {
		super();
	}
	public MyJMenuItem(String str) {
		//设置菜单图标
		super(str);
	}
	public MyJMenuItem(String string, ImageIcon imageIcon) {
		//设置菜单图标
		super(string,imageIcon);
	}
}
