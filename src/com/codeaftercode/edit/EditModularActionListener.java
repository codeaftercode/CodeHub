package com.codeaftercode.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditModularActionListener implements ActionListener {
	private EditModular editModualr;
	
	public EditModularActionListener(EditModular fileModualr) {
		super();
		this.editModualr = fileModualr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == editModualr.copyMenuItem) {
			//����
			return;
		}
		if(source == editModualr.copyMenuItem) {
			//����
			return;
		}
		if(source == editModualr.pasteMenuItem) {
			//ճ��
			return;
		}
		if(source == editModualr.selectAllMenuItem) {
			//ȫѡ
			return;
		}
	}
	
}
