package com.example.edukan.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TokenDto {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
}
