package com.codeaftercode.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
		if(source == window.openMenuItem) {
			try {
				window.openFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(source == window.newTextMenuItem) {
			//新建纯文本文件
			window.newText();
		}else if(source == window.showLeftCheckBoxMenuItem) {
			//显示或关闭左侧(文件夹)
			window.showLeft();
		} else if(source == window.showConsoleCheckBoxMenuItem) {
			//显示或关闭下方(命令行)
			window.showConsole();
		}
	}
}
