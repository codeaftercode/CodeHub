package com.codeaftercode.document;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DocumentModularActionListener implements ActionListener {
	private DocumentModular documentModular;
	
	public DocumentModularActionListener(DocumentModular documentModular) {
		this.documentModular = documentModular;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == documentModular.changeCharsetMenuItem) {
			// 转换字符集为...
			documentModular.changeCharset();
			return;
		}
		if(source == documentModular.reloadCharsetMenuItem) {
			// 更改字符集加载文件
			documentModular.reloadCharset();
			return;
		}
		
	}

}
