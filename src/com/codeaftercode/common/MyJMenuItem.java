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
		//���ò˵�ͼ��
		super(str);
	}
	public MyJMenuItem(String string, ImageIcon imageIcon) {
		//���ò˵�ͼ��
		super(string,imageIcon);
	}
}
