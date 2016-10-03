package com.codeaftercode.ui;

import javax.swing.ImageIcon;
import javax.swing.JMenu;

public class MyJMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MyJMenu() {
		super();
	}
	public MyJMenu(String str) {
		super(str);
	}
	public MyJMenu(String string, ImageIcon imageIcon) {
		super(string);
		//…Ë÷√≤Àµ•Õº±Í
		setIcon(imageIcon);
	}
}
