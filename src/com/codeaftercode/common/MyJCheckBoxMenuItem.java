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
		//���ò˵�ͼ��
		super(string);
	}
	public MyJCheckBoxMenuItem(String string, ImageIcon imageIcon) {
		//���ò˵�ͼ��
		super(string,imageIcon);
		//ע�������Ч
		//addActionListener(Window.myActionListener);
	}
}
