package com.codeaftercode.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
/**
 * 
 * @author Codeaftercode
 * ������
 */
public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Ĭ������
	private Font globalFont = new Font("΢���ź�",Font.BOLD,16);
	private Font workspaceFont = new Font("Consolas",Font.BOLD,18);
	//Ĭ�ϱ����
	private String charset = System.getProperty("file.encoding");

	//Ĭ�Ϸ����ָ������
	public static int DividerSize = 5;
	//Ĭ�ϴ�ֱ����(���ҷ���)����
	public static double verticalDividerLocation = 0.8;
	//Ĭ��ˮƽ����(���·���)����
	public static double horizontalDividerLocation = 0.2;
	//ͼ��·��
	String iconsPath = this.getClass().getResource("/").getFile() + "icons//";
	//�������
	String name;
	int width = 800;
	int height = 800;
	private int newFileCounter = 0;

	
	//�¼�������
	public static WindowActionListener windowActionListener;
	/***********��������*************/
	//�ı��༭��:
	//JTextPane jTextPane;
	//JScrollPane jScrollPane;
	STabbedPane sTabbedPane;
	//������:
	JTextPane consoleJPanel;
	JScrollPane consoleJScrollPane;
	//���:
	JPanel leftPanel;
	//����:
	JSplitPane verticalJSplitPane;//���·���,��Ϊ�ı��༭��,��Ϊ������
	JSplitPane horizontalJSplitPane;//���ҷ���,��Ϊ���,��ΪverticalJSplitPane
	
	/***********������*************/
	//������:δ����
	/***********�˵���*************/
	//�˵���
	JMenuBar jMenuBar;
	//�˵�:
	MyJMenu filesMenu;
	MyJMenu editMenu;
	MyJMenu toolsMenu;
	MyJMenu viewMenu;
	MyJMenu helpMenu;
	//�˵���:
	MyJMenu newFileMenu;
	MyJMenuItem newTextMenuItem;
	MyJMenuItem openMenuItem;
	MyJMenuItem saveMenuItem;
	MyJMenuItem saveAsMenuItem;
	MyJMenuItem exitMenuItem;

	MyJMenuItem copyMenuItem;
	MyJMenuItem cutMenuItem;
	MyJMenuItem pasteMenuItem;
	MyJMenuItem selectAllMenuItem;

	MyJMenuItem highlightMenuItem;
	MyJMenuItem checkMenuItem;
	
	MyJMenu showViewMenu;
	MyJCheckBoxMenuItem showLeftCheckBoxMenuItem;
	//MyJCheckBoxMenuItem showTextCheckBoxMenuItem;
	MyJCheckBoxMenuItem showConsoleCheckBoxMenuItem;
	
	MyJMenuItem aboutMenuItem;
	
	
	//��ʼ������
	Window(String name) {
		super(name);
		//���ô������
		this.name = name;
		//���ô���ͼ��
		this.setIconImage(new ImageIcon(iconsPath + "newFile.jpg").getImage());

		//�ó��������ʾ����Ļ����
		int x,y;
		Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
		x=(size.width-this.width)/2;
		y=(size.height-this.height)/2;
		setSize(this.width, this.height);
		setLocation(x,y);
		//���ô�����С�ߴ�
		//setMinimumSize(new Dimension(250,150));
		//���ùرհ�ť
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	//��ʼ������
	void init() {
		//����ȫ������
		initGlobalFontSetting(globalFont);
		//���ü���
		//myActionListener = new MyActionListener();
		windowActionListener = new WindowActionListener(this); 
		
		/***********�˵���*************/
		//�˵���
		jMenuBar = new JMenuBar();
		//�˵�:
		filesMenu=new MyJMenu("�ļ�(F)");
		editMenu=new MyJMenu("�༭(E)");
		toolsMenu=new MyJMenu("����(T)");
		viewMenu = new MyJMenu("��ͼ(V)");
		helpMenu=new MyJMenu("����(H)");
		//�˵���:
		newFileMenu=new MyJMenu("�½�(N)",new ImageIcon(iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("��ͨ���ı��ļ�",new ImageIcon(iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("��(O)...");
		saveMenuItem=new MyJMenuItem("����(S)");
		saveAsMenuItem=new MyJMenuItem("���Ϊ(A)...");
		exitMenuItem=new MyJMenuItem("�˳�(X)");

		copyMenuItem=new MyJMenuItem("����(C)");
		cutMenuItem=new MyJMenuItem("����(T)");
		pasteMenuItem=new MyJMenuItem("ճ��(P)");
		selectAllMenuItem=new MyJMenuItem("ȫѡ(A)");

		highlightMenuItem=new MyJMenuItem("������ʾhtml��ǩ");
		checkMenuItem=new MyJMenuItem("����");
		
		showViewMenu = new MyJMenu("��ʾ��ͼ");
		showLeftCheckBoxMenuItem = new MyJCheckBoxMenuItem("�ļ���");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("������");
		
		aboutMenuItem = new MyJMenuItem("����html�����(A)");
		
		
		//������Ʒ�:
		filesMenu.setMnemonic('F');
		editMenu.setMnemonic('E');
		toolsMenu.setMnemonic('T');
		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');
		/*дһ��ר�Ŵ�������¼��ķ���,�ô��Ǽ�ʹ�˵������,Ҳ���Լ����������¼�.
		//��ӿ�ݼ�:
		//newFileMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));		//Ctrl+N�½�
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));	//Ctrl+O��
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));	//Ctrl+S����
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK));	//Alt+F4�˳�
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));	//Ctrl+C����
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));		//Ctrl+X����
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));	//Ctrl+Vճ��
		selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));//Ctrl+Aȫѡ
		*/
		
		//�󶨲˵���:
		filesMenu.add(newFileMenu);
		newFileMenu.add(newTextMenuItem);

		filesMenu.add(openMenuItem);
		filesMenu.add(saveMenuItem);
		filesMenu.add(saveAsMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(exitMenuItem);
		
		editMenu.add(copyMenuItem);
		editMenu.add(cutMenuItem);
		editMenu.add(pasteMenuItem);
		editMenu.addSeparator();
		editMenu.add(selectAllMenuItem);
		
		toolsMenu.add(highlightMenuItem);
		toolsMenu.add(checkMenuItem);
		
		viewMenu.add(showViewMenu);
		showViewMenu.add(showLeftCheckBoxMenuItem);
		//showViewMenu.add(showTextCheckBoxMenuItem);
		showViewMenu.add(showConsoleCheckBoxMenuItem);
		showLeftCheckBoxMenuItem.setState(false);
		showConsoleCheckBoxMenuItem.setState(false);
		helpMenu.add(aboutMenuItem);
		
		//�󶨲˵�����ť:
		jMenuBar.add(filesMenu);
		jMenuBar.add(editMenu);
		jMenuBar.add(toolsMenu);
		jMenuBar.add(viewMenu);
		jMenuBar.add(helpMenu);
		
		//���ô���˵���
		setJMenuBar(jMenuBar);

		//���ü���
		//�ļ�:
		newTextMenuItem.addActionListener(windowActionListener);
		openMenuItem.addActionListener(windowActionListener);
		//�༭:
		//����:
		//��ͼ:
		showLeftCheckBoxMenuItem.addActionListener(windowActionListener);
		showConsoleCheckBoxMenuItem.addActionListener(windowActionListener);
		//����:
		
		
		/***********��������*************/
		//�ı��༭��:
		sTabbedPane = new STabbedPane();
		//������:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		//���:
		leftPanel = new JPanel();
		//����:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sTabbedPane,consoleJScrollPane);//���·���,��Ϊ�ı��༭��,��Ϊ������
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,verticalJSplitPane);//���ҷ���,��Ϊ���,��ΪjSplitPane1
		
		//���÷���
		this.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(Window.DividerSize);
		horizontalJSplitPane.setDividerSize(Window.DividerSize);
		showLeft();
		showConsole();
		
		//�����ı䴰���С�¼�
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				//�����С�ı�ʱ,���ô�ֱ�ָ�(���ҷ���)����
				verticalJSplitPane.setDividerLocation(Window.verticalDividerLocation);
				//�����С�ı�ʱ,����ˮƽ�ָ�(���·���)����
				horizontalJSplitPane.setDividerLocation(Window.horizontalDividerLocation);
			}
		});
		/*//�������������ı��¼�,�����µı���:
		verticalJSplitPane.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                	//verticalJSplitPane.getDividerLocation();
                	verticalDividerLocation = (double)verticalJSplitPane.getWidth() / (verticalJSplitPane.getDividerLocation() - verticalJSplitPane.getDividerSize());
                }
			}
		});
		
		horizontalJSplitPane.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                	//verticalJSplitPane.getDividerLocation();
                	horizontalDividerLocation = (double)horizontalJSplitPane.getHeight() / (horizontalJSplitPane.getDividerLocation() - horizontalJSplitPane.getDividerSize());
                }
			}
		});*/

	}
	/**
	 * ����ȫ������
	 * �˷���Ӧ��swing�������ڽ��洦����
	 * @param fnt
	 */
	public static void initGlobalFontSetting(Font fnt){
	    FontUIResource fontRes = new FontUIResource(fnt);
	    for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if(value instanceof FontUIResource)
	            UIManager.put(key, fontRes);
	    }
	    /* ������ʽ:��������
	    Font font = new Font("Dialog",Font.PLAIN,12);
	    UIManager.put("ToolTip.font",font);
	    UIManager.put("Table.font",font);
	    UIManager.put("TableHeader.font",font); 
	    UIManager.put("TextField.font",font); 
	    UIManager.put("ComboBox.font",font); 
	    UIManager.put("TextField.font",font); 
	    UIManager.put("PasswordField.font",font); 
	    UIManager.put("TextArea.font",font); 
	    UIManager.put("TextPane.font",font); 
	    UIManager.put("EditorPane.font",font); 
	    UIManager.put("FormattedTextField.font",font); 
	    UIManager.put("Button.font",font); 
	    UIManager.put("CheckBox.font",font); 
	    UIManager.put("RadioButton.font",font); 
	    UIManager.put("ToggleButton.font",font); 
	    UIManager.put("ProgressBar.font",font); 
	    UIManager.put("DesktopIcon.font",font); 
	    UIManager.put("TitledBorder.font",font); 
	    UIManager.put("Label.font",font); 
	    UIManager.put("List.font",font); 
	    UIManager.put("TabbedPane.font",font); 
	    UIManager.put("MenuBar.font",font); 
	    UIManager.put("Menu.font",font); 
	    UIManager.put("MenuItem.font",font); 
	    UIManager.put("PopupMenu.font",font); 
	    UIManager.put("CheckBoxMenuItem.font",font); 
	    UIManager.put("RadioButtonMenuItem.font",font); 
	    UIManager.put("Spinner.font",font); 
	    UIManager.put("Tree.font",font); 
	    UIManager.put("ToolBar.font",font); 
	    UIManager.put("OptionPane.messageFont",font); 
	    UIManager.put("OptionPane.buttonFont",font); */
	}
	protected void showConsole() {
		//��ʾ:������
		if(!consoleJScrollPane.isVisible()) {
			//����Ϊ�����пɼ�
			consoleJScrollPane.setVisible(true);
			//�������·�������
			verticalJSplitPane.setDividerLocation(Window.verticalDividerLocation);
			//���÷ָ��߿��
			verticalJSplitPane.setDividerSize(Window.DividerSize);
		}else if(!showConsoleCheckBoxMenuItem.getState()) {
			//����Ϊ�����в��ɼ�
			consoleJScrollPane.setVisible(false);
			verticalJSplitPane.setDividerSize(0);
		}
	}

	protected void showLeft() {
		//��ʾ:�ļ���
		if(!leftPanel.isVisible()) {
			//����Ϊ���ɼ�
			leftPanel.setVisible(true);
			//�������ҷ�������
			horizontalJSplitPane.setDividerLocation(Window.horizontalDividerLocation);
			//���÷ָ��߿��
			horizontalJSplitPane.setDividerSize(Window.DividerSize);
		}else if(!showLeftCheckBoxMenuItem.getState()) {
			//����Ϊ��಻�ɼ�
			leftPanel.setVisible(false);
			horizontalJSplitPane.setDividerSize(0);
		}
	}
	protected void newText() {
		//�½����ı��ļ�					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(workspaceFont);
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		newFileCounter++;
		String title = "�µ��ĵ�" + newFileCounter + ".txt";
		sTabbedPane.addTab(title, null, jScrollPane, title, true);
		sTabbedPane.setSelectedComponent(jScrollPane);
	}


	protected void openFile() throws IOException {
		// ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(this);
		File file = jfc.getSelectedFile();
		// �½�ѡ�
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(workspaceFont);
		String title = file.getName();
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		sTabbedPane.addTab(title, null, jScrollPane, title, true);
		sTabbedPane.setSelectedComponent(jScrollPane);
		//ʹ��ϵͳĬ���ַ�����ȡ����ʾ�ļ�
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));) {
			int ch;
			StringBuilder sb = new StringBuilder("");
			while((ch = br.read()) != -1) {
				sb = sb.append((char)ch);
			}
			jTextPane.setText(sb.toString());
		}
	}
	public Font getGlobalFont() {
		return globalFont;
	}


	public void setGlobalFont(Font globalFont) {
		this.globalFont = globalFont;
	}


	public Font getWorkspaceFont() {
		return workspaceFont;
	}


	public void setWorkspaceFont(Font workspaceFont) {
		this.workspaceFont = workspaceFont;
	}
	public int getNewFileCounter() {
		return newFileCounter;
	}


	public void setNewFileCounter(int newFileCounter) {
		this.newFileCounter = newFileCounter;
	}
}
