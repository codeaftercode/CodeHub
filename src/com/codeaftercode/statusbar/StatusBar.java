package com.codeaftercode.statusbar;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import com.codeaftercode.ui.Window;

public class StatusBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window window;
	private JLabel label;
	
	public StatusBar(Window window) {
		this.window = window;
		label = new JLabel("×´Ì¬À¸");
		add(label);
		this.setFloatable(false);
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
}
