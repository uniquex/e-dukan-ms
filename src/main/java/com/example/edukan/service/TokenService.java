package com.example.edukan.service;

import com.example.edukan.entity.UserEntity;
import com.example.edukan.model.dto.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    public TokenDto generateToken(UserEntity user) {
        String accessToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Token expiration time 1 hour
                .signWith(SignatureAlgorithm.HS512, "yourSecretKey")
                .compact();

        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setTokenType("Bearer");
        tokenDto.setExpiresIn(3600L);

        return tokenDto;
    }
}

