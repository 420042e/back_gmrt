package com.gambarte.app.service;

import org.springframework.stereotype.Service;

import com.gambarte.app.util.AESUtil;

@Service
public class EncryptionService {

    private static final String SECRET_KEY = "1234567890123456";  // Debe ser de 16, 24 o 32 caracteres
    private static final String IV = "1234567890123456";  // El mismo IV utilizado en el cifrado

    public String decryptData(String encryptedData) {
        try {
            // Desencriptar utilizando la clave secreta y el IV
            return AESUtil.decrypt(encryptedData, SECRET_KEY, IV);
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // En caso de error
        }
    }
}
