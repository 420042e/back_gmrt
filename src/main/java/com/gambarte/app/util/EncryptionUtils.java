package com.gambarte.app.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    // Deben ser los mismos valores utilizados en Kotlin
    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes
    private static final String IV = "1234567890123456"; // 16 bytes

    public static String decrypt(String encryptedText) throws Exception {
        // Inicializamos el cifrador para desencriptar
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes("UTF-8"));

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // Decodificamos la cadena en Base64 antes de desencriptar
        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);

        // Desencriptamos la cadena
        byte[] decryptedBytes = cipher.doFinal(decodedEncryptedText);

        // Convertimos los bytes desencriptados en texto claro
        return new String(decryptedBytes, "UTF-8");
    }
}
