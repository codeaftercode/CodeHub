package com.codeaftercode.views;

import com.codeaftercode.common.MyJCheckBoxMenuItem;
import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.Window;

public class ViewsModular {

	protected Window window;
	private MyJMenu viewsMenu;
	private MyJMenu showViewMenu;
	//protected MyJCheckBoxMenuItem showViewGroupsCheckBoxMenuItem;
	//protected MyJCheckBoxMenuItem showTextCheckBoxMenuItem;
	protected MyJCheckBoxMenuItem showConsoleCheckBoxMenuItem;
	protected MyJCheckBoxMenuItem showFolderCheckBoxMenuItem;

	public ViewsModular(Window window) {
		this.window = window;
		//ע�����
		ViewsModularActionListener viewsModularActionListener = new ViewsModularActionListener(this);
		//��ʼ���˵�
		viewsMenu=new MyJMenu("��ͼ(V)");

		showViewMenu = new MyJMenu("��ʾ");
		//showViewGroupsCheckBoxMenuItem = new MyJCheckBoxMenuItem("��ͼ��");
		showFolderCheckBoxMenuItem = new MyJCheckBoxMenuItem("�ļ���");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		//���Ƿ�
		viewsMenu.setMnemonic('V');

		//�˵��󶨲˵���
		viewsMenu.add(showViewMenu);
		//showViewMenu.add(showViewGroupsCheckBoxMenuItem);
		showViewMenu.addSeparator();
		showViewMenu.add(showFolderCheckBoxMenuItem);
		//showViewMenu.add(showTextCheckBoxMenuItem);
		showViewMenu.add(showConsoleCheckBoxMenuItem);
		//showViewGroupsCheckBoxMenuItem.setState(false);
		showConsoleCheckBoxMenuItem.setState(false);

		
		//�������ע���¼�
		//showViewGroupsCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		showFolderCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		showConsoleCheckBoxMenuItem.addActionListener(viewsModularActionListener);
	}
	
	public MyJMenu getViewsMenu() {
		return viewsMenu;
	}

	public MyJCheckBoxMenuItem getShowFolderCheckBoxMenuItem() {
		return showFolderCheckBoxMenuItem;
	}

	public MyJCheckBoxMenuItem getShowConsoleCheckBoxMenuItem() {
		return showConsoleCheckBoxMenuItem;
	}
}
