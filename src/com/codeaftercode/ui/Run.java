package com.codeaftercode.ui;

public class Run {

	public static void main(String[] args) {
		Window window = new Window("CodeHub");
		window.init();
		window.setVisible(true);
		//���÷�������:����Ҫwindow.setVisible(ture)������,������Ч.
		//window.horizontalJSplitPane.setDividerLocation(window.getHorizontalDividerLocation());//���·���
		//window.verticalJSplitPane.setDividerLocation(window.getVerticalDividerLocation());//���ҷ���
	}

}
