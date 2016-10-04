package com.codeaftercode.file;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
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
		// ע�����
		FileModularActionListener fileModularActionListener = new FileModularActionListener(this);
		// ��ʼ���˵�
		filesMenu = new MyJMenu("�ļ�(F)");

		newFileMenu = new MyJMenu("�½�(N)", new ImageIcon(window.iconsPath + "16px//newFile.png"));
		newTextMenuItem = new MyJMenuItem("��ͨ���ı��ļ�", new ImageIcon(window.iconsPath + "16px//txt.png"));
		openMenuItem = new MyJMenuItem("���ļ�(O)...");
		openFolderMenuItem = new MyJMenuItem("���ļ���...");
		openRecentMenu = new MyJMenu("Open recent...");
		saveMenuItem = new MyJMenuItem("����(S)");
		saveWithEncodingMenuItem = new MyJMenuItem("Save with Encoding");
		saveAsMenuItem = new MyJMenuItem("���Ϊ(A)...");
		saveAllMenuItem = new MyJMenuItem("ȫ������");
		closeFileMenuItem = new MyJMenuItem("�ر�");
		closeAllFileMenuItem = new MyJMenuItem("ȫ���ر�");
		exitMenuItem = new MyJMenuItem("�˳�(X)");

		// ���Ƿ�
		filesMenu.setMnemonic('F');

		// �˵��󶨲˵���
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

		// �������ע���¼�
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

		// �����JMenuBar��ӱ��˵�
		window.getJMenuBar().add(filesMenu);

	}

	protected void newText() {
		// �½����ı��ļ�
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		jTextPane.setBackground(new Color(0x272822));
		jTextPane.setForeground(new Color(0xFFFFFF));
		jTextPane.setCaretColor(new Color(0xFFFFFF));
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		// ѡ���ʾ���ļ�����
		window.setNewFileCounter(window.getNewFileCounter() + 1);
		String title = "�µ��ĵ�" + window.getNewFileCounter() + ".txt";
		// �½�ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		// ��ʾ�к�
		jScrollPane.setRowHeaderView(new LineNumberHeaderView(new Font("Consolas", Font.BOLD, 18)));
	}

	protected void openFile() throws IOException {
		// ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// ȡ���򿪲���
		if (file == null || !file.isFile()) {
			return;
		}

		String title = file.getName();
		// ����.txt�ļ�
		if (title.toLowerCase().endsWith(".txt")) {
			JTextArea jTextArea = new JTextArea();
			jTextArea.setFont(window.getFont());
			jTextArea.setBackground(new Color(0x272822));
			jTextArea.setForeground(new Color(0xFFFFFF));
			jTextArea.setCaretColor(new Color(0xFFFFFF));
			JScrollPane jScrollPane = new JScrollPane(jTextArea);
			window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
			// �л�����ѡ�
			window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
			// ��ʾ�к�--���Ǵ���JScrollPane������,����ʾ�кŹ��ܼ��ɽ�ȥ,�Ͳ������������÷�����ʾ�Ⱥ�
			// �����ϱ߾�,�Ա�֤������кŸ߶�Ҫͬ
			jTextArea.setMargin(new Insets(3, 0, 0, 0));
			jScrollPane.setRowHeaderView(new LineNumberHeaderView());
			openTxt(file, jTextArea);
			return;
		}

		// �����.txt�ļ�
		// �����ı���
		JTextPane jTextPane = new JTextPane();
		// �����ı�������
		jTextPane.setFont(window.getFont());
		jTextPane.setBackground(new Color(0x272822));
		jTextPane.setForeground(new Color(0xFFFFFF));
		jTextPane.setCaretColor(new Color(0xFFFFFF));
		// �����������
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		// ���ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		// �л�����ѡ�
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		// ��ʾ�к�--���Ǵ���JScrollPane������,����ʾ�кŹ��ܼ��ɽ�ȥ,�Ͳ������������÷�����ʾ�Ⱥ�
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());

		// ��ȡ�ļ�
		/*
		 * if(title.toLowerCase().endsWith(".java")) { openJava(file,
		 * jTextPane); return; }
		 */
		String fileType = title.substring(title.lastIndexOf(".")).toLowerCase();
		switch (fileType) {
		case ".java":
			openJava(file, jTextPane);
			break;
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
		case ".asm"://���
		case ".json":
		case ".xml":
		case ".lock":
		case ".yml":
		case ".gitignore":
		case ".gitattributes":
			openJava(file, jTextPane);
			break;
		default:
			openHexFile(file, jTextPane);
			break;
		}
	}

	public void openTxt(File file, JTextArea jTextArea) throws FileNotFoundException, IOException {
		// ��.txt�ļ�
		/*try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), window.getCharset()));) {
			char[] cbuf = new char[(int) file.length()];//�ַ���������,�޷���ȷ��ȡ�ļ�����
			br.read(cbuf);
			jTextArea.replaceSelection(new String(cbuf));
			// ���ù��ͣ��λ��
			jTextArea.setCaretPosition(0);
		}*/
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), window.getCharset()));) {
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line + System.lineSeparator());
			}
			jTextArea.replaceSelection(new String(sb.toString()));
			// ���ù��ͣ��λ��
			jTextArea.setCaretPosition(0);
		}
	}

	public void openJava(File file, JTextPane jTextPane) throws FileNotFoundException, IOException {
		// ��.java�ļ�
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), window.getCharset()));) {
			StringBuffer sb = new StringBuffer();
			String line;
			while((line = br.readLine()) != null) {
				sb.append(line + System.lineSeparator());
			}
			jTextPane.replaceSelection(new String(sb.toString()));
			// ���ù��ͣ��λ��
			jTextPane.setCaretPosition(0);
		}
	}

	public void openHexFile(File file, JTextPane jTextPane) throws FileNotFoundException, IOException {
		// ��16�����ļ�
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] cbuf = new byte[(int) file.length()];
			fis.read(cbuf);
			// ����һ���ȿ�����
			jTextPane.setFont(new Font("Consolas", Font.BOLD, 18));
			jTextPane.replaceSelection(Hex.toHexString(cbuf, 4, ' '));
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
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File folder = jfc.getSelectedFile();

		// ȡ���򿪲���
		if (folder == null || !folder.isDirectory()) {
			return;
		}

		// ״̬��:
		window.getStatusBar().getLabel().setText(folder.getAbsolutePath());

		// �򿪲���ʾ�ļ���
		window.getViewsModular().getShowFolderCheckBoxMenuItem().setState(true);
		window.getWorkplace().showFolder(folder);
	}
}
