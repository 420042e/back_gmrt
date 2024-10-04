package com.gambarte.app.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";  // Algoritmo de cifrado AES con modo CBC y relleno PKCS5

    // Método para desencriptar
    public static String decrypt(String encryptedData, String secretKey, String iv) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));  // Descodificar IV
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");  // Convertir la clave en bytes

        Cipher cipher = Cipher.getInstance(ALGORITHM);  // Obtener instancia del cifrador
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);  // Inicializar para desencriptar
        byte[] decodedEncryptedData = Base64.getDecoder().decode(encryptedData);  // Decodificar datos cifrados
        byte[] decryptedBytes = cipher.doFinal(decodedEncryptedData);  // Desencriptar los datos

        return new String(decryptedBytes);  // Convertir a texto
    }
}
