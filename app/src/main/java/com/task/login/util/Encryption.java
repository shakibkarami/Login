package com.task.login.util;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class Encryption {

    public final String privateKey = "Pr1vat3k3y1s00!?";

    public String encrypt(String value) {
        try {
            String stringIV = getRandomIV();
            IvParameterSpec iv = new IvParameterSpec(stringIV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            String encryptedString = Base64.encodeToString(encrypted, Base64.DEFAULT);
            return stringIV + encryptedString;
        } catch (Exception ex) {
            Log.e("Encryption Error", ex.toString());
            return null;
        }
    }

    public String decrypt(String encrypted) {
        try {
            String stringIV = encrypted.substring(0, 16);
            IvParameterSpec iv = new IvParameterSpec(stringIV.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] encryptedBytes = Base64.decode(encrypted.substring(16), Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            Log.e("Decryption Error", ex.toString());
            return null;
        }
    }

    public static String getRandomIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return UUID.randomUUID().toString().replaceAll("[_-]", "").substring(0, 16);
    }
}