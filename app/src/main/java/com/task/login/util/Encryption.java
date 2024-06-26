package com.task.login.util;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class Encryption {
    private static Encryption instance;
    private static final String TAG = "Encryption";
    private final SecretKeySpec secretKeySpec;

    private Encryption() {
        byte[] keyBytes = new byte[16];
        new SecureRandom().nextBytes(keyBytes);
        this.secretKeySpec = new SecretKeySpec(keyBytes, "AES");
    }

    public static Encryption getInstance() {
        if (instance == null) {
            synchronized (Encryption.class) {
                if (instance == null) {
                    instance = new Encryption();
                }
            }
        }
        return instance;
    }

    public String encrypt(String value) {
        try {
            String stringIV = getRandomIV();
            IvParameterSpec iv = new IvParameterSpec(stringIV.getBytes(StandardCharsets.UTF_8));
//            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            String encryptedString = Base64.encodeToString(encrypted, Base64.DEFAULT);
            return stringIV + encryptedString;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException ex) {
            Log.e(TAG, "Encryption Error", ex);
            return null;
        }
    }

    public String decrypt(String encrypted) {
        try {
            String stringIV = encrypted.substring(0, 16);
            IvParameterSpec iv = new IvParameterSpec(stringIV.getBytes(StandardCharsets.UTF_8));
//            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] encryptedBytes = Base64.decode(encrypted.substring(16), Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException ex) {
            Log.e(TAG, "Encryption Error", ex);
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