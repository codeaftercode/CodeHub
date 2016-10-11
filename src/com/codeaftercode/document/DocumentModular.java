package com.codeaftercode.document;

import com.codeaftercode.common.MyJMenu;
import com.codeaftercode.common.MyJMenuItem;
import com.codeaftercode.common.Window;

public class DocumentModular {
	private Window window;
	private MyJMenu documentMenu;
	private MyJMenu charsetMenu;
	protected MyJMenuItem changeCharsetMenuItem;
	protected MyJMenuItem reloadCharsetMenuItem;
	public DocumentModular(Window window) {
		super();
		this.window = window;
		
		// 注册监听
		DocumentModularActionListener documentModularActionListener = new DocumentModularActionListener(this);
		
		documentMenu = new MyJMenu("文档(D)");
		charsetMenu = new MyJMenu("字符集");
		changeCharsetMenuItem = new MyJMenuItem("转换字符集为...");
		reloadCharsetMenuItem = new MyJMenuItem("更改字符集加载文件");
		
		// 助记符
		documentMenu.setMnemonic('D');
		
		documentMenu.add(charsetMenu);
		charsetMenu.add(changeCharsetMenuItem);
		charsetMenu.add(reloadCharsetMenuItem);
		
		changeCharsetMenuItem.addActionListener(documentModularActionListener);
		reloadCharsetMenuItem.addActionListener(documentModularActionListener);
	}
	public MyJMenu getDocumentMenu() {
		return documentMenu;
	}
	public void changeCharset() {
		// TODO Auto-generated method stub
		
	}
	public void reloadCharset() {
		window.getWorkplace().getsTabbedPane().reloadCharset();
	}
}
