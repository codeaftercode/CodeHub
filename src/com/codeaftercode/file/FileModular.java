package com.codeaftercode.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

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
	protected MyJMenuItem openBigFileMenuItem;
	protected MyJMenuItem saveMenuItem;
	protected MyJMenuItem saveAsMenuItem;
	protected MyJMenuItem exitMenuItem;
	
	public FileModular(Window window) {
		this.window = window;
		//注册监听
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		//初始化菜单
		filesMenu=new MyJMenu("文件(F)");
		
		newFileMenu=new MyJMenu("新建(N)",new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("普通纯文本文件",new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("打开(O)...");
		openBigFileMenuItem=new MyJMenuItem("打开大文件");
		saveMenuItem=new MyJMenuItem("保存(S)");
		saveAsMenuItem=new MyJMenuItem("另存为(A)...");
		exitMenuItem=new MyJMenuItem("退出(X)");

		//助记符
		filesMenu.setMnemonic('F');
		
		//菜单绑定菜单项
		filesMenu.add(newFileMenu);
		newFileMenu.add(newTextMenuItem);

		filesMenu.add(openMenuItem);
		filesMenu.add(openBigFileMenuItem);
		filesMenu.add(saveMenuItem);
		filesMenu.add(saveAsMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(exitMenuItem);
		

		//向监听器注册事件
		newTextMenuItem.addActionListener(fileModularActionListener);
		openMenuItem.addActionListener(fileModularActionListener);
		openBigFileMenuItem.addActionListener(fileModularActionListener);
		
		//向窗体的JMenuBar添加本菜单
		window.getJMenuBar().add(filesMenu);
		
	}
	
	protected void newText() {
		//新建纯文本文件					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getWorkspaceFont());
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "新的文档" + window.getNewFileCounter() + ".txt";
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
	}
	
	protected void openFile() throws IOException {
		// 打开文件
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//只能选择文件,不能选文件夹
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();
		String title = file.getName();
		JScrollPane jScrollPane;
		//读取文件
		if(file.isFile() && title.toLowerCase().endsWith(".txt")) {
			JTextArea jTextArea = new JTextArea();
			//jTextArea.setFont(window.getWorkspaceFont());
			jScrollPane = new JScrollPane(jTextArea);
			window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
			window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
			openText(file, jTextArea);
			jScrollPane.setRowHeaderView(new LineNumberHeaderView());
		}
		// 新建选项卡
		//JTextPane jTextPane = new JTextPane();
		//jTextPane.setFont(window.getWorkspaceFont());
		//JScrollPane jScrollPane = new JScrollPane(jTextPane);
		//
		//使用系统默认字符集读取并显示文件
		double start;
		start = System.currentTimeMillis();
		/*try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			int ch;
			StringBuilder sb = new StringBuilder("");
			while((ch = br.read()) != -1) {
				sb = sb.append((char)ch);
			}
			double time1 = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();
			//jTextPane.setText(sb.toString());
			jTextPane.replaceSelection(sb.toString());
			double time2 = System.currentTimeMillis() - start;
			window.getWorkplace().getConsoleJPanel().setText(Double.toString(time1) + "\t" + Double.toString(time2));
			
		}*/
/*		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			while(br.read(cbuf) >0 ) {
				;
			}
			br.read(cbuf);
			double time1 = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();

			jTextPane.replaceSelection(new String(cbuf,0,cbuf.length));
			jTextPane.setBorder(new LineNumberBorder(5));
			//jTextPane.setText(String text);//速度太慢
			double time2 = System.currentTimeMillis() - start;
			window.getWorkplace().getConsoleJPanel().replaceSelection(Double.toString(time1) + "\t" + Double.toString(time2));
		}*/

	}
	
	public void openText(File file, JTextArea jTextArea) throws FileNotFoundException, IOException {
		/*double start;
		start = System.currentTimeMillis();
		try(RandomAccessFile raf = new RandomAccessFile(file, "r");) {
			byte[] cbuf = new byte[(int)raf.length()];
			raf.seek(0);
			raf.readFully(cbuf);
			//s=new String(s.getBytes("8859_1"), "gbk");//编码转换
			double time1 = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();
			jTextArea.replaceSelection(new String(cbuf,0,cbuf.length,window.getCharset()));
			jTextArea.setBorder(new LineNumberBorder());
			double time2 = System.currentTimeMillis() - start;
			window.getWorkplace().getConsoleJPanel().replaceSelection("|" + Double.toString(time1) + "\t" + Double.toString(time2));
		}*/
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			while(br.read(cbuf) >0 ) {
				;
			}
			br.read(cbuf);

			jTextArea.replaceSelection(new String(cbuf,0,cbuf.length));
			//jTextArea.setText(new String(cbuf,0,cbuf.length));
			//jTextArea.setBorder(new LineNumberBorder(jTextArea));
		}
	}
	
	public MyJMenu getFilesMenu() {
		return filesMenu;
	}

	public void setFilesMenu(MyJMenu filesMenu) {
		this.filesMenu = filesMenu;
	}	
}
