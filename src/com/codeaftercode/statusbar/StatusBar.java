package com.codeaftercode.statusbar;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import com.codeaftercode.common.Window;

public class StatusBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Window window;
	private JLabel label;
	private JSeparator charsetSeparator;
	private SLabel charset;
	// 当前文件字符集
	private JSeparator testSeparator;
	private SLabel test;
	
	public StatusBar(Window window) {
		this.window = window;
		
		// 状态栏为单行,布局用BoxLayout,水平方向
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(boxLayout);
		
		// 初始化标签,分隔线
		label = new JLabel();		
		charsetSeparator = new JSeparator(JSeparator.VERTICAL);
		testSeparator = new JSeparator(JSeparator.VERTICAL);
		initSeparator(charsetSeparator);
		initSeparator(testSeparator);
		charset = new SLabel(charsetSeparator);
		test = new SLabel(testSeparator);
		// 固定标签宽度.其他方法均无效
		label.setMaximumSize(new Dimension(500, 100));
		charset.setMaximumSize(new Dimension(100, 100));
		test.setMaximumSize(new Dimension(100, 100));
		
		add(label);
		add(charsetSeparator);
		add(charset);
		add(testSeparator);
		add(test);
		
		setFloatable(false);
	}

	private void initSeparator(JSeparator separator) {
		// 设置前导分隔线
		separator.setPreferredSize(new Dimension(20, 20));
		separator.setMaximumSize(new Dimension(20, 20));
		separator.setMinimumSize(new Dimension(20, 20));
		// 初始均为不可见
		separator.setVisible(false);
	}
	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public SLabel getCharset() {
		return charset;
	}

	public void setCharset(SLabel charset) {
		this.charset = charset;
	}

	public SLabel getTest() {
		return test;
	}

	public void setTest(SLabel test) {
		this.test = test;
	}
}
