package com.rathon.manatee.database.service;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

class CompanyServiceTest {

    @Test
    void test() throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("SHA-512");
        String pw = "123";
        byte[] bytes = pw.getBytes(Charset.forName("UTF-8"));

        instance.update(bytes);
        String encryptPw = Base64.getEncoder().encodeToString(instance.digest());
        System.out.println(encryptPw);

    }

}