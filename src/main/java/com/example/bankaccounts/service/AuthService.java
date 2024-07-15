package com.example.bankaccounts.service;

import com.example.bankaccounts.payload.request.LoginRequest;

public interface AuthService {
    public String auth(LoginRequest loginRequest);
}
