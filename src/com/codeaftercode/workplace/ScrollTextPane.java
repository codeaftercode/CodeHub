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
	private File file;							//可以为空,表示新建且未保存的文件
	private String fileType;					//应改为枚举型,在Global.java中定义
	private String charset = Global.charset;	//默认字符集
	
	public ScrollTextPane(File file) {
		super();
		this.file = file;
		init();
		
		if(file == null  || !file.exists()) {
			return;
		}
		fileType = file.getName().substring(file.getName().lastIndexOf(".")).toLowerCase();
		
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
					openText();
				}
			}.start();
			break;
		default:
			// 无法识别的文件类型,用16进制查看器打开
			new Thread() {
				public void run() {
					openHexFile();
				}
			}.start();
			break;
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
	 * 文本文件编辑器
	 */
	private void openText() {
		// 创建字节数组buf[],大小为文件字节数file.length();
		UniversalDetector detector = new UniversalDetector(null);
		int len = (int) file.length();
		byte[] buf = new byte[len];
		// 字节流打开文件,一次性读取到字节数组buf[]
		try (FileInputStream fis = new FileInputStream(file);) {
			fis.read(buf);
			// 用juniversalchardet-1.0.3.jar获取编码encoding
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
		// 如果编码检测失败,则指定为默认字符集
		charset = (charset != null) ? charset : Global.charset;
		// 状态栏
		//window.getStatusBar().getLabel().setText("字符集:" + encoding);

		try {
			//jTextPane.replaceSelection(new String(buf, encoding));	此方法太慢
			textPane.setText(new String(buf, charset));
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
	private void openHexFile() {
		// 打开16进制文件
		charset = "HexFile";
		// 设置一个等宽字体
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
