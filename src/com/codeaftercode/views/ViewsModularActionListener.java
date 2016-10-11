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
		if(source == viewsModular.showFolderCheckBoxMenuItem) {
			//��ʾ��ر������ͼ��-�ļ���
			viewsModular.window.getWorkplace().getViewGroups().showFolder();
			return;
		}
		if(source == viewsModular.showConsoleCheckBoxMenuItem) {
			//��ʾ��ر��·�(������)
			viewsModular.window.getWorkplace().showConsole();
			return;
		}

	}
	
}
