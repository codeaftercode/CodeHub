package com.codeaftercode.statusbar;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSeparator;

public class SLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSeparator separator;

	public SLabel(JSeparator separator) {
		super();
		this.separator = separator;
	}


	@Override
	public void setText(String text) {
		super.setText(text);
		if(separator == null) {
			return;
		}
		separator.setVisible(text != null && !text.equals(""));
	}
}
