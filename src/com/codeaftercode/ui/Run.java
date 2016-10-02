package com.codeaftercode.ui;

public class Run {

	public static void main(String[] args) {
		Window window = new Window("CodeHub");
		window.init();
		window.setVisible(true);
		//设置分栏比例:必须要window.setVisible(ture)后设置,否则无效
		window.horizontalJSplitPane.setDividerLocation(Window.horizontalDividerLocation);//上下分栏
		window.verticalJSplitPane.setDividerLocation(Window.verticalDividerLocation);//左右分栏
	}

}
