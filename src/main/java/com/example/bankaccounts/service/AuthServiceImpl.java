package com.example.bankaccounts.service;

import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.security.JWTTokenProvider;
import com.example.bankaccounts.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    /**
     * Приходит loginrequest с паролем и именем пользователя для автоизации.
     *
     * @param loginRequest запрос на авторизацию
     * @return токен
     */
    @Override
    public String auth(LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateWebToken(authentication);
        return jwt;
    }
}
