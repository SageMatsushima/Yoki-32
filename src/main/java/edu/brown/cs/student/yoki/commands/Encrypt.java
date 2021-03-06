package edu.brown.cs.student.yoki.commands;
import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.TriggerAction;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;


public class Encrypt implements TriggerAction {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  public static void setKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public static String encrypt(String strToEncrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String decrypt(String strToDecrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }

  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {
    if (args.size() == 3) {
      String encryptionKey = args.get(1);
      String argument = args.get(2);
      setKey(encryptionKey);
      System.out.println("Encryption key: " + encryptionKey);
      System.out.println("Original message: " + argument);
      String encyrptedMessage = encrypt(argument, encryptionKey);
      System.out.println("Encrypted message: " + encyrptedMessage);
      System.out.println("Decrypted message: " + decrypt(encyrptedMessage, encryptionKey));
      Main.keyreader();
    } else {
      System.err.println("ERROR: the encrypt command must follow the form <[encrypt] [key] [string]>");
    }
  }
}
