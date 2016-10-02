package com.codeaftercode.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * 
 * @author Codeaftercode
 * Window����¼���������
 * �����в����Ĺ��췽������ʽ����������Window�Բ�������ʽ�����˼�������
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
			//�½����ı��ļ�
			window.newText();
		}else if(source == window.showLeftCheckBoxMenuItem) {
			//��ʾ��ر����(�ļ���)
			window.showLeft();
		} else if(source == window.showConsoleCheckBoxMenuItem) {
			//��ʾ��ر��·�(������)
			window.showConsole();
		}
	}
}
