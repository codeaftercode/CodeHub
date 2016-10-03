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
		//ע�����
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		//��ʼ���˵�
		filesMenu=new MyJMenu("�ļ�(F)");
		
		newFileMenu=new MyJMenu("�½�(N)",new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("��ͨ���ı��ļ�",new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("��(O)...");
		openBigFileMenuItem=new MyJMenuItem("�򿪴��ļ�");
		saveMenuItem=new MyJMenuItem("����(S)");
		saveAsMenuItem=new MyJMenuItem("���Ϊ(A)...");
		exitMenuItem=new MyJMenuItem("�˳�(X)");

		//���Ƿ�
		filesMenu.setMnemonic('F');
		
		//�˵��󶨲˵���
		filesMenu.add(newFileMenu);
		newFileMenu.add(newTextMenuItem);

		filesMenu.add(openMenuItem);
		filesMenu.add(openBigFileMenuItem);
		filesMenu.add(saveMenuItem);
		filesMenu.add(saveAsMenuItem);
		filesMenu.addSeparator();
		filesMenu.add(exitMenuItem);
		

		//�������ע���¼�
		newTextMenuItem.addActionListener(fileModularActionListener);
		openMenuItem.addActionListener(fileModularActionListener);
		openBigFileMenuItem.addActionListener(fileModularActionListener);
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(filesMenu);
		
	}
	
	protected void newText() {
		//�½����ı��ļ�					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getWorkspaceFont());
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "�µ��ĵ�" + window.getNewFileCounter() + ".txt";
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
	}
	
	protected void openFile() throws IOException {
		// ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();
		String title = file.getName();
		JScrollPane jScrollPane;
		//��ȡ�ļ�
		if(file.isFile() && title.toLowerCase().endsWith(".txt")) {
			JTextArea jTextArea = new JTextArea();
			//jTextArea.setFont(window.getWorkspaceFont());
			jScrollPane = new JScrollPane(jTextArea);
			window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
			window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
			openText(file, jTextArea);
			jScrollPane.setRowHeaderView(new LineNumberHeaderView());
		}
		// �½�ѡ�
		//JTextPane jTextPane = new JTextPane();
		//jTextPane.setFont(window.getWorkspaceFont());
		//JScrollPane jScrollPane = new JScrollPane(jTextPane);
		//
		//ʹ��ϵͳĬ���ַ�����ȡ����ʾ�ļ�
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
			//jTextPane.setText(String text);//�ٶ�̫��
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
			//s=new String(s.getBytes("8859_1"), "gbk");//����ת��
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
