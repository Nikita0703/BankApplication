package com.example.bankaccounts.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {
    private Long id;
    @NotEmpty
    private int schet;
}
