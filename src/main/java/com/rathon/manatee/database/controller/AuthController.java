package com.rathon.manatee.database.controller;

import com.rathon.manatee.database.dto.LoginDto;
import com.rathon.manatee.database.model.Employee;
import com.rathon.manatee.database.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/test")
public class AuthController {
//    private EmployeeService eService;
//
//    public AuthController(EmployeeService eService) {
//        this.eService = eService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(LoginDto info) {
//        Employee employee = eService.getEmployeeByUsername(info.username());
//        String encrypted = null;
//        try {
//            encrypted = Encryption.encrypt(info.password());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        if (employee.getPasswordHash().equals(encrypted)) return ResponseEntity.accepted().build();
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(String msg) {
//
//
//        return ResponseEntity.ok().build();
//    }
//
//    private static class Encryption {
//        private static final String algorithm = "SHA-512";
//
//        public static String encrypt(String msg) throws NoSuchAlgorithmException {
//            MessageDigest m = MessageDigest.getInstance(algorithm);
//            m.update(msg.getBytes(StandardCharsets.UTF_8));
//            return Base64.getEncoder().encodeToString(m.digest());
//        }
//    }
}
