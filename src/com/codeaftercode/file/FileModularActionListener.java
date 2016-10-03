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
			//�½����ı��ļ�
			fileModualr.newText();
			return;
		}
		if(source == fileModualr.openMenuItem) {
			//���ļ�
			try {
				fileModualr.openFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}

	}
	
}
