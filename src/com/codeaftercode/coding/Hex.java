package com.codeaftercode.coding;

public class Hex {
	public static final String HEX = "0123456789ABCDEF";
	
	/** 
     * ʮ�����ֽ�����byte[]תΪʮ�������ַ���String
     * �ֱ�ȡ���ֽڵĸ���λ�����λȻ��ֱ�ó�10����0-15������ֵ��������һ���ַ���������ɡ�
     *  
     * @param bytes ʮ�����ֽ�����
     * @return ʮ�����Ʊ�ʾ���ַ���
     */  
    public static String toHexString(byte[] bytes)  
    {  
        StringBuilder sb = new StringBuilder(bytes.length * 2);  
        for (byte b : bytes)  
        {  
            // ȡ������ֽڵĸ�4λ��Ȼ����0x0f�����㣬�õ�һ��0-15֮������ݣ�ͨ��HEX.charAt(0-15)��Ϊ16������  
            sb.append(HEX.charAt((b >> 4) & 0x0f));  
            // ȡ������ֽڵĵ�λ����0x0f�����㣬�õ�һ��0-15֮������ݣ�ͨ��HEX.charAt(0-15)��Ϊ16������  
            sb.append(HEX.charAt(b & 0x0f));  
        }
  
        return sb.toString();  
    }
    
    /**
     * ת��Ϊ16�����ַ���,
     * @param bytes �ֽ�����
     * @param divide ÿ������16�����ַ���
     * @param c �ָ���
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
        chars[0] = ' ';//ռλ,����
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
     * ʮ����int����int[]תΪʮ�������ַ���String
     * û�п����з��������޷�����������,��ȷ���˷����Ƿ���ȷ
     * �������û����Integer.toHexString(i)����,��֪���ĸ���
     *  
     * @param bytes ʮ�����ֽ�����
     * @return ʮ�����Ʊ�ʾ���ַ���
     */  
    public static String toHexString(int[] intData)  
    {  
        StringBuilder sb = new StringBuilder(intData.length * 2);  
        for (int i : intData)  
        {  
        	//1��int�����ݷֳ�4��,�ֱ������Ӧ�Ķ�����ֵ.
        	// ȡ������ֽڵĵ�λ����0x0f�����㣬�õ�һ��0-15֮������ݣ�ͨ��HEX.charAt(0-15)��Ϊ16������  
        	sb.append(HEX.charAt((i >> 12) & 0x0f)); 
        	sb.append(HEX.charAt((i >> 8) & 0x0f)); 
            sb.append(HEX.charAt((i >> 4) & 0x0f));  
            sb.append(HEX.charAt(i & 0x0f));
        }
        return sb.toString();  
    }

    
    
    
    /*
     * �ַ�ת��Ϊ�ֽ�
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
     }
    
    /*
     * 16�����ַ���ת�ֽ�����
     * ÿһ��16�����ַ���4bit��һ���ֽ���8bit����������16�����ַ�ת����1���ֽ�
     * ���ڵ�1���ַ���ת����byte�Ժ�����4λ
     * Ȼ��͵�2���ַ���byte�������㣬�����Ͱ������ַ�ת��Ϊ1���ֽڡ�
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
