package com.codeaftercode.common;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
/**
 * 定义全局静态变量,软件默认配置
 * @author codeaftercode
 *
 */
public class Global {
	// 默认字体
	public static Font textFont = new Font("微软雅黑", Font.BOLD, 16); 
	public static Font globalFont = new Font("微软雅黑", Font.BOLD, 16); 
	public static Font monospacedFont = new Font("Consolas", Font.BOLD, 18);
	// 行号
	public static Font lineNumber = new Font("微软雅黑", Font.BOLD, 16);
	public static Color lineNumber_DEFAULT_BACKGROUD = new Color(0x272822);
	public static Color LineNumber_DEFAULT_FOREGROUD = new Color(0xA49C9C);
	
	public static String charset = System.getProperty("file.encoding");
	public static String currentFolder = new File("").getAbsoluteFile().toString();
	public static enum FileType {
		txt,ini,inf,h,c,cpp,bat,md,markdown,css,js,html,htm,php,asp,asm,json,xml,lock,yml,gitignore,gitattributes;
	}
}
