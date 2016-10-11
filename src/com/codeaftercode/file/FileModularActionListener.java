package com.codeaftercode.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileModularActionListener implements ActionListener {
	private FileModular fileModular;
	
	public FileModularActionListener(FileModular fileModualr) {
		super();
		this.fileModular = fileModualr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == fileModular.newTextMenuItem) {
			// 新建纯文本文件
			fileModular.newText();
			return;
		}
		if(source == fileModular.openMenuItem) {
			// 打开文件
			fileModular.openFile();
			return;
		}
		if(source == fileModular.openFolderMenuItem) {
			// 打开文件夹
			fileModular.openFolder();
			return;
		}
		if(source == fileModular.saveMenuItem) {
			// 保存
			fileModular.saveFile();
			return;
		}
		if(source == fileModular.saveAsMenuItem) {
			// 另存为
			fileModular.saveAs();
			return;
		}
		if(source == fileModular.saveAllMenuItem) {
			// 全部保存
			fileModular.savaAll();
			return;
		}
		if(source == fileModular.closeFileMenuItem) {
			// 关闭
			fileModular.closeFile();
			return;
		}
		if(source == fileModular.closeAllFileMenuItem) {
			// 全部关闭
			fileModular.closeAllFile();
			return;
		}
		if(source == fileModular.exitMenuItem) {
			// 退出
			fileModular.exit();
			return;
		}

	}
	
}
