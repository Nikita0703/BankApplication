package com.example.bankaccounts.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@Data
public class CustomPrincipal {
    private Long id;
    private String username;
    private String password;
}
