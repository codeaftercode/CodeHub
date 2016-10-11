package com.codeaftercode.common;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
/**
 * ¶¨ÒåÈ«¾Ö¾²Ì¬±äÁ¿,Èí¼þÄ¬ÈÏÅäÖÃ
 * @author codeaftercode
 *
 */
public class Global {
	// Ä¬ÈÏ×ÖÌå
	public static Font textFont = new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16); 
	public static Font globalFont = new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16); 
	public static Font monospacedFont = new Font("Consolas", Font.BOLD, 18);
	// ÐÐºÅ
	public static Font lineNumber = new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16);
	public static Color lineNumber_DEFAULT_BACKGROUD = new Color(0x272822);
	public static Color LineNumber_DEFAULT_FOREGROUD = new Color(0xA49C9C);
	
	public static String charset = System.getProperty("file.encoding");
	//public static String currentFolder = new File("").getAbsoluteFile().toString();
	public static String currentFolder = new File("D:\\CodeHub²âÊÔÇø\\").getAbsolutePath().toString();
	public static enum FileType {
		txt,ini,inf,h,c,cpp,bat,md,markdown,css,js,html,htm,php,asp,asm,json,xml,lock,yml,gitignore,gitattributes;
	}

	// ³£ÓÃ×Ö·û¼¯
	public static String[] Charset = {"UTF-8", "GB2312", "WINDOWS"};
	
	
	public static String getExtName(String s) {
        int i = s.indexOf('.');
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? "" : s.substring(i, s.length()) : "").toLowerCase();
    }
}
