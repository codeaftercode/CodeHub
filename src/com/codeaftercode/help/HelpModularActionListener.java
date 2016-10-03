package com.codeaftercode.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpModularActionListener implements ActionListener {
	private HelpModular helpModular;
	
	public HelpModularActionListener(HelpModular helpModular) {
		super();
		this.helpModular = helpModular;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == helpModular.aboutMenuItem) {
			//¹ØÓÚ
			return;
		}

	}
	
}
