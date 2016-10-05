package com.codeaftercode.file;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import org.mozilla.universalchardet.UniversalDetector;

import com.codeaftercode.coding.Hex;
import com.codeaftercode.ui.LineNumberHeaderView;
import com.codeaftercode.ui.MyJMenu;
import com.codeaftercode.ui.MyJMenuItem;
import com.codeaftercode.ui.Window;

public class FileModular {
	private Window window;
	private MyJMenu filesMenu;
	private MyJMenu newFileMenu;
	protected MyJMenuItem newTextMenuItem;
	protected MyJMenuItem openMenuItem;
	protected MyJMenuItem openFolderMenuItem;
	protected MyJMenu openRecentMenu;
	protected MyJMenuItem saveMenuItem;
	protected MyJMenuItem saveWithEncodingMenuItem;
	protected MyJMenuItem saveAsMenuItem;
	protected MyJMenuItem saveAllMenuItem;
	protected MyJMenuItem closeFileMenuItem;
	protected MyJMenuItem closeAllFileMenuItem;
	protected MyJMenuItem exitMenuItem;

	public FileModular(Window window) {
		this.window = window;
		// 注册监听
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		// 初始化菜单
		filesMenu = new MyJMenu("文件(F)");

		newFileMenu = new MyJMenu("新建(N)", new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("普通纯文本文件", new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem = new MyJMenuItem("打开文件(O)...");
		openFolderMenuItem = new MyJMenuItem("打开文件夹...");
		openRecentMenu = new MyJMenu("Open recent...");
		saveMenuItem = new MyJMenuItem("保存(S)");
		saveWithEncodingMenuItem = new MyJMenuItem("Save with Encoding");
		saveAsMenuItem = new MyJMenuItem("另存为(A)...");
		saveAllMenuItem = new MyJMenuItem("全部保存");
		closeFileMenuItem = new MyJMenuItem("关闭");
		closeAllFileMenuItem = new MyJMenuItem("全部关闭");
		exitMenuItem = new MyJMenuItem("退出(X)");

		// 助记符
		filesMenu.setMnemonic('F');

		// 菜单绑定菜单项
		filesMenu.add(newFileMenu);
		newFileMenu.add(newTextMenuItem);
		filesMenu.add(openMenuItem);
		filesMenu.add(openFolderMenuItem);
		filesMenu.add(openRecentMenu);
		filesMenu.add(saveMenuItem);
		filesMenu.add(saveWithEncodingMenuItem);
		filesMenu.add(saveAsMenuItem);
		filesMenu.add(saveAllMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(closeFileMenuItem);
		filesMenu.add(closeAllFileMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(exitMenuItem);

		// 向监听器注册事件
		newTextMenuItem.addActionListener(fileModularActionListener);
		openMenuItem.addActionListener(fileModularActionListener);
		openFolderMenuItem.addActionListener(fileModularActionListener);
		saveMenuItem.addActionListener(fileModularActionListener);
		saveWithEncodingMenuItem.addActionListener(fileModularActionListener);
		saveAsMenuItem.addActionListener(fileModularActionListener);
		saveAllMenuItem.addActionListener(fileModularActionListener);
		closeFileMenuItem.addActionListener(fileModularActionListener);
		closeAllFileMenuItem.addActionListener(fileModularActionListener);
		exitMenuItem.addActionListener(fileModularActionListener);

		// 向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(filesMenu);

	}

	protected void newText() {
		// 新建纯文本文件
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		jTextPane.setBackground(new Color(0x272822));
		jTextPane.setForeground(new Color(0xFFFFFF));
		jTextPane.setCaretColor(new Color(0xFFFFFF));
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		// 选项卡显示的文件名称
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "新的文档" + window.getNewFileCounter() + ".txt";
		// 新建选项卡
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		// 显示行号
		jScrollPane.setRowHeaderView(new LineNumberHeaderView(new Font("Consolas", Font.BOLD, 18)));
	}


	/**
	 * 打开文件
	 * 调用系统文件选择器选择文件,根据文件类型调用对应的方法打开文件
	 */
	protected void openFile(){
		// 选择文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// 只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// 取消打开操作
		if (file == null || !file.isFile()) {
			return;
		}
		
		// 选择的文件已经打开
		// 检测window.getWorkplace().getsTabbedPane()中有没有这个文件,如果有,则不要重复打开,只需切换到此选项卡即可
		//window.getWorkplace().getsTabbedPane().getComponents().getClass();
		
			
		/** 配置文本显示容器 **/
		String title = file.getName();
		String fileType = title.substring(title.lastIndexOf(".")).toLowerCase();
		// 配置JTextPane:字体,背景色,前景色,光标颜色,光标初始位置
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		jTextPane.setBackground(new Color(0x272822));
		jTextPane.setForeground(new Color(0xFFFFFF));
		jTextPane.setCaretColor(new Color(0xFFFFFF));
		jTextPane.setCaretPosition(0);
		// 配置滚动面板,添加行号组件
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());
		// 配置选项卡,切换到新选项卡
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		
		/** 读取文件并显示 **/
		// 如果不把打开文件的处理代码放在一个单独的线程中，打开大文件时界面停止一切响应，直到方法执行完毕。
		// 单独建立线程的好处是允许用户随时停止该耗时的操作，使界面更加友好。
		switch (fileType) {
		case ".txt":
		case ".java":
		case ".ini":
		case ".inf":
		case ".h":
		case ".c":
		case ".cpp":
		case ".bat":
		case ".md":
		case ".markdown":
		case ".css":
		case ".js":
		case ".html":
		case ".htm":
		case ".php":
		case ".asp":
		case ".asm":// 汇编
		case ".json":
		case ".xml":
		case ".lock":
		case ".yml":
		case ".gitignore":
		case ".gitattributes":
			new Thread() {
				public void run() {
					openText(file, jTextPane);
				}
			}.start();
			break;
		default:
			// 无法识别的文件类型,用16进制查看器打开
			new Thread() {
				public void run() {
					openHexFile(file, jTextPane);
				}
			}.start();
			break;
		}
	}
	/**
	 * 文本文件编辑器
	 * @param file 文件对象
	 * @param jTextPane 文件显示容器
	 */
	public void openText(File file, JTextPane jTextPane) {
		// 创建字节数组buf[],大小为文件字节数file.length();
		UniversalDetector detector = new UniversalDetector(null);
		String encoding = "";
		int len = (int) file.length();
		byte[] buf = new byte[len];
		// 字节流打开文件,一次性读取到字节数组buf[]
		try (FileInputStream fis = new FileInputStream(file);) {
			fis.read(buf);
			// 用juniversalchardet-1.0.3.jar获取编码encoding
			detector.handleData(buf, 0, len);
			detector.dataEnd();
			encoding = detector.getDetectedCharset();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果编码检测失败,则指定为默认字符集
		encoding = (encoding != null) ? encoding : window.getCharset();
		// 状态栏
		window.getStatusBar().getLabel().setText("字符集:" + encoding);

		try {
			//jTextPane.replaceSelection(new String(buf, encoding));	此方法太慢
			jTextPane.setText(new String(buf, encoding));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 16进制查看器
	 * @param file 文件对象
	 * @param jTextPane 文件显示容器
	 */
	public void openHexFile(File file, JTextPane jTextPane) {
		// 打开16进制文件
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] cbuf = new byte[(int) file.length()];
			fis.read(cbuf);
			// 设置一个等宽字体
			jTextPane.setFont(new Font("Consolas", Font.BOLD, 18));
			//jTextPane.replaceSelection(Hex.toHexString(cbuf, 4, ' '));
			jTextPane.setText(Hex.toHexString(cbuf, 4, ' '));
			// 设置光标停留位置
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyJMenu getFilesMenu() {
		return filesMenu;
	}

	public void setFilesMenu(MyJMenu filesMenu) {
		this.filesMenu = filesMenu;
	}

	public void openFolder() {
		// 打开文件夹
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();

		// 取消打开操作
		if (folder == null || !folder.isDirectory()) {
			return;
		}

		// 状态栏:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());

		// 打开并显示文件夹
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}

	public void saveFile() {
		// 保存文件
		
	}

	public void saveAs() {
		// 文件另存为
		// 写入准备:字符串,编码
		
		// 写入准备:确定文件路径,文件名
		
		// 写入文件
		
	}
}
