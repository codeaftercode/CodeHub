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


	/**
	 * ���ļ�
	 * ����ϵͳ�ļ�ѡ����ѡ���ļ�,�����ļ����͵��ö�Ӧ�ķ������ļ�
	 */
	protected void openFile(){
		// ѡ���ļ�
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);// ֻ��ѡ���ļ�,����ѡ�ļ���
		jfc.showOpenDialog(window);
		File file = jfc.getSelectedFile();

		// ȡ���򿪲���
		if (file == null || !file.isFile()) {
			return;
		}
		
		// ѡ����ļ��Ѿ���
		// ���window.getWorkplace().getsTabbedPane()����û������ļ�,�����,��Ҫ�ظ���,ֻ���л�����ѡ�����
		//window.getWorkplace().getsTabbedPane().getComponents().getClass();
		
			
		/** �����ı���ʾ���� **/
		String title = file.getName();
		String fileType = title.substring(title.lastIndexOf(".")).toLowerCase();
		// ����JTextPane:����,����ɫ,ǰ��ɫ,�����ɫ,����ʼλ��
		JTextPane jTextPane = new JTextPane();
		jTextPane.setFont(window.getFont());
		jTextPane.setBackground(new Color(0x272822));
		jTextPane.setForeground(new Color(0xFFFFFF));
		jTextPane.setCaretColor(new Color(0xFFFFFF));
		jTextPane.setCaretPosition(0);
		// ���ù������,����к����
		JScrollPane jScrollPane = new JScrollPane(jTextPane);
		jScrollPane.setRowHeaderView(new LineNumberHeaderView());
		// ����ѡ�,�л�����ѡ�
		window.getWorkplace().getsTabbedPane().addTab(title, null, jScrollPane, title, true);
		window.getWorkplace().getsTabbedPane().setSelectedComponent(jScrollPane);
		
		/** ��ȡ�ļ�����ʾ **/
		// ������Ѵ��ļ��Ĵ���������һ���������߳��У��򿪴��ļ�ʱ����ֹͣһ����Ӧ��ֱ������ִ����ϡ�
		// ���������̵߳ĺô��������û���ʱֹͣ�ú�ʱ�Ĳ�����ʹ��������Ѻá�
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
		case ".asm":// ���
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
			// �޷�ʶ����ļ�����,��16���Ʋ鿴����
			new Thread() {
				public void run() {
					openHexFile(file, jTextPane);
				}
			}.start();
			break;
		}
	}
	/**
	 * �ı��ļ��༭��
	 * @param file �ļ�����
	 * @param jTextPane �ļ���ʾ����
	 */
	public void openText(File file, JTextPane jTextPane) {
		// �����ֽ�����buf[],��СΪ�ļ��ֽ���file.length();
		UniversalDetector detector = new UniversalDetector(null);
		String encoding = "";
		int len = (int) file.length();
		byte[] buf = new byte[len];
		// �ֽ������ļ�,һ���Զ�ȡ���ֽ�����buf[]
		try (FileInputStream fis = new FileInputStream(file);) {
			fis.read(buf);
			// ��juniversalchardet-1.0.3.jar��ȡ����encoding
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
		// ���������ʧ��,��ָ��ΪĬ���ַ���
		encoding = (encoding != null) ? encoding : window.getCharset();
		// ״̬��
		window.getStatusBar().getLabel().setText("�ַ���:" + encoding);

		try {
			//jTextPane.replaceSelection(new String(buf, encoding));	�˷���̫��
			jTextPane.setText(new String(buf, encoding));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 16���Ʋ鿴��
	 * @param file �ļ�����
	 * @param jTextPane �ļ���ʾ����
	 */
	public void openHexFile(File file, JTextPane jTextPane) {
		// ��16�����ļ�
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] cbuf = new byte[(int) file.length()];
			fis.read(cbuf);
			// ����һ���ȿ�����
			jTextPane.setFont(new Font("Consolas", Font.BOLD, 18));
			//jTextPane.replaceSelection(Hex.toHexString(cbuf, 4, ' '));
			jTextPane.setText(Hex.toHexString(cbuf, 4, ' '));
			// ���ù��ͣ��λ��
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

	public void saveFile() {
		// �����ļ�
		
	}

	public void saveAs() {
		// �ļ����Ϊ
		// д��׼��:�ַ���,����
		
		// д��׼��:ȷ���ļ�·��,�ļ���
		
		// д���ļ�
		
	}
}
