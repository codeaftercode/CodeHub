package com.codeaftercode.workplace;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.mozilla.universalchardet.UniversalDetector;

import com.codeaftercode.coding.Hex;
import com.codeaftercode.common.Global;
import com.codeaftercode.common.Window;

public class ScrollTextPane extends JScrollPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textPane = new JTextPane();
	private STabbedPane sTabbedPane;
	private File file;							//����Ϊ��,��ʾ�½���δ������ļ�
	private String fileType;					//Ӧ��Ϊö����,��Global.java�ж���
	private String charset = Global.charset;	//Ĭ���ַ���
	private boolean changed = false;			//�ı��Ƿ����ı�
	
	public ScrollTextPane(File file, STabbedPane sTabbedPane) {
		super();
		this.sTabbedPane = sTabbedPane;
		this.file = file;
		init();
		
		if(file == null  || !file.exists()) {
			// �½��ĵ�Ĭ���ļ�����Ϊtxt
			fileType = ".txt";
			// ����ı����ݼ���
			textPane.getDocument().addDocumentListener(new SDocumentListener());
			return;
		}

		/** ��ȡ�ļ�����ʾ **/
		// ������Ѵ��ļ��Ĵ���������һ���������߳��У��򿪴��ļ�ʱ����ֹͣһ����Ӧ��ֱ������ִ����ϡ�
		// ���������̵߳ĺô��������û���ʱֹͣ�ú�ʱ�Ĳ�����ʹ��������Ѻá�
		new Thread() {
			public void run() {
				fileType = Global.getExtName(file.getName());
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
					openText();
					break;
				default:
					// �޷�ʶ����ļ�����,��16���Ʋ鿴����
					openHexFile();
					break;
				}
				// ����ı����ݼ���
				textPane.getDocument().addDocumentListener(new SDocumentListener());
			}
		}.start();
	}
	
	private class SDocumentListener implements DocumentListener {

		@Override
		public void removeUpdate(DocumentEvent e) {
			changed = true;
			textPane.getDocument().removeDocumentListener(this);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			changed = true;
			textPane.getDocument().removeDocumentListener(this);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			changed = true;
			textPane.getDocument().removeDocumentListener(this);
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
	 * ��ȡ�ļ�(�Զ�̽���ַ���)
	 */
	private void openText() {
		// �Զ�̽���ַ���
		charset = detectCharset(file);
		byte[] bytes = readFileByBytes();
		try {
			if(bytes != null) {
				textPane.setText(new String(bytes, charset));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���¼����ļ�(�����Ի���ѡ���ַ���)
	 */
	protected void reloadCharset() {
		// ��ʾδ������޸Ľ��ᶪʧ,ѯ���Ƿ����
		if(changed) {
			String warning =sTabbedPane.getTitleAt(sTabbedPane.getSelectedIndex()) + "��δ������޸Ľ��ᶪʧ.�Ƿ����?";
			if(JOptionPane.showConfirmDialog(this, warning, null, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		// ѡ���ַ���
		charset = selectCharset();
		new Thread() {
			public void run() {
				Window.statusBar.getLabel().setText("�������¼����ĵ�..");
				// ��ȡ�ļ����ֽ�����
				byte[] bytes = readFileByBytes();
				// �ֽ�����ת��Ϊ�ַ���
				try {
					if(bytes != null) {
						textPane.setText(new String(bytes, charset));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Window.statusBar.getLabel().setText("�ĵ��������");
				Window.statusBar.getCharset().setText(charset);
				changed = false;
				textPane.getDocument().addDocumentListener(new SDocumentListener());
			};
		}.start();
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
			textPane.setText(Hex.toHexString(cbuf, 4, ' '));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �Զ�̽���ļ��ַ���.�ļ�Խ��,׼ȷ��Խ��.
	 * @param file
	 */
	private String detectCharset(File file) {
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] buf = new byte[4096];
			UniversalDetector detector = new UniversalDetector(null);
			int nread;
			while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			  detector.handleData(buf, 0, nread);
			}
			detector.dataEnd();
			charset = detector.getDetectedCharset();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ���������ʧ��,��ָ��ΪĬ���ַ���
		return charset = (charset != null) ? charset : Global.charset;
	}
	
	/**
	 * �����Ի���,ѡ���ַ���
	 * @return �ַ�������
	 */
	private String selectCharset() {
		String title = "��ָ���ַ�����";
		String message = "ѡ���ַ���:";
		String[] charsets = new String[Charset.availableCharsets().keySet().size()];
		Charset.availableCharsets().keySet().toArray(charsets);
		return (String)JOptionPane.showInputDialog(sTabbedPane, message, title, JOptionPane.INFORMATION_MESSAGE, null, charsets, charsets[0]);
	}
	
	/**
	 * �ֽ������ļ�
	 * @param file
	 * @return
	 */
	private byte[] readFileByBytes() {
		int length;
		if(file == null || !file.exists() || (length = (int)file.length()) < 1) {
			return null;
		}
		byte[] bytes = new byte[length];
		
		try(FileInputStream fis = new FileInputStream(file);) {
			fis.read(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	/** �ļ����� **/
	public boolean saveFile() {
		// д��׼��:�����ļ�����
		if (file == null) {
			File newFile = getFileToSave();
			if (newFile == null) {
				return false;
			}else {
				file = newFile;
				// Ӧ����ǰѡ��е�ScrollTextPane.file����Ϊ�µ��ļ�����,����ǰѡ��������Ϊ���ļ���
				sTabbedPane.setTitleAt(sTabbedPane.getTabIndex(this), file.getName());
			}
		}

		new Thread() {
			public void run() {
				if ("HexFile".equals(charset)) {
					saveHexFile();
				} else {
					saveTextFile();
				}
				// �ļ��޸ı�ǩ����Ϊδ�޸�
				changed = false;
				// ����ı����ݼ���
				textPane.getDocument().addDocumentListener(new SDocumentListener());
			}
		}.start();
		return true;
	}
	
	public void saveFileAs() {
		File oldFile = file;
		file = null;
		
		saveFile();
		// �����ļ���;ȡ������,�����ļ�����
		file = (file == null) ? oldFile : file ;
	}
	
	/**
	 * ��ȡ�����ļ�����
	 * @param sTabbedPane		ѡ�����,���Ƕ���Ϊ��̬
	 * @param scrollTextPane	�ı�����
	 * @return	����ֵΪ���ڱ���������ļ�����,����null��ʾȡ������
	 */
	private File getFileToSave() {
		// Ԥ�ȴ����ļ�����,��ȡ��չ��
		String fileName = sTabbedPane.getTitleAt(sTabbedPane.getTabIndex(this));
		String extendName = fileType;
		// δ��������ļ��Ͳ����ڵ��ļ�,���ļ�����װ���µ��ļ�����
		// ��������ļ�,�ļ���չ��ǰ����"����",��װ���µ��ļ�����;
		File savefile = new File(file == null || !file.exists() ? fileName
				: ("".equals(extendName) ? fileName
						: fileName.substring(0, fileName.lastIndexOf('.')) + "����" + extendName));
		
		// �����ļ�ѡ����:Ĭ�ϱ���λ��,�ļ���,��ֹ��ѡ,��չ��������
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Global.currentFolder));
		jfc.setSelectedFile(savefile);
		jfc.setDialogTitle("�����ļ�");
		jfc.setMultiSelectionEnabled(false);
		if (extendName != null && extendName.length() > 1) {
			// ��չ�����ȴ���1,��������չ��������
			jfc.setFileFilter(new FileNameExtensionFilter(extendName, extendName.substring(1)));
		}

		// �����ļ�ѡ����
		int result = jfc.showDialog(this, "�����ļ�");
		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		} else if (result == JFileChooser.APPROVE_OPTION) {
			savefile = jfc.getSelectedFile();
		}

		// ���ͬ���ļ�
		if (savefile.exists()) {
			String warning = "�Ƿ��滻�Ѿ����ڵ�\"" + savefile.getName() + "\"";
			// �����Ի���Ҫ��ȷ�ϻ�ȡ��
			result = JOptionPane.showConfirmDialog(this, warning, null, JOptionPane.YES_NO_OPTION,
					JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.YES_OPTION) {
				return null;
			}
		}
		return savefile;
	}
	

	
	private void saveTextFile() {
		// �����ı��ļ�
		String text = textPane.getText();
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));) {
			bw.write(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveHexFile() {
		// ����16�����ļ�
		// ȥ���ո�,ת��Ϊ�ֽ�����
		try(BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(file));) {
			bis.write(Hex.hexString2Bytes(textPane.getText().replace(" ", "")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public JTextPane getTextPane() {
		return textPane;
	}

	public File getFile() {
		return file;
	}

	public String getFileType() {
		return fileType;
	}

	public String getCharset() {
		return charset;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
