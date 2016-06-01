package com.aaronnebbs.thesociallink.plum.Encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
    static String IV = "SkIiUzmSDoxgXOcD";
    static String encryptionKey = "XTHuRzniwKhXNIte";
    
  public byte[] encrypt(String text) throws Exception {
    //checkString(text);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(text.getBytes("UTF-8"));
  }

  public String decrypt(byte[] cipherText) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(cipherText),"UTF-8");
  }
}
