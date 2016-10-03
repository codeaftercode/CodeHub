package com.codeaftercode.tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsModularActionListener implements ActionListener {
	private ToolsModular toolsModualr;
	
	public ToolsModularActionListener(ToolsModular toolsModualr) {
		super();
		this.toolsModualr = toolsModualr;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if(source == toolsModualr.checkMenuItem) {
			//ºÏ≤È
			return;
		}
		if(source == toolsModualr.highlightMenuItem) {
			//∏ﬂ¡¡
			return;
		}

	}
	
}
