package utils;

import com.google.gson.Gson;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import protocol.Envelope;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


public class EncryptionUtil {

  /**
   * String to hold name of the encryption algorithm.
   */


  public static KeyPair generateRSAKey() throws NoSuchAlgorithmException {
      final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
      keyGen.initialize(1024);
      final KeyPair key = keyGen.generateKeyPair();
      return key;
  }

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
        KeyGen.init(128);

        return KeyGen.generateKey();
    }

    public static byte[] encryptAES(String text, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher AesCipher = Cipher.getInstance("AES");
        byte[] byteText = text.getBytes();
        AesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return AesCipher.doFinal(byteText);
    }

    public static String decryptAES(byte[] text, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytePlainText = aesCipher.doFinal(text);
        return new String(bytePlainText, StandardCharsets.UTF_8);

    }

    public static String decryptJson(Map msg, String privateKey) throws DecoderException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        PrivateKey pk =
            KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec( Hex.decodeHex(privateKey.toCharArray())));

        byte[] aesKey = decryptRSA(Hex.decodeHex(msg.get("key").toString().toCharArray()),pk);
        SecretKey secretKey = new SecretKeySpec(aesKey,0,aesKey.length,"AES");
        return decryptAES(Hex.decodeHex(msg.get("content").toString().toCharArray()),secretKey);
    }


    public static byte[] encryptRSA(byte[] text, PublicKey key) {
    byte[] cipherText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance("RSA");
      // encrypt the plain text using the public key
      cipher.init(Cipher.ENCRYPT_MODE, key);
      cipherText = cipher.doFinal(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }
  public static byte[] decryptRSA(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance("RSA");

      // decrypt the text using the private key
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);

    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return dectyptedText;
  }

}
