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
 * 窗体类
 */
public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//默认字体
	private Font globalFont = new Font("微软雅黑",Font.BOLD,16);
	private Font workspaceFont = new Font("Consolas",Font.BOLD,18);
	//默认编码表
	private String charset = System.getProperty("file.encoding");

	//默认分栏分隔符宽度
	public static int DividerSize = 5;
	//默认垂直分栏(左右分栏)比例
	public static double verticalDividerLocation = 0.8;
	//默认水平分栏(上下分栏)比例
	public static double horizontalDividerLocation = 0.2;
	//图标路径
	String iconsPath = this.getClass().getResource("/").getFile() + "icons//";
	//窗体标题
	String name;
	int width = 800;
	int height = 800;
	private int newFileCounter = 0;

	
	//事件监听器
	public static WindowActionListener windowActionListener;
	/***********主工作区*************/
	//文本编辑区:
	//JTextPane jTextPane;
	//JScrollPane jScrollPane;
	STabbedPane sTabbedPane;
	//命令行:
	JTextPane consoleJPanel;
	JScrollPane consoleJScrollPane;
	//左侧:
	JPanel leftPanel;
	//分栏:
	JSplitPane verticalJSplitPane;//上下分栏,上为文本编辑区,下为命令行
	JSplitPane horizontalJSplitPane;//左右分栏,左为左侧,右为verticalJSplitPane
	
	/***********工具栏*************/
	//工具栏:未定义
	/***********菜单栏*************/
	//菜单栏
	JMenuBar jMenuBar;
	//菜单:
	MyJMenu filesMenu;
	MyJMenu editMenu;
	MyJMenu toolsMenu;
	MyJMenu viewMenu;
	MyJMenu helpMenu;
	//菜单项:
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
	
	
	//初始化窗体
	Window(String name) {
		super(name);
		//设置窗体标题
		this.name = name;
		//设置窗体图标
		this.setIconImage(new ImageIcon(iconsPath + "newFile.jpg").getImage());

		//让程序界面显示在屏幕中央
		int x,y;
		Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
		x=(size.width-this.width)/2;
		y=(size.height-this.height)/2;
		setSize(this.width, this.height);
		setLocation(x,y);
		//设置窗口最小尺寸
		//setMinimumSize(new Dimension(250,150));
		//设置关闭按钮
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	//初始化布局
	void init() {
		//设置全局字体
		initGlobalFontSetting(globalFont);
		//设置监听
		//myActionListener = new MyActionListener();
		windowActionListener = new WindowActionListener(this); 
		
		/***********菜单栏*************/
		//菜单栏
		jMenuBar = new JMenuBar();
		//菜单:
		filesMenu=new MyJMenu("文件(F)");
		editMenu=new MyJMenu("编辑(E)");
		toolsMenu=new MyJMenu("工具(T)");
		viewMenu = new MyJMenu("视图(V)");
		helpMenu=new MyJMenu("帮助(H)");
		//菜单项:
		newFileMenu=new MyJMenu("新建(N)",new ImageIcon(iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("普通纯文本文件",new ImageIcon(iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("打开(O)...");
		saveMenuItem=new MyJMenuItem("保存(S)");
		saveAsMenuItem=new MyJMenuItem("另存为(A)...");
		exitMenuItem=new MyJMenuItem("退出(X)");

		copyMenuItem=new MyJMenuItem("复制(C)");
		cutMenuItem=new MyJMenuItem("剪切(T)");
		pasteMenuItem=new MyJMenuItem("粘贴(P)");
		selectAllMenuItem=new MyJMenuItem("全选(A)");

		highlightMenuItem=new MyJMenuItem("高亮显示html标签");
		checkMenuItem=new MyJMenuItem("纠错");
		
		showViewMenu = new MyJMenu("显示视图");
		showLeftCheckBoxMenuItem = new MyJCheckBoxMenuItem("文件夹");
		//showTextCheckBoxMenuItem = new MyJCheckBoxMenuItem("工作区");
		showConsoleCheckBoxMenuItem = new MyJCheckBoxMenuItem("命令行");
		
		aboutMenuItem = new MyJMenuItem("关于html检查器(A)");
		
		
		//添加助计符:
		filesMenu.setMnemonic('F');
		editMenu.setMnemonic('E');
		toolsMenu.setMnemonic('T');
		viewMenu.setMnemonic('V');
		helpMenu.setMnemonic('H');
		/*写一个专门处理键盘事件的方法,好处是即使菜单项不存在,也可以监听到键盘事件.
		//添加快捷键:
		//newFileMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));		//Ctrl+N新建
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));	//Ctrl+O打开
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));	//Ctrl+S保存
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK));	//Alt+F4退出
		copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));	//Ctrl+C复制
		cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));		//Ctrl+X剪切
		pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK));	//Ctrl+V粘贴
		selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));//Ctrl+A全选
		*/
		
		//绑定菜单项:
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
		
		//绑定菜单栏按钮:
		jMenuBar.add(filesMenu);
		jMenuBar.add(editMenu);
		jMenuBar.add(toolsMenu);
		jMenuBar.add(viewMenu);
		jMenuBar.add(helpMenu);
		
		//设置窗体菜单栏
		setJMenuBar(jMenuBar);

		//设置监听
		//文件:
		newTextMenuItem.addActionListener(windowActionListener);
		openMenuItem.addActionListener(windowActionListener);
		//编辑:
		//工具:
		//视图:
		showLeftCheckBoxMenuItem.addActionListener(windowActionListener);
		showConsoleCheckBoxMenuItem.addActionListener(windowActionListener);
		//帮助:
		
		
		/***********主工作区*************/
		//文本编辑区:
		sTabbedPane = new STabbedPane();
		//命令行:
		consoleJPanel = new JTextPane();
		consoleJScrollPane = new JScrollPane(consoleJPanel);
		//左侧:
		leftPanel = new JPanel();
		//分栏:
		verticalJSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sTabbedPane,consoleJScrollPane);//上下分栏,上为文本编辑区,下为命令行
		horizontalJSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,verticalJSplitPane);//左右分栏,左为左侧,右为jSplitPane1
		
		//设置分栏
		this.add(horizontalJSplitPane);
		verticalJSplitPane.setDividerSize(Window.DividerSize);
		horizontalJSplitPane.setDividerSize(Window.DividerSize);
		showLeft();
		showConsole();
		
		//监听改变窗体大小事件
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				//窗体大小改变时,设置垂直分隔(左右分栏)比例
				verticalJSplitPane.setDividerLocation(Window.verticalDividerLocation);
				//窗体大小改变时,设置水平分隔(上下分栏)比例
				horizontalJSplitPane.setDividerLocation(Window.horizontalDividerLocation);
			}
		});
		/*//监听分栏比例改变事件,保存新的比例:
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
	 * 设置全局字体
	 * 此方法应在swing界面的入口界面处调用
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
	    /* 其它方式:依次设置
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
		//显示:命令行
		if(!consoleJScrollPane.isVisible()) {
			//设置为命令行可见
			consoleJScrollPane.setVisible(true);
			//设置上下分栏比例
			verticalJSplitPane.setDividerLocation(Window.verticalDividerLocation);
			//设置分隔线宽度
			verticalJSplitPane.setDividerSize(Window.DividerSize);
		}else if(!showConsoleCheckBoxMenuItem.getState()) {
			//设置为命令行不可见
			consoleJScrollPane.setVisible(false);
			verticalJSplitPane.setDividerSize(0);
		}
	}

	protected void showLeft() {
		//显示:文件夹
		if(!leftPanel.isVisible()) {
			//设置为左侧可见
			leftPanel.setVisible(true);
			//设置左右分栏比例
			horizontalJSplitPane.setDividerLocation(Window.horizontalDividerLocation);
			//设置分隔线宽度
			horizontalJSplitPane.setDividerSize(Window.DividerSize);
		}else if(!showLeftCheckBoxMenuItem.getState()) {
			//设置为左侧不可见
			leftPanel.setVisible(false);
			horizontalJSplitPane.setDividerSize(0);
		}
	}
	protected void newText() {
		//新建纯文本文件					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(workspaceFont);
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		newFileCounter++;
		String title = "新的文档" + newFileCounter + ".txt";
		sTabbedPane.addTab(title, null, jScrollPane, title, true);
		sTabbedPane.setSelectedComponent(jScrollPane);
	}


	protected void openFile() throws IOException {
		// 打开文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//只能选择文件,不能选文件夹
		jfc.showOpenDialog(this);
		File file = jfc.getSelectedFile();
		// 新建选项卡
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(workspaceFont);
		String title = file.getName();
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		sTabbedPane.addTab(title, null, jScrollPane, title, true);
		sTabbedPane.setSelectedComponent(jScrollPane);
		//使用系统默认字符集读取并显示文件
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
