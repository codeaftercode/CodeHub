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
		//注册监听
		EditModularActionListener editModularActionListener = new EditModularActionListener(this);
		//初始化菜单
		editMenu=new MyJMenu("编辑(E)");

		copyMenuItem=new MyJMenuItem("复制(C)");
		cutMenuItem=new MyJMenuItem("剪切(T)");
		pasteMenuItem=new MyJMenuItem("粘贴(P)");
		selectAllMenuItem=new MyJMenuItem("全选(A)");
		
		//助记符
		editMenu.setMnemonic('E');

		//菜单绑定菜单项
		editMenu.add(copyMenuItem);
		editMenu.add(cutMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		
		//向监听器注册事件
		copyMenuItem.addActionListener(editModularActionListener);
		
		//向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(editMenu);
	}
}
