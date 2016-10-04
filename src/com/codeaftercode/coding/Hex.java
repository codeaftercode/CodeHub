package com.codeaftercode.coding;

public class Hex {
	public static final String HEX = "0123456789ABCDEF";
	
	/** 
     * 十进制字节数组byte[]转为十六进制字符串String
     * 分别取出字节的高四位与低四位然后分别得出10进制0-15这样的值，再利用一个字符串数组完成。
     *  
     * @param bytes 十进制字节数组
     * @return 十六进制表示的字符串
     */  
    public static String toHexString(byte[] bytes)  
    {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
        for (byte b : bytes)  
        {  
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt((b >> 4) & 0x0f));  
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
            sb.append(HEX.charAt(b & 0x0f));  
        }
  
        return sb.toString();  
    }
    
    /**
     * 转换为16进制字符串,
     * @param bytes 字节数组
     * @param divide 每个分组16进制字符数
     * @param c 分隔符
     * @return
     */
    public static String toHexString(byte[] bytes,int divide, char c)  
    {  
    	if(divide < 1) {
    		return toHexString(bytes);
    	}
    	int len = bytes.length;
        char[] chars = new char[len * 2 / divide + len * 2 + 1];
        int index = 1;
        chars[0] = ' ';//占位,不用
        int d = divide + 1;
    	for (byte b : bytes) {
			chars[index++] = HEX.charAt((b >> 4) & 0x0f);
			if(index % d == 0) {
				chars[index] = c;
				index++;
			}
			chars[index++] = HEX.charAt(b & 0x0f);
			if(index % d == 0) {
				chars[index] = c;
				index++;
			}
		}
        return new String(chars,1,chars.length-1);
    }
    
	/** 
     * 十进制int数组int[]转为十六进制字符串String
     * 没有考虑有符号数与无符号数的区别,不确定此方法是否正确
     * 这个方法没有用Integer.toHexString(i)方法,不知道哪个好
     *  
     * @param bytes 十进制字节数组
     * @return 十六进制表示的字符串
     */  
    public static String toHexString(int[] intData)  
    {  
        StringBuilder sb = new StringBuilder(intData.length * 2);  
        for (int i : intData)  
        {  
        	//1个int型数据分成4组,分别求出对应的二进制值.
        	// 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数  
        	sb.append(HEX.charAt((i >> 12) & 0x0f)); 
        	sb.append(HEX.charAt((i >> 8) & 0x0f)); 
            sb.append(HEX.charAt((i >> 4) & 0x0f));  
            sb.append(HEX.charAt(i & 0x0f));
        }
        return sb.toString();  
    }

    
    
    
    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
     }
    
    /*
     * 16进制字符串转字节数组
     * 每一个16进制字符是4bit，一个字节是8bit，所以两个16进制字符转换成1个字节
     * 对于第1个字符，转换成byte以后左移4位
     * 然后和第2个字符的byte做或运算，这样就把两个字符转换为1个字节。
     */
    public static byte[] hexString2Bytes(String hex) {
            
            if ((hex == null) || (hex.equals(""))){
                return null;
            } else if (hex.length()%2 != 0){
                return null;
            } else {                
                hex = hex.toUpperCase();
                int len = hex.length()/2;
                byte[] b = new byte[len];
                char[] hc = hex.toCharArray();
                for (int i=0; i<len; i++){
                    int p=2*i;
                    b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
                }
              return b;
            }           
    }
}
