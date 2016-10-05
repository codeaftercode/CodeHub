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
			// �½����ı��ļ�
			fileModualr.newText();
			return;
		}
		if(source == fileModualr.openMenuItem) {
			fileModualr.openFile();
			return;
		}
		if(source == fileModualr.openFolderMenuItem) {
			// ���ļ���
			fileModualr.openFolder();
			return;
		}
		if(source == fileModualr.saveMenuItem) {
			// ����
			fileModualr.saveFile();
			return;
		}
		if(source == fileModualr.saveWithEncodingMenuItem) {
			// ��ָ�����뱣��
			
			return;
		}
		if(source == fileModualr.saveAsMenuItem) {
			// ���Ϊ
			fileModualr.saveAs();
			return;
		}
		if(source == fileModualr.saveAllMenuItem) {
			// ȫ������
			
			return;
		}
		if(source == fileModualr.closeFileMenuItem) {
			// �ر�
			
			return;
		}
		if(source == fileModualr.closeAllFileMenuItem) {
			// ȫ���ر�
			
			return;
		}
		if(source == fileModualr.exitMenuItem) {
			// �˳�
			
			return;
		}

	}
	
}
