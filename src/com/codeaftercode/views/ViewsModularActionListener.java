package com.codeaftercode.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewsModularActionListener implements ActionListener {
	private ViewsModular viewsModular;
	
	public ViewsModularActionListener(ViewsModular viewsModular) {
		super();
		this.viewsModular = viewsModular;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == viewsModular.showLeftCheckBoxMenuItem) {
			//显示或关闭左侧(文件夹)
			viewsModular.window.getWorkplace().showLeft();
			return;
		}
		if(source == viewsModular.showConsoleCheckBoxMenuItem) {
			//显示或关闭下方(命令行)
			viewsModular.window.getWorkplace().showConsole();
			return;
		}

	}
	
}
