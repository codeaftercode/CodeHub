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
		//注册监听
		HelpModularActionListener viewsModularActionListener = new HelpModularActionListener(this);
		//初始化菜单
		helpMenu=new MyJMenu("帮助(H)");
		
		aboutMenuItem = new MyJMenuItem("关于html检查器(A)");

		//助记符
		helpMenu.setMnemonic('H');

		//菜单绑定菜单项
		helpMenu.add(aboutMenuItem);

		//向监听器注册事件
		aboutMenuItem.addActionListener(viewsModularActionListener);
		
		//向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(helpMenu);
	}
}
