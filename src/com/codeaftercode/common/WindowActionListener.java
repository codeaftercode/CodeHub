package com.codeaftercode.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * @author Codeaftercode
 * Window类的事件监听器类
 * 采用有参数的构造方法的形式，将窗体类Window以参数的形式传给此监听器类
 */
public class WindowActionListener implements ActionListener {
	private Window window;
	WindowActionListener(Window window) {
		this.window = window;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

	}
}
