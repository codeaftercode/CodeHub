package com.codeaftercode.workplace;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import com.codeaftercode.common.Window;

public class Workplace {
	private Window window;
	// Ĭ�Ϸ����ָ������
	private int DividerSize = 5;
	// Ĭ�ϴ�ֱ����(���ҷ���)����
	private double verticalDividerLocation = 0.8;
	// Ĭ��ˮƽ����(���·���)����
	private double horizontalDividerLocation = 0.2;
	
	
	// �ı��༭��:
	private STabbedPane sTabbedPane;
	// ������:
	private JTextPane consoleJPanel;
	private JScrollPane consoleJScrollPane;
	// ���:
	private ViewGroups viewGroups; 
	// ����:
	private JSplitPane verticalJSplitPane;// ���·���,��Ϊ�ı��༭��,��Ϊ������
	private JSplitPane horizontalJSplitPane;// ���ҷ���,��Ϊ���,��ΪverticalJSplitPane
	
	public Workplace(Window window) {
		super();
		this.window = window;
		
		
		// �ı��༭��:
		sTabbedPane = new STabbedPane(window);
		// ������:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		// ���:�����ʵ���ļ��нṹ
		//leftPanel =new FileTreePanel(window,new File("D:\\Data"));
		//leftPanel = new JPanel();//new FileTreePanel(window);
		viewGroups = new ViewGroups(window);
		// ����:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// ���·���,��Ϊ�ı��༭��,��Ϊ������
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewGroups, verticalJSplitPane);// ���ҷ���,��Ϊ���,��ΪjSplitPane1
		
		// ���÷���
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		closeViewGroups();
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
	
	public void showViewGroups() {
		// ����ͼ��
		viewGroups.setVisible(true);
		horizontalJSplitPane.setDividerLocation(horizontalDividerLocation);
		horizontalJSplitPane.setDividerSize(DividerSize);
	}
	public void closeViewGroups() {
		// �ر���ͼ��
		viewGroups.setVisible(false);
		horizontalJSplitPane.setDividerSize(0);
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

	public ViewGroups getLeftSTabbedPanel() {
		return viewGroups;
	}

	public void setLeftSTabbedPanel(ViewGroups viewGroups) {
		this.viewGroups = viewGroups;
	}

	public ViewGroups getViewGroups() {
		return viewGroups;
	}
}
