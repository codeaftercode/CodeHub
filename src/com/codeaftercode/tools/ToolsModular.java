package com.codeaftercode.tools;

import com.codeaftercode.ui.MyJMenu;
import com.codeaftercode.ui.MyJMenuItem;
import com.codeaftercode.ui.Window;

public class ToolsModular {

	private Window window;
	private MyJMenu toolsMenu;
	protected MyJMenuItem highlightMenuItem;
	protected MyJMenuItem checkMenuItem;
	
	public ToolsModular(Window window) {
		this.window = window;
		//ע�����
		ToolsModularActionListener editModularActionListener = new ToolsModularActionListener(this);
		//��ʼ���˵�
		toolsMenu=new MyJMenu("����(T)");


		highlightMenuItem=new MyJMenuItem("������ʾhtml��ǩ");
		checkMenuItem=new MyJMenuItem("����");
		//���Ƿ�
		toolsMenu.setMnemonic('T');

		//�˵��󶨲˵���
		toolsMenu.add(highlightMenuItem);
		toolsMenu.add(checkMenuItem);

		
		//�������ע���¼�
		highlightMenuItem.addActionListener(editModularActionListener);
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(toolsMenu);
	}
}
