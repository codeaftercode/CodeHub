package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class MyJMenuItem extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJMenuItem() {
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	MyJMenuItem(String str) {
		//���ò˵�ͼ��
		super(str);
		//���ò˵�����
		//setFont(Window.menuFont);
	}
	MyJMenuItem(String string, ImageIcon imageIcon) {
		//���ò˵�ͼ��
		super(string,imageIcon);
		//���ò˵�����
		//setFont(Window.menuFont);
		//ע�����.�����ȴ���������,�ٴ���������,����ע����Ч.��ʱ����,��ʱ������,why?
		//addActionListener(Window.windowActionListener);
	}
}
