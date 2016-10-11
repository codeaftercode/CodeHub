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
		
		// ע�����
		DocumentModularActionListener documentModularActionListener = new DocumentModularActionListener(this);
		
		documentMenu = new MyJMenu("�ĵ�(D)");
		charsetMenu = new MyJMenu("�ַ���");
		changeCharsetMenuItem = new MyJMenuItem("ת���ַ���Ϊ...");
		reloadCharsetMenuItem = new MyJMenuItem("�����ַ��������ļ�");
		
		// ���Ƿ�
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
