package com.codeaftercode.common;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	}
}
