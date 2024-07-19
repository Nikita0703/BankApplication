package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.BankAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private Set<String> emails = new HashSet<>();
    @NotEmpty
    private Set<String> phones = new HashSet<>();
    @NotNull
    private PersonalInfoDTO personalInfoDTO;
    private BankAccountDTO bankAccountDTO;
    private Collection<? extends GrantedAuthority> authorities;
}
