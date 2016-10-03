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
		//注册监听
		ToolsModularActionListener editModularActionListener = new ToolsModularActionListener(this);
		//初始化菜单
		toolsMenu=new MyJMenu("工具(T)");


		highlightMenuItem=new MyJMenuItem("高亮显示html标签");
		checkMenuItem=new MyJMenuItem("纠错");
		//助记符
		toolsMenu.setMnemonic('T');

		//菜单绑定菜单项
		toolsMenu.add(highlightMenuItem);
		toolsMenu.add(checkMenuItem);

		
		//向监听器注册事件
		highlightMenuItem.addActionListener(editModularActionListener);
		
		//向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(toolsMenu);
	}
}
