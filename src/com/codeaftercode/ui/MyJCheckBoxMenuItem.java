package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;

public class MyJCheckBoxMenuItem extends JCheckBoxMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJCheckBoxMenuItem() {
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	MyJCheckBoxMenuItem(String string) {
		//���ò˵�ͼ��
		super(string);
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	MyJCheckBoxMenuItem(String string, ImageIcon imageIcon) {
		//���ò˵�ͼ��
		super(string,imageIcon);
		//���ò˵�����
		//setFont(Window.menuFont);
		//ע�������Ч
		//addActionListener(Window.myActionListener);
	}
}
