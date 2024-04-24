package com.task.login.util;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    private final String privateKey = "Pr1vat3k3y1s00!?";

    public String encrypt(String value) {
        try {
            String stringIV = getRandomIV();
            IvParameterSpec iv = new IvParameterSpec(stringIV.getBytes());
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(stringIV.getBytes());
            outputStream.write(encrypted);
            byte[] c = outputStream.toByteArray();
            String encryptedString = new String(encrypted, StandardCharsets.UTF_8);
            String toEncrypt = stringIV + encryptedString;
            return Base64.encodeToString(c, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            byte[] base64decoded = Base64.decode(encrypted, Base64.DEFAULT);
            byte[] byteiv = Arrays.copyOfRange(base64decoded, 0, 16);
            IvParameterSpec iv = new IvParameterSpec(byteiv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] bytemsg = Arrays.copyOfRange(base64decoded, 16, base64decoded.length);
            byte[] original;
            try {
                original = cipher.doFinal(bytemsg);
            } catch (Exception e) {
                Log.e("Decryption Error", e.toString());
                return null;
            }
            return Base64.encodeToString(original, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getRandomIV() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return UUID.nameUUIDFromBytes(iv).toString().replaceAll("[_-]", "").substring(0, 16);
    }
}