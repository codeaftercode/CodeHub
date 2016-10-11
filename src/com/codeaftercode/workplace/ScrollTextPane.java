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
	private File file;							//可以为空,表示新建且未保存的文件
	private String fileType;					//应改为枚举型,在Global.java中定义
	private String charset = Global.charset;	//默认字符集
	private boolean changed = false;			//文本是否发生改变
	
	public ScrollTextPane(File file, STabbedPane sTabbedPane) {
		super();
		this.sTabbedPane = sTabbedPane;
		this.file = file;
		init();
		
		if(file == null  || !file.exists()) {
			// 新建文档默认文件类型为txt
			fileType = ".txt";
			// 添加文本内容监听
			textPane.getDocument().addDocumentListener(new SDocumentListener());
			return;
		}

		/** 读取文件并显示 **/
		// 如果不把打开文件的处理代码放在一个单独的线程中，打开大文件时界面停止一切响应，直到方法执行完毕。
		// 单独建立线程的好处是允许用户随时停止该耗时的操作，使界面更加友好。
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
				case ".asm":// 汇编
				case ".json":
				case ".xml":
				case ".lock":
				case ".yml":
				case ".gitignore":
				case ".gitattributes":
					openText();
					break;
				default:
					// 无法识别的文件类型,用16进制查看器打开
					openHexFile();
					break;
				}
				// 添加文本内容监听
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
		// 配置JTextPane:字体,背景色,前景色,光标颜色,光标初始位置
		textPane.setFont(Global.textFont);
		textPane.setBackground(new Color(0x272822));
		textPane.setForeground(new Color(0xFFFFFF));
		textPane.setCaretColor(new Color(0xFFFFFF));
		textPane.setCaretPosition(0);
		// 配置滚动面板,添加行号组件
		this.setRowHeaderView(new LineNumberHeaderView());
		this.setViewportView(textPane);
		
		
	}
	
	/**
	 * 读取文件(自动探测字符集)
	 */
	private void openText() {
		// 自动探测字符集
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
	 * 重新加载文件(弹出对话框选择字符集)
	 */
	protected void reloadCharset() {
		// 提示未保存的修改将会丢失,询问是否继续
		if(changed) {
			String warning =sTabbedPane.getTitleAt(sTabbedPane.getSelectedIndex()) + "尚未保存的修改将会丢失.是否继续?";
			if(JOptionPane.showConfirmDialog(this, warning, null, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
				return;
			}
		}
		// 选择字符集
		charset = selectCharset();
		new Thread() {
			public void run() {
				Window.statusBar.getLabel().setText("正在重新加载文档..");
				// 读取文件至字节数组
				byte[] bytes = readFileByBytes();
				// 字节数组转换为字符串
				try {
					if(bytes != null) {
						textPane.setText(new String(bytes, charset));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Window.statusBar.getLabel().setText("文档加载完毕");
				Window.statusBar.getCharset().setText(charset);
				changed = false;
				textPane.getDocument().addDocumentListener(new SDocumentListener());
			};
		}.start();
	}

	/**
	 * 16进制查看器
	 * @param file 文件对象
	 * @param jTextPane 文件显示容器
	 */
	private void openHexFile() {
		// 打开16进制文件
		charset = "HexFile";
		// 设置一个等宽字体
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
	 * 自动探测文件字符集.文件越大,准确度越高.
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
		
		// 如果编码检测失败,则指定为默认字符集
		return charset = (charset != null) ? charset : Global.charset;
	}
	
	/**
	 * 弹出对话框,选择字符集
	 * @return 字符集名称
	 */
	private String selectCharset() {
		String title = "以指定字符集打开";
		String message = "选择字符集:";
		String[] charsets = new String[Charset.availableCharsets().keySet().size()];
		Charset.availableCharsets().keySet().toArray(charsets);
		return (String)JOptionPane.showInputDialog(sTabbedPane, message, title, JOptionPane.INFORMATION_MESSAGE, null, charsets, charsets[0]);
	}
	
	/**
	 * 字节流读文件
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
	
	/** 文件保存 **/
	public boolean saveFile() {
		// 写入准备:创建文件对象
		if (file == null) {
			File newFile = getFileToSave();
			if (newFile == null) {
				return false;
			}else {
				file = newFile;
				// 应将当前选项卡中的ScrollTextPane.file更改为新的文件对象,将当前选项卡标题更新为新文件名
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
				// 文件修改标签设置为未修改
				changed = false;
				// 添加文本内容监听
				textPane.getDocument().addDocumentListener(new SDocumentListener());
			}
		}.start();
		return true;
	}
	
	public void saveFileAs() {
		File oldFile = file;
		file = null;
		
		saveFile();
		// 保存文件中途取消操作,重置文件对象
		file = (file == null) ? oldFile : file ;
	}
	
	/**
	 * 获取保存文件对象
	 * @param sTabbedPane		选项卡对象,考虑定义为静态
	 * @param scrollTextPane	文本容器
	 * @return	返回值为用于保存操作的文件对象,返回null表示取消操作
	 */
	private File getFileToSave() {
		// 预先创建文件对象,截取扩展名
		String fileName = sTabbedPane.getTitleAt(sTabbedPane.getTabIndex(this));
		String extendName = fileType;
		// 未保存过的文件和不存在的文件,用文件名包装成新的文件对象
		// 保存过的文件,文件扩展名前插入"副本",包装成新的文件对象;
		File savefile = new File(file == null || !file.exists() ? fileName
				: ("".equals(extendName) ? fileName
						: fileName.substring(0, fileName.lastIndexOf('.')) + "副本" + extendName));
		
		// 配置文件选择器:默认保存位置,文件名,禁止多选,扩展名过滤器
		JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(Global.currentFolder));
		jfc.setSelectedFile(savefile);
		jfc.setDialogTitle("保存文件");
		jfc.setMultiSelectionEnabled(false);
		if (extendName != null && extendName.length() > 1) {
			// 扩展名长度大于1,则启用扩展名过滤器
			jfc.setFileFilter(new FileNameExtensionFilter(extendName, extendName.substring(1)));
		}

		// 弹出文件选择器
		int result = jfc.showDialog(this, "保存文件");
		if (result == JFileChooser.CANCEL_OPTION) {
			return null;
		} else if (result == JFileChooser.APPROVE_OPTION) {
			savefile = jfc.getSelectedFile();
		}

		// 检测同名文件
		if (savefile.exists()) {
			String warning = "是否替换已经存在的\"" + savefile.getName() + "\"";
			// 弹出对话框要求确认或取消
			result = JOptionPane.showConfirmDialog(this, warning, null, JOptionPane.YES_NO_OPTION,
					JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.YES_OPTION) {
				return null;
			}
		}
		return savefile;
	}
	

	
	private void saveTextFile() {
		// 保存文本文件
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
		// 保存16进制文件
		// 去除空格,转换为字节数组
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
