package com.codeaftercode.workplace;

import java.io.File;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;

import com.codeaftercode.ui.MyJCheckBoxMenuItem;
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
	private ViewGroups viewGroups; 
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
		// ���:�����ʵ���ļ��нṹ
		//leftPanel =new FileTreePanel(window,new File("D:\\Data"));
		//leftPanel = new JPanel();//new FileTreePanel(window);
		viewGroups = new ViewGroups(window);
		// ����:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sTabbedPane, consoleJScrollPane);// ���·���,��Ϊ�ı��༭��,��Ϊ������
		//horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, verticalJSplitPane);// ���ҷ���,��Ϊ���,��ΪjSplitPane1
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewGroups, verticalJSplitPane);// ���ҷ���,��Ϊ���,��ΪjSplitPane1
		
		// ���÷���
		window.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(DividerSize);
		horizontalJSplitPane.setDividerSize(DividerSize);
		showViewGroups();
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
	
	public void showFolder() {
		showFolder(null);
	}
	public void showFolder(File folder) {
		MyJCheckBoxMenuItem showFolderCheckBoxMenuItem = window.getViewsModular().getShowFolderCheckBoxMenuItem();
		if(showFolderCheckBoxMenuItem != null && showFolderCheckBoxMenuItem.getState()) {
			// ��ʾ�ļ���
			// �����ͼ�鱻�ر�,Ӧ�ȴ���ͼ��
			window.getViewsModular().getShowViewGroupsCheckBoxMenuItem().setState(true);
			showViewGroups();
			if(viewGroups.getArrayList().contains(showFolderCheckBoxMenuItem)) {
				// ��������Ѿ��򿪵��ļ���,��Ҫ�ظ���,ȷ��viewGroups.arrayList�в����ظ���,����ر�ʱ���ܳ���
				// ������ɾ��Ҳ���Զ�ȥ���ظ�
				viewGroups.removeTab(viewGroups.getArrayList().indexOf(showFolderCheckBoxMenuItem));
				viewGroups.addTab("�ļ���", null, new FileTreePanel(window, folder), null, true, showFolderCheckBoxMenuItem);
				return;
			} else {
				// ����������Ѿ��򿪵��ļ���
				viewGroups.addTab("�ļ���", null, new FileTreePanel(window, folder), null, true, showFolderCheckBoxMenuItem);
			}
		}else {
			// �ر��ļ���
			if(viewGroups.getArrayList().contains(showFolderCheckBoxMenuItem)) {
				// ��������Ѿ��򿪵��ļ���,���viewGroups���Ƴ�
				int index = viewGroups.getArrayList().indexOf(showFolderCheckBoxMenuItem);
				viewGroups.removeTab(index);
			}
			if(viewGroups.getComponentCount() < 1) {
				// �����ͼ��Ϊ��,��ر���ͼ��
				viewGroups.setVisible(false);
				horizontalJSplitPane.setDividerSize(0);
			}
		}
	}
	public void showViewGroups() {
		// ��ʾ:��ͼ��
		// ����window.ViewsModular.showViewGroupsCheckBoxMenuItem��״̬�жϴ򿪻�ر���ͼ��
		if(window.getViewsModular().getShowViewGroupsCheckBoxMenuItem().getState()) {
			// ����ͼ��
			viewGroups.setVisible(true);
			horizontalJSplitPane.setDividerLocation(horizontalDividerLocation);
			horizontalJSplitPane.setDividerSize(DividerSize);
		}else {
			// �ر���ͼ��
			viewGroups.setVisible(false);
			horizontalJSplitPane.setDividerSize(0);
			// ��������ͼ���ڴ�������,Ӧȫ���ر�
			viewGroups.removeAllTab();
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

	public void setViewGroups(ViewGroups viewGroups) {
		this.viewGroups = viewGroups;
	}
}
