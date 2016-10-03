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
		//注册监听
		ViewsModularActionListener viewsModularActionListener = new ViewsModularActionListener(this);
		//初始化菜单
		viewsMenu=new MyJMenu("视图(V)");

		showViewMenu = new MyJMenu("显示视图");
		showLeftCheckBoxMenuItem = new MyJCheckBoxMenuItem("文件夹");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("工作区");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("命令行");
		//助记符
		viewsMenu.setMnemonic('V');

		//菜单绑定菜单项
		viewsMenu.add(showViewMenu);
		showViewMenu.add(showLeftCheckBoxMenuItem);
		//showViewMenu.add(showTextCheckBoxMenuItem);
		showViewMenu.add(showConsoleCheckBoxMenuItem);
		showLeftCheckBoxMenuItem.setState(false);
		showConsoleCheckBoxMenuItem.setState(false);

		
		//向监听器注册事件
		showLeftCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		showConsoleCheckBoxMenuItem.addActionListener(viewsModularActionListener);
		
		//向窗体的JMenuBar添加本菜单
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
