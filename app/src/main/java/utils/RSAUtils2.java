package utils;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Lspring on 2017/3/31.
 */

public class RSAUtils2 {
    //  private static RSAPublicKey publicKey = null;
    /**************************** RSA 公钥加密解密**************************************/
    /**
     * 从字符串中加载公钥,从服务端获取
     *
     * @param pubKey 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKey(String pubKey) {
        try {
            RSAPublicKey publicKey = null;
            byte[] buffer = Base64.decode(pubKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 公钥加密过程
     *
     * @param plainData
     *            明文数据
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encryptWithRSA(String plainData,RSAPublicKey publicKey) throws Exception {
        if (publicKey == null) {
            throw new NullPointerException("encrypt PublicKey is null !");
        }

        Cipher cipher = null;
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"加密出来的信息JAVA服务器无法解析

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(plainData.getBytes("utf-8"));
        // 必须先encode成 byte[]，再转成encodeToString，否则服务器解密会失败
        byte[] encode = Base64.encode(output, Base64.DEFAULT);
        return Base64.encodeToString(encode, Base64.DEFAULT);
    }

    /**
     * 公钥解密过程
     *
     * @param encryedData
     *            明文数据
     * @return
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String decryptWithRSA(String encryedData,RSAPublicKey publicKey) throws Exception {
        if (publicKey == null) {
            throw new NullPointerException("decrypt PublicKey is null !");
        }

        Cipher cipher = null;
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 此处如果写成"RSA"解析的数据前多出来些乱码
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(Base64.decode(encryedData, Base64.DEFAULT));
        return new String(output);
    }
    /**************************** RSA 公钥加密解密**************************************/
}
