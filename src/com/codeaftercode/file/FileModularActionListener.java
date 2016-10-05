package com.codeaftercode.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FileModularActionListener implements ActionListener {
	private FileModular fileModualr;
	
	public FileModularActionListener(FileModular fileModualr) {
		super();
		this.fileModualr = fileModualr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == fileModualr.newTextMenuItem) {
			// 新建纯文本文件
			fileModualr.newText();
			return;
		}
		if(source == fileModualr.openMenuItem) {
			fileModualr.openFile();
			return;
		}
		if(source == fileModualr.openFolderMenuItem) {
			// 打开文件夹
			fileModualr.openFolder();
			return;
		}
		if(source == fileModualr.saveMenuItem) {
			// 保存
			fileModualr.saveFile();
			return;
		}
		if(source == fileModualr.saveWithEncodingMenuItem) {
			// 以指定编码保存
			
			return;
		}
		if(source == fileModualr.saveAsMenuItem) {
			// 另存为
			fileModualr.saveAs();
			return;
		}
		if(source == fileModualr.saveAllMenuItem) {
			// 全部保存
			
			return;
		}
		if(source == fileModualr.closeFileMenuItem) {
			// 关闭
			
			return;
		}
		if(source == fileModualr.closeAllFileMenuItem) {
			// 全部关闭
			
			return;
		}
		if(source == fileModualr.exitMenuItem) {
			// 退出
			
			return;
		}

	}
	
}
