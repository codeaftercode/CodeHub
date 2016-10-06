package com.codeaftercode.workplace;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.mozilla.universalchardet.UniversalDetector;

import com.codeaftercode.coding.Hex;
import com.codeaftercode.common.Global;

public class ScrollTextPane extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textPane = new JTextPane();
	private File file;							//����Ϊ��,��ʾ�½���δ������ļ�
	private String fileType;					//Ӧ��Ϊö����,��Global.java�ж���
	private String charset = Global.charset;	//Ĭ���ַ���
	
	public ScrollTextPane(File file) {
		super();
		this.file = file;
		init();
		
		if(file == null  || !file.exists()) {
			return;
		}
		fileType = file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase();
		
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
					openText();
				}
			}.start();
			break;
		default:
			// �޷�ʶ����ļ�����,��16���Ʋ鿴����
			new Thread() {
				public void run() {
					openHexFile();
				}
			}.start();
			break;
		}
	}
	
	private void init() {
		// ����JTextPane:����,����ɫ,ǰ��ɫ,�����ɫ,����ʼλ��
		textPane.setFont(Global.textFont);
		textPane.setBackground(new Color(0x272822));
		textPane.setForeground(new Color(0xFFFFFF));
		textPane.setCaretColor(new Color(0xFFFFFF));
		textPane.setCaretPosition(0);
		// ���ù������,����к����
		this.setRowHeaderView(new LineNumberHeaderView());
		this.setViewportView(textPane);
	}
	
	/**
	 * �ı��ļ��༭��
	 */
	private void openText() {
		// �����ֽ�����buf[],��СΪ�ļ��ֽ���file.length();
		UniversalDetector detector = new UniversalDetector(null);
		int len = (int) file.length();
		byte[] buf = new byte[len];
		// �ֽ������ļ�,һ���Զ�ȡ���ֽ�����buf[]
		try (FileInputStream fis = new FileInputStream(file);) {
			fis.read(buf);
			// ��juniversalchardet-1.0.3.jar��ȡ����encoding
			detector.handleData(buf, 0, len);
			detector.dataEnd();
			charset = detector.getDetectedCharset();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���������ʧ��,��ָ��ΪĬ���ַ���
		charset = (charset != null) ? charset : Global.charset;
		// ״̬��
		//window.getStatusBar().getLabel().setText("�ַ���:" + encoding);

		try {
			//jTextPane.replaceSelection(new String(buf, encoding));	�˷���̫��
			textPane.setText(new String(buf, charset));
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
	private void openHexFile() {
		// ��16�����ļ�
		charset = "HexFile";
		// ����һ���ȿ�����
		textPane.setFont(Global.monospacedFont);
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] cbuf = new byte[(int) file.length()];
			fis.read(cbuf);
			//jTextPane.replaceSelection(Hex.toHexString(cbuf, 4, ' '));
			textPane.setText(Hex.toHexString(cbuf, 4, ' '));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public JTextPane getTextPane() {
		return textPane;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
