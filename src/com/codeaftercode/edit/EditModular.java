package com.codeaftercode.edit;

import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.MyJMenuItem;
import com.codeaftercode.common.Window;

public class EditModular {

	private Window window;
	private MyJMenu editMenu;
	protected MyJMenuItem copyMenuItem;
	protected MyJMenuItem cutMenuItem;
	protected MyJMenuItem pasteMenuItem;
	protected MyJMenuItem selectAllMenuItem;
	
	public EditModular(Window window) {
		//this.window = window;
		//ע�����
		EditModularActionListener editModularActionListener = new EditModularActionListener(this);
		//��ʼ���˵�
		editMenu=new MyJMenu("�༭(E)");

		copyMenuItem=new MyJMenuItem("����(C)");
		cutMenuItem=new MyJMenuItem("����(T)");
		pasteMenuItem=new MyJMenuItem("ճ��(P)");
		selectAllMenuItem=new MyJMenuItem("ȫѡ(A)");
		
		//���Ƿ�
		editMenu.setMnemonic('E');

		//�˵��󶨲˵���
		editMenu.add(copyMenuItem);
		editMenu.add(cutMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		
		//�������ע���¼�
		copyMenuItem.addActionListener(editModularActionListener);
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(editMenu);
	}
}
