package com.thankjava.toolkit.core.encrypit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密
 * <p> Function: MD5 </p>
 * <p> Description: </p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2015年12月30日 上午11:08:43
 */
public final class MD5 {

    private MD5() {
    }

    static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String generate(String content) {
        return encodeByMD5(content);
    }

    public static String generateUpperCase(String content) {
        String encodeStr = encodeByMD5(content);
        return encodeStr == null ? null : encodeStr.toUpperCase();
    }

    public static boolean validate(String content, String originContent) {
        if (content.equals(encodeByMD5(originContent))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对字符串进行MD5加密
     */
    private static String encodeByMD5(String content) {
        if (content != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(content.getBytes());
                // 将得到的字节数组变成字符串返回
                return byteArrayToHexString(results);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 转换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
