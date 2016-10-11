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
		//注册监听
		ViewsModularActionListener viewsModularActionListener = new ViewsModularActionListener(this);
		//初始化菜单
		viewsMenu=new MyJMenu("视图(V)");

		showViewMenu = new MyJMenu("显示");
		//showViewGroupsCheckBoxMenuItem = new MyJCheckBoxMenuItem("视图组");
		showFolderCheckBoxMenuItem = new MyJCheckBoxMenuItem("文件夹");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("工作区");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("命令行");
		//助记符
		viewsMenu.setMnemonic('V');

		//菜单绑定菜单项
		viewsMenu.add(showViewMenu);
		//showViewMenu.add(showViewGroupsCheckBoxMenuItem);
		showViewMenu.addSeparator();
		showViewMenu.add(showFolderCheckBoxMenuItem);
		//showViewMenu.add(showTextCheckBoxMenuItem);
		showViewMenu.add(showConsoleCheckBoxMenuItem);
		//showViewGroupsCheckBoxMenuItem.setState(false);
		showConsoleCheckBoxMenuItem.setState(false);

		
		//向监听器注册事件
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
