package com.thankjava.toolkit3d.core.encrypit;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.thankjava.toolkit.bean.encrypit.RSAKey;
import com.thankjava.toolkit.core.encrypit.RSA;
import com.thankjava.toolkit3d.bean.encrypit.RSAKeyString;

/**
 * 基于JDK RSA 算法 通过Base64缩位
 * <p>Function: RSAWithBase64</p>
 * <p>Description: </p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2016年8月10日 下午6:25:44
 */
public class RSAWithBase64 {

    private static final String algorithm = "RSA";

    /**
     * 获取RAS Base64 公密钥对
     * <p>Function: keyGen</p>
     * <p>Description: </p>
     *
     * @param
     * @return
     * @author acexy@thankjava.com
     * @date 2016年8月10日 下午6:31:26
     * @version 1.0
     */
    public static RSAKeyString keyGen(int keySize) {
        try {
            RSAKey keys = RSA.keyGen(keySize);
            return new RSAKeyString(
                    Base64Util.encode2String(keys.getPublicKey().getEncoded()),
                    Base64Util.encode2String(keys.getPrivateKey().getEncoded())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从Base64字符中解出私钥
     * <p>Function: decryptPrivateKey</p>
     * <p>Description: </p>
     *
     * @param privateKeyStr
     * @return
     * @author acexy@thankjava.com
     * @date 2016年8月11日 上午9:43:39
     * @version 1.0
     */
    public static PrivateKey decryptPrivateKey(String privateKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64Util.decode(privateKeyStr));
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从Base64字符中解出公钥
     * <p>Function: decryptPublicKey</p>
     * <p>Description: </p>
     *
     * @param publicKeyStr
     * @return
     * @author acexy@thankjava.com
     * @date 2016年8月11日 上午9:50:30
     * @version 1.0
     */
    public static PublicKey decryptPublicKey(String publicKeyStr) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(publicKeyStr));
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * RSA公钥加密并返回base64密文字符串
     * <p>Function: encrypt</p>
     * <p>Description: </p>
     *
     * @param content
     * @param pubKey
     * @return
     * @author acexy@thankjava.com
     * @date 2016年8月11日 上午9:48:48
     * @version 1.0
     */
    public static String encrypt(String content, String pubKey) {
        return Base64Util.encode2String(RSA.encrypt(content.getBytes(), decryptPublicKey(pubKey)));
    }

    /**
     * RSA私钥解密经过base64处理的密文
     * <p>Function: verify</p>
     * <p>Description: </p>
     *
     * @param base64Cipher Base64处理后的字符串
     * @param priKey
     * @return
     * @author acexy@thankjava.com
     * @date 2016年8月11日 上午9:54:13
     * @version 1.0
     */
    public static String decrypt(String base64Cipher, String priKey) {
        return new String(RSA.decrypt(Base64Util.decode(base64Cipher), decryptPrivateKey(priKey)));
    }

    /**
     * 对内容进行加签，并base64字符串
     *
     * @param content
     * @param priKey
     * @return
     */
    public static String sign(String content, String priKey) {
        return Base64Util.encode2String(RSA.sign(content.getBytes(), decryptPrivateKey(priKey)));
    }


    /**
     * 对源内容及base64密文进行验签处理
     *
     * @param content
     * @param base64Cipher
     * @param pubKey
     * @return
     */
    public static boolean verify(String content, String base64Cipher, String pubKey) {
        return RSA.verify(content.getBytes(), Base64Util.decode(base64Cipher), decryptPublicKey(pubKey));
    }

}
