package com.example.bankaccounts.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTTokenSuccessResponse {
    private String token;
    private boolean success;

}
