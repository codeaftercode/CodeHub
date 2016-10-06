package com.codeaftercode.help;

import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.MyJMenuItem;
import com.codeaftercode.common.Window;

public class HelpModular {

	private Window window;
	private MyJMenu helpMenu;
	protected MyJMenuItem aboutMenuItem;

	public HelpModular(Window window) {
		this.window = window;
		//ע�����
		HelpModularActionListener viewsModularActionListener = new HelpModularActionListener(this);
		//��ʼ���˵�
		helpMenu=new MyJMenu("����(H)");
		
		aboutMenuItem = new MyJMenuItem("����html�����(A)");

		//���Ƿ�
		helpMenu.setMnemonic('H');

		//�˵��󶨲˵���
		helpMenu.add(aboutMenuItem);

		//�������ע���¼�
		aboutMenuItem.addActionListener(viewsModularActionListener);
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(helpMenu);
	}
}
