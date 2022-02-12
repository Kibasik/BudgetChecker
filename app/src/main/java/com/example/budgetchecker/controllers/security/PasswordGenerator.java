package com.example.budgetchecker.controllers.security;

import com.example.budgetchecker.controllers.sharedPreferences.SharedPreferencesHelper;

import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// Класс для генерации зашифрованного пароля пользователя
public class PasswordGenerator {

    // Формирование salted пароля
    public static String generateSecurePassword(String userPass) {
        // Создание salt
        String salt = generateSalt();
        SharedPreferencesHelper.setString("salt", salt);
        return generateSaltedPassword(userPass, salt);
    }

    // Генерация salt для формирования пароля
    private static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    // Генерация защищенного salted пароля
    public static String generateSaltedPassword(String userPass, String salt) {
        String generatedPassword = null;
        byte[] hash = new byte[128];

        try {
            KeySpec spec = new PBEKeySpec(userPass.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            StringBuilder sb = new StringBuilder();

            try {
                hash = factory.generateSecret(spec).getEncoded();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }

            for (byte b : hash) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }
}
