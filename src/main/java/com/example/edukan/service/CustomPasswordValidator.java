package com.example.edukan.service;

import org.springframework.stereotype.Service;

@Service

public class CustomPasswordValidator {

    public boolean validatePassword(String password) {
        // Şifrə ən az 8 char uzunluğunda olmalı
        // Ən az bir böyük hərf olmalı
        // Ən az bir rəqəm olmalı
        return password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}