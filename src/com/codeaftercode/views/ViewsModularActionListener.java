package com.codeaftercode.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.codeaftercode.common.MyJCheckBoxMenuItem;
import com.codeaftercode.workplace.Workplace;

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
		if(source == viewsModular.showViewGroupsCheckBoxMenuItem) {
			// 显示或关闭左侧视图组
			viewsModular.window.getWorkplace().showViewGroups();
			return;
		}
		if(source == viewsModular.showFolderCheckBoxMenuItem) {
			//显示或关闭左侧视图组-文件夹
			viewsModular.window.getWorkplace().showFolder();
			return;
		}
		if(source == viewsModular.showConsoleCheckBoxMenuItem) {
			//显示或关闭下方(命令行)
			viewsModular.window.getWorkplace().showConsole();
			return;
		}

	}
	
}
