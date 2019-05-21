package com.kd.openplatform.util;

import java.util.Locale;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/*******************************************************************************
 * AES加解密算法
 * 2016.07.22
 * @author  王学明 
 * aes 128位 cbc 算法
 * HTML的&lt; &gt;&amp;&quot;&copy;&nbsp;分别是<，>，&，"，©;空格的转义字符
 */

public class AESUtil {
    private static final Log log =  LogFactory.getLog(AESUtil.class);
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
       

        return  Encrypt(sSrc,sKey,"1234567890123456"); //默认的加密向量1234567890123456
    }
    
    /**
     * @param sSrc 加密data
     * @param sKey 加密密钥
     * @param ivStr 加密向量
     * @Description:加密操作
     */
    public static String Encrypt(String sSrc, String sKey,String ivStr) throws Exception {
        if (sKey == null) {
            log.info("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            log.info("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式" 密码器
        IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度1234567890123456 
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); //初始化
       // sSrc= escapeChar(sSrc);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8")); //执行最终的加密操作
        
        String str=new BASE64Encoder().encode(encrypted); ////此处使用BASE64做转码功能，同时能起到2次加密的作用。
        
        //获取操作系统的类型（目前默认无mac操作系统）
        Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
//        log.info("操作系统类型："+os);
        if (os!=null && StringUtils.isNotEmpty(os)) {
            if(os.toLowerCase(Locale.ENGLISH).startsWith("win")){ //windows 系统
                str=str.replaceAll("\r\n", "");   //windows 换行\r\n; linux 换行\n ;mac 换行 \r
            }else{ //linux 系统
                str=str.replaceAll("\n", "");   //windows 换行\r\n; linux 换行\n ;mac 换行 \r
            }
        }

        return str;//new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
    	return Decrypt(sSrc,sKey,"1234567890123456");
    }
    
    // 解密
    public static String Decrypt(String sSrc, String sKey,String ivStr) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                log.info("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                log.info("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes("utf-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                originalString= unEscapeChar(originalString);
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            log.info(ex.toString());
            return null;
        }
    }
    
    
  
    /*
     * escapeChar 字符转换 
     * 加密前分别把<，>，&，"，© 的转义字符 转换成 &lt; &gt;&amp;&quot;&copy;
     * 
     
    private static String  escapeChar(String beforeEncryptString ){
    	String escapeStr=beforeEncryptString;
    	escapeStr=escapeStr.replaceAll("<", "&lt;");
    	escapeStr=escapeStr.replaceAll(">", "&gt;");
    	escapeStr=escapeStr.replaceAll("&", "&amp;");
    	escapeStr=escapeStr.replaceAll("\"", "&quot;");
    	escapeStr=escapeStr.replaceAll("©", "&copy;");
    	escapeStr=escapeStr.replaceAll(" ", "&nbsp;");
    	
		return escapeStr;
    	
    }
    * */
    /*
     * unEscapeChar 反向字符转换 
     * 解密后分别把&lt; &gt;&amp;&quot;&copy; 的转义字符 转换成  <，>，&，"，©
     *  
     * */
    private static String  unEscapeChar(String beforeDecryptString ){
    	String unEscapeStr=beforeDecryptString;
    	unEscapeStr=unEscapeStr.replaceAll( "&lt;","<");
    	unEscapeStr=unEscapeStr.replaceAll( "&gt;",">");
    	unEscapeStr=unEscapeStr.replaceAll( "&amp;","&");
    	unEscapeStr=unEscapeStr.replaceAll( "&quot;","\"");
    	unEscapeStr=unEscapeStr.replaceAll( "&copy;","©");
    	unEscapeStr=unEscapeStr.replaceAll( "&nbsp;"," ");
    	
		return unEscapeStr;
    	
    }
}