package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

public class MyJMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJMenu() {
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	MyJMenu(String str) {
		super(str);
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	public MyJMenu(String string, ImageIcon imageIcon) {
		super(string);
		//���ò˵�ͼ��
		setIcon(imageIcon);
		//���ò˵�����
		//setFont(Window.menuFont);
	}
}
