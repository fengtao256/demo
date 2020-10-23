package com.example.secrite;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    public static final String algorithm = "AES";
    // AES/CBC/NOPaddin
    // AES 默认模式
    // 使用CBC模式, 在初始化Cipher对象时, 需要增加参数, 初始化向量IV : IvParameterSpec iv = new
    // IvParameterSpec(key.getBytes());
    // NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍
    public static final String transformation = "AES/CBC/NOPadding";
    public static final String key = "1234567812345678";

    /***
     * 加密
     * @param original 需要加密的参数（注意必须是16位）
     * @return
     * @throws Exception
     */
    public static String encryptByAES(String original) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(加密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        // 加密
        byte[] bytes = cipher.doFinal(original.getBytes());

        return AESUtil.encryptBASE64(bytes);
    }

    /**
     * 解密
     * @param encrypted 需要解密的参数
     * @return
     * @throws Exception
     */
    public static String decryptByAES(String encrypted) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(transformation);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        // 指定模式(解密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.DECRYPT_MODE, keySpec);
        // 解密
        byte[] bytes = cipher.doFinal(AESUtil.decryptBASE64(encrypted.getBytes()));

        return new String(bytes);
    }

    public static void main(String[] args) throws Exception{
        String password = "HKKnowledgeStudio" ;
        System.out.println("密码："+ password);
        String password01 = AESUtil.encryptBASE64(password.getBytes()) ;
        System.out.println("加密："+password01);
        String password02 = new String(AESUtil.decryptBASE64(password01.getBytes())) ;
        System.out.println("解密："+password02);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(byte[] key) throws Exception {
        return Base64Utils.decode(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return new String(Base64Utils.encode(key));
    }

}