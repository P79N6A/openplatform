package com.kd.op.util;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    private static final Logger logger = Logger.getLogger(Encrypt.class);
    public static String encrypt(String pwd) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("MD5").digest(
                    pwd.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个MD5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        StringBuffer sb = new StringBuffer(md5code);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            sb.append("0" + sb.toString());
        }
        return sb.toString();
    }

    /**
     * MD5加密（小写）
     * @param plainText
     * @return
     */
    public static String encryptLow(String plainText) {
        String re_md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("error:",e);
        }
        return re_md5;
    }

    /**
     * 大写
     * @param s
     * @return
     */
    public static String md5Capital(String s){
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] strTemp = s.getBytes();
            //使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                //logger.debug((int)b);
                //将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {return null;}
    }

}
