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
			// �½����ı��ļ�
			fileModular.newText();
			return;
		}
		if(source == fileModular.openMenuItem) {
			// ���ļ�
			fileModular.openFile();
			return;
		}
		if(source == fileModular.openFolderMenuItem) {
			// ���ļ���
			fileModular.openFolder();
			return;
		}
		if(source == fileModular.saveMenuItem) {
			// ����
			fileModular.saveFile();
			return;
		}
		if(source == fileModular.saveAsMenuItem) {
			// ���Ϊ
			fileModular.saveAs();
			return;
		}
		if(source == fileModular.saveAllMenuItem) {
			// ȫ������
			fileModular.savaAll();
			return;
		}
		if(source == fileModular.closeFileMenuItem) {
			// �ر�
			fileModular.closeFile();
			return;
		}
		if(source == fileModular.closeAllFileMenuItem) {
			// ȫ���ر�
			fileModular.closeAllFile();
			return;
		}
		if(source == fileModular.exitMenuItem) {
			// �˳�
			fileModular.exit();
			return;
		}

	}
	
}
