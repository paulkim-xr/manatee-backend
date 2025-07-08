package com.rathon.manatee.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Date;

class SecurityConfigTest {

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
}