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
			// ��ʾ��ر������ͼ��
			viewsModular.window.getWorkplace().showViewGroups();
			return;
		}
		if(source == viewsModular.showFolderCheckBoxMenuItem) {
			//��ʾ��ر������ͼ��-�ļ���
			viewsModular.window.getWorkplace().showFolder();
			return;
		}
		if(source == viewsModular.showConsoleCheckBoxMenuItem) {
			//��ʾ��ر��·�(������)
			viewsModular.window.getWorkplace().showConsole();
			return;
		}

	}
	
}
