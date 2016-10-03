package com.codeaftercode.workplace;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import com.codeaftercode.ui.STabbedPane;
import com.codeaftercode.ui.Window;

public class Workplace {
	private Window window;
	// �ı��༭��:
	// JTextPane jTextPane;
	// JScrollPane jScrollPane;
	// Ĭ�Ϸ����ָ������
	private int DividerSize = 5;
	// Ĭ�ϴ�ֱ����(���ҷ���)����
	private double verticalDividerLocation = 0.8;
	// Ĭ��ˮƽ����(���·���)����
	private double horizontalDividerLocation = 0.2;
	
	
	private STabbedPane sTabbedPane;
	// ������:
	private JTextPane consoleJPanel;
	private JScrollPane consoleJScrollPane;
	// ���:
	private JPanel leftPanel;
	// ����:
	private JSplitPane verticalJSplitPane;// ���·���,��Ϊ�ı��༭��,��Ϊ������
	
	private JSplitPane horizontalJSplitPane;// ���ҷ���,��Ϊ���,��ΪverticalJSplitPane
	
	public Workplace(Window window) {
		super();
		this.window = window;
		
		
		// �ı��༭��:
		sTabbedPane = new STabbedPane();
		// ������:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		// ���:
		leftPanel = new JPanel();
		// ����:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// ���·���,��Ϊ�ı��༭��,��Ϊ������
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, verticalJSplitPane);// ���ҷ���,��Ϊ���,��ΪjSplitPane1
		
		// ���÷���
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		showLeft();
		showConsole();
	}
	
	@Override
	public String toString() {
		return "Workplace [DividerSize=" + DividerSize + ", verticalDividerLocation=" + verticalDividerLocation
				+ ", horizontalDividerLocation=" + horizontalDividerLocation + "]";
	}

	public void showConsole() {
		//��ʾ:������
		if(!getConsoleJScrollPane().isVisible()) {
			//����Ϊ�����пɼ�
			getConsoleJScrollPane().setVisible(true);
			//�������·�������
			getVerticalJSplitPane().setDividerLocation(getVerticalDividerLocation());
			//���÷ָ��߿��
			getVerticalJSplitPane().setDividerSize(getDividerSize());
		}else if(!window.getViewsModular().getShowConsoleCheckBoxMenuItem().getState()) {
			//����Ϊ�����в��ɼ�
			getConsoleJScrollPane().setVisible(false);
			getVerticalJSplitPane().setDividerSize(0);
		}
	}

	public void showLeft() {
		//��ʾ:�ļ���
		if(!getLeftPanel().isVisible()) {
			//����Ϊ���ɼ�
			getLeftPanel().setVisible(true);
			//�������ҷ�������
			getHorizontalJSplitPane().setDividerLocation(getHorizontalDividerLocation());
			//���÷ָ��߿��
			getHorizontalJSplitPane().setDividerSize(getDividerSize());
		}else if(!window.getViewsModular().getShowLeftCheckBoxMenuItem().getState()) {
			//����Ϊ��಻�ɼ�
			getLeftPanel().setVisible(false);
			getHorizontalJSplitPane().setDividerSize(0);
		}
	}
	
	
	public int getDividerSize() {
		return DividerSize;
	}

	public void setDividerSize(int dividerSize) {
		DividerSize = dividerSize;
	}

	public double getVerticalDividerLocation() {
		return verticalDividerLocation;
	}

	public void setVerticalDividerLocation(double verticalDividerLocation) {
		this.verticalDividerLocation = verticalDividerLocation;
	}

	public double getHorizontalDividerLocation() {
		return horizontalDividerLocation;
	}

	public void setHorizontalDividerLocation(double horizontalDividerLocation) {
		this.horizontalDividerLocation = horizontalDividerLocation;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public STabbedPane getsTabbedPane() {
		return sTabbedPane;
	}

	public void setsTabbedPane(STabbedPane sTabbedPane) {
		this.sTabbedPane = sTabbedPane;
	}

	public JTextPane getConsoleJPanel() {
		return consoleJPanel;
	}

	public void setConsoleJPanel(JTextPane consoleJPanel) {
		this.consoleJPanel = consoleJPanel;
	}

	public JScrollPane getConsoleJScrollPane() {
		return consoleJScrollPane;
	}

	public void setConsoleJScrollPane(JScrollPane consoleJScrollPane) {
		this.consoleJScrollPane = consoleJScrollPane;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	public JSplitPane getVerticalJSplitPane() {
		return verticalJSplitPane;
	}

	public void setVerticalJSplitPane(JSplitPane verticalJSplitPane) {
		this.verticalJSplitPane = verticalJSplitPane;
	}

	public JSplitPane getHorizontalJSplitPane() {
		return horizontalJSplitPane;
	}

	public void setHorizontalJSplitPane(JSplitPane horizontalJSplitPane) {
		this.horizontalJSplitPane = horizontalJSplitPane;
	}
}
