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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private List<String> emails = new ArrayList<>();
    @NotEmpty
    private List<String> phones = new ArrayList<>();
    @NotEmpty
    private BankAccountDTO bankAccount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
    @NotEmpty
    private String fio;
    private Collection<? extends GrantedAuthority> authorities;
}
