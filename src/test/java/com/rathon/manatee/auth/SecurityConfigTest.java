package com.rathon.manatee.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Base64;
import java.util.Date;
import org.apache.commons.codec.binary.Base32;

class SecurityConfigTest {

    @Test
    void stringPads() {
        byte[] bytes = Base64.getUrlDecoder().decode("82_pe-o4");
        String s = new Base32().encodeToString(bytes);
        System.out.println(s);
    }

    @Test
    void passwordEncoder() {


        Long start = new Date().getTime();
        System.out.println(start);
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("%1c,&z;",
                32,
                300000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
        encoder.setEncodeHashAsBase64(true);
        System.out.println(encoder.matches("1234", "NMWMqk6s0K2OaKk1fJFJ3APhhx/nZKlN8COZj3FfG/U0WkduFUTBgf4nNbXmb9fIagA2MFJ0ieb4KnQ42/H1x+FyekfFkANiwBiygXx/iV7oF+EbUyoa6eKOokV7c8NX"));
        System.out.println(encoder.encode("1234"));
        Long end = new Date().getTime();
        System.out.println(end - start);

    }

    @Test
    public void encodeKeyHashFromHex() {
        // Replace this with your SHA-1 or SHA-256 (no colons, uppercase or lowercase both fine)
        String shaHex = "31D43D5ACF5061183C1A82403486F7A4DA353F0132FC3D2793BA0B73E7171758";

        byte[] bytes = hexStringToByteArray(shaHex);
        String base64KeyHash = Base64.getUrlEncoder().encodeToString(bytes);

        System.out.println("Base64 Key Hash: " + base64KeyHash);
        // Example output: SnfIrN2Zf+5mVUQzIhEqq7zN3e4=
    }

    // Utility method to convert hex string to byte array
    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        if (len % 2 != 0) throw new IllegalArgumentException("Hex string must have even length.");
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte)
                    ((Character.digit(hex.charAt(i), 16) << 4)
                            + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}