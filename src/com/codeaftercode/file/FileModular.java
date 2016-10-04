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
import com.codeaftercode.ui.STabbedPane;
import com.codeaftercode.ui.Window;
import com.codeaftercode.workplace.FileTreePanel;
import com.codeaftercode.workplace.ViewGroups;

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
		//ע�����
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		//��ʼ���˵�
		filesMenu=new MyJMenu("�ļ�(F)");
		
		newFileMenu=new MyJMenu("�½�(N)",new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("��ͨ���ı��ļ�",new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem=new MyJMenuItem("���ļ�(O)...");
		openFolderMenuItem=new MyJMenuItem("���ļ���...");
		openRecentMenu=new MyJMenu("Open recent...");
		saveMenuItem=new MyJMenuItem("����(S)");
		saveWithEncodingMenuItem=new MyJMenuItem("Save with Encoding");
		saveAsMenuItem=new MyJMenuItem("���Ϊ(A)...");
		saveAllMenuItem=new MyJMenuItem("ȫ������");
		closeFileMenuItem = new MyJMenuItem("�ر�");
		closeAllFileMenuItem = new MyJMenuItem("ȫ���ر�");
		exitMenuItem=new MyJMenuItem("�˳�(X)");

		//���Ƿ�
		filesMenu.setMnemonic('F');
		
		//�˵��󶨲˵���
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
		

		//�������ע���¼�
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
		
		//�����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(filesMenu);
		
	}
	
	protected void newText() {
		//�½����ı��ļ�					
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		//ѡ���ʾ���ļ�����
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "�µ��ĵ�" + window.getNewFileCounter() + ".txt";
		//�½�ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		//��ʾ�к�
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());
	}
	
	protected void openFile() throws IOException {
		// ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// ȡ���򿪲���
		if(file == null) {
			return;
		}
		
		String title = file.getName();
		// ����.txt�ļ�
		if(file.isFile() && title.toLowerCase().endsWith(".txt")) {
			JTextArea jTextArea = new JTextArea();
			jTextArea.setFont(window.getFont());
			JScrollPane jScrollPane = new JScrollPane(jTextArea);
			window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
			// �л�����ѡ�
			window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
			// ��ʾ�к�--���Ǵ���JScrollPane������,����ʾ�кŹ��ܼ��ɽ�ȥ,�Ͳ������������÷�����ʾ�Ⱥ�
			jScrollPane.setRowHeaderView(new LineNumberHeaderView());
			openTxt(file, jTextArea);
			return;
		}
		
		// �����.txt�ļ�
		// �����ı���
		JTextPane jTextPane = new JTextPane();
		// �����ı�������
		jTextPane.setFont(window.getFont());
		// �����������
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		// ���ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		// �л�����ѡ�
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		// ��ʾ�к�--���Ǵ���JScrollPane������,����ʾ�кŹ��ܼ��ɽ�ȥ,�Ͳ������������÷�����ʾ�Ⱥ�
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());

		// ��ȡ�ļ�
		if(file.isFile() && title.toLowerCase().endsWith(".java")) {
			openJava(file, jTextPane);
			return;
		}
	}
	
	public void openTxt(File file, JTextArea jTextArea) throws FileNotFoundException, IOException {
		// ��.txt�ļ�
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			br.read(cbuf);
			jTextArea.replaceSelection(new String(cbuf,0,cbuf.length));
			// ���ù��ͣ��λ��
			jTextArea.setCaretPosition(0);
		}
	}
	
	public void openJava(File file, JTextPane jTextPane) throws FileNotFoundException, IOException {
		// ��.java�ļ�
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),window.getCharset()));) {
			char[] cbuf = new char[(int)file.length()];
			br.read(cbuf);
			jTextPane.replaceSelection(new String(cbuf,0,cbuf.length));
			// ���ù��ͣ��λ��
			jTextPane.setCaretPosition(0);
		}
	}
	
	public MyJMenu getFilesMenu() {
		return filesMenu;
	}

	public void setFilesMenu(MyJMenu filesMenu) {
		this.filesMenu = filesMenu;
	}

	public void openFolder() {
		// ���ļ���
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();
		
		// ȡ���򿪲���
		if(folder == null || !folder.isDirectory()) {
			return;
		}
		//test:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());
		
		/*ViewGroups viewGroups = window.getWorkplace().getViewGroups();
		if(viewGroups.getTabCount() > 0) {
			// �Ѵ��ڴ򿪵��ļ���
			viewGroups.removeAll();
			viewGroups.addTab(file.getName(), null, new FileTreePanel(window, file), null, true, window.getViewsModular().getShowLeftCheckBoxMenuItem());
		} else {
			// �������Ѵ򿪵��ļ���
			viewGroups.addTab(file.getName(), null, new FileTreePanel(window, file), null, true, window.getViewsModular().getShowLeftCheckBoxMenuItem());
		}
		window.getViewsModular().getShowLeftCheckBoxMenuItem().setState(true);
		window.getWorkplace().showViewGroups();
		
		// ��ʾ�ļ���
		window.getWorkplace().setLeftPanel(new FileTreePanel(window));*/
		
		// �򿪲���ʾ�ļ���
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}	
}
