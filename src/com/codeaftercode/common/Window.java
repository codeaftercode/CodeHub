package com.codeaftercode.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.codeaftercode.edit.EditModular;
import com.codeaftercode.file.FileModular;
import com.codeaftercode.help.HelpModular;
import com.codeaftercode.statusbar.StatusBar;
import com.codeaftercode.tools.ToolsModular;
/**
 * 
 * @author Codeaftercode
 * ������
 */
import com.codeaftercode.views.ViewsModular;
import com.codeaftercode.workplace.Workplace;

public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	// ͼ��·��
	public String iconsPath = this.getClass().getResource("/").getFile() + "icons//";
	// �������
	String name;
	int width = 800;
	int height = 800;
	private int newFileCounter = 0;

	// �¼�������
	public static WindowActionListener windowActionListener;
	/*********** �������� *************/
	Workplace workplace;
	/*********** ������ *************/
	// ������:δ����
	/*********** ״̬�� *************/
	StatusBar statusBar;
	/*********** �˵��� *************/
	// �˵���
	JMenuBar jMenuBar;
	// �˵�:
	FileModular fileModular;
	EditModular editModular;
	ToolsModular toolsModular;
	ViewsModular viewsModular;
	HelpModular helpModular;

	/**
	 * ���췽��:��ʼ������
	 * @param name �������
	 */
	Window(String name) {
		super(name);
		// ���ô������
		this.name = name;
		// ���ô���ͼ��
		this.setIconImage(new ImageIcon(iconsPath + "newFile.jpg").getImage());

		// �ó��������ʾ����Ļ����
		int x, y;
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		x = (size.width - this.width) / 2;
		y = (size.height - this.height) / 2;
		setSize(this.width, this.height);
		setLocation(x, y);
		// ���ô�����С�ߴ�
		// setMinimumSize(new Dimension(250,150));
		// ���ùرհ�ť
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	/**
	 * ��ʼ������
	 */
	void init() {
		// ����ȫ������
		initGlobalFontSetting(Global.globalFont);
		// ���ü���
		windowActionListener = new WindowActionListener(this);

		/*********** �˵��� *************/
		// �˵���
		jMenuBar = new JMenuBar();
		// ���ô���˵���
		super.setJMenuBar(jMenuBar);
		// �˵�:
		fileModular = new FileModular(this);
		editModular = new EditModular(this);
		toolsModular = new ToolsModular(this);
		viewsModular = new ViewsModular(this);
		helpModular = new HelpModular(this);

		/*********** �������� *************/
		workplace = new Workplace(this);

		/*********** ״̬�� *************/
		statusBar = new StatusBar(this);
		this.add(statusBar,BorderLayout.SOUTH);
		

		// �����ı䴰���С�¼�
		this.addComponentListener(new WindowComponentAdapter(this));
		/*
		 * //�������������ı��¼�,�����µı���: 
		 * verticalJSplitPane.addPropertyChangeListener(new PropertyChangeListener() {
		 * 
		 * @Override public void propertyChange(PropertyChangeEvent evt) { if
		 * (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY))
		 * { //verticalJSplitPane.getDividerLocation(); verticalDividerLocation
		 * = (double)verticalJSplitPane.getWidth() /
		 * (verticalJSplitPane.getDividerLocation() -
		 * verticalJSplitPane.getDividerSize()); } } });
		 * 
		 * horizontalJSplitPane.addPropertyChangeListener(new
		 * PropertyChangeListener() {
		 * 
		 * @Override public void propertyChange(PropertyChangeEvent evt) { if
		 * (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY))
		 * { //verticalJSplitPane.getDividerLocation();
		 * horizontalDividerLocation = (double)horizontalJSplitPane.getHeight()
		 * / (horizontalJSplitPane.getDividerLocation() -
		 * horizontalJSplitPane.getDividerSize()); } } });
		 */

	}
	
	/**
	 * ����ȫ������ �˷���Ӧ��swing�������ڽ��洦����
	 * 
	 * @param fnt Ĭ������
	 */
	public static void initGlobalFontSetting(Font fnt) {
		FontUIResource fontRes = new FontUIResource(fnt);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource)
				UIManager.put(key, fontRes);
		}
		/*
		 * ������ʽ:�������� Font font = new Font("Dialog",Font.PLAIN,12);
		 * UIManager.put("ToolTip.font",font); UIManager.put("Table.font",font);
		 * UIManager.put("TableHeader.font",font);
		 * UIManager.put("TextField.font",font);
		 * UIManager.put("ComboBox.font",font);
		 * UIManager.put("TextField.font",font);
		 * UIManager.put("PasswordField.font",font);
		 * UIManager.put("TextArea.font",font);
		 * UIManager.put("TextPane.font",font);
		 * UIManager.put("EditorPane.font",font);
		 * UIManager.put("FormattedTextField.font",font);
		 * UIManager.put("Button.font",font);
		 * UIManager.put("CheckBox.font",font);
		 * UIManager.put("RadioButton.font",font);
		 * UIManager.put("ToggleButton.font",font);
		 * UIManager.put("ProgressBar.font",font);
		 * UIManager.put("DesktopIcon.font",font);
		 * UIManager.put("TitledBorder.font",font);
		 * UIManager.put("Label.font",font); UIManager.put("List.font",font);
		 * UIManager.put("TabbedPane.font",font);
		 * UIManager.put("MenuBar.font",font); UIManager.put("Menu.font",font);
		 * UIManager.put("MenuItem.font",font);
		 * UIManager.put("PopupMenu.font",font);
		 * UIManager.put("CheckBoxMenuItem.font",font);
		 * UIManager.put("RadioButtonMenuItem.font",font);
		 * UIManager.put("Spinner.font",font); UIManager.put("Tree.font",font);
		 * UIManager.put("ToolBar.font",font);
		 * UIManager.put("OptionPane.messageFont",font);
		 * UIManager.put("OptionPane.buttonFont",font);
		 */
	}
	
	
	public ViewsModular getViewsModular() {
		return viewsModular;
	}

	public void setViewsModular(ViewsModular viewsModular) {
		this.viewsModular = viewsModular;
	}

	public Workplace getWorkplace() {
		return workplace;
	}

	public void setWorkplace(Workplace workplace) {
		this.workplace = workplace;
	}

	/**
	 * Getters and Setters 
	 */
				
	public JMenuBar getjMenuBar() {
		return jMenuBar;
	}

	public void setjMenuBar(JMenuBar jMenuBar) {
		this.jMenuBar = jMenuBar;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(StatusBar statusBar) {
		this.statusBar = statusBar;
	}

	public int getNewFileCounter() {
		return newFileCounter;
	}

	public void setNewFileCounter(int newFileCounter) {
		this.newFileCounter = newFileCounter;
	}
}
