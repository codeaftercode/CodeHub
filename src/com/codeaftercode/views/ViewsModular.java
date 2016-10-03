package com.codeaftercode.views;

import com.codeaftercode.ui.MyJCheckBoxMenuItem;
import com.codeaftercode.ui.MyJMenu;
import com.codeaftercode.ui.Window;

public class ViewsModular {

	protected Window window;
	private MyJMenu viewsMenu;
	private MyJMenu showViewMenu;
	protected MyJCheckBoxMenuItem showLeftCheckBoxMenuItem;
	//protected MyJCheckBoxMenuItem showTextCheckBoxMenuItem;
	protected MyJCheckBoxMenuItem showConsoleCheckBoxMenuItem;
	

	public ViewsModular(Window window) {
		this.window = window;
		//ע�����
		ViewsModularActionListener viewsModularActionListener = new ViewsModularActionListener(this);
		//��ʼ���˵�
		viewsMenu=new MyJMenu("��ͼ(V)");

		showViewMenu = new MyJMenu("��ʾ��ͼ");
		showLeftCheckBoxMenuItem = new MyJCheckBoxMenuItem("�ļ���");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		//���Ƿ�
		viewsMenu.setMnemonic('V');

		//�˵��󶨲˵���
		viewsMenu.add(showViewMenu);
		showViewMenu.add(showLeftCheckBoxMenuItem);
		//showViewMenu.add(showTextCheckBoxMenuItem);
		showViewMenu.add(showConsoleCheckBoxMenuItem);
		showLeftCheckBoxMenuItem.setState(false);
		showConsoleCheckBoxMenuItem.setState(false);

		
		//�������ע���¼�
		showLeftCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		showConsoleCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(viewsMenu);
	}
	
	
	public MyJCheckBoxMenuItem getShowLeftCheckBoxMenuItem() {
		return showLeftCheckBoxMenuItem;
	}

	public void setShowLeftCheckBoxMenuItem(MyJCheckBoxMenuItem showLeftCheckBoxMenuItem) {
		this.showLeftCheckBoxMenuItem = showLeftCheckBoxMenuItem;
	}

	public MyJCheckBoxMenuItem getShowConsoleCheckBoxMenuItem() {
		return showConsoleCheckBoxMenuItem;
	}

	public void setShowConsoleCheckBoxMenuItem(MyJCheckBoxMenuItem showConsoleCheckBoxMenuItem) {
		this.showConsoleCheckBoxMenuItem = showConsoleCheckBoxMenuItem;
	}
}
