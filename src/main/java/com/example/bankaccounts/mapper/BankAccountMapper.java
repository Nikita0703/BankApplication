package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.entity.BankAccount;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    public BankAccount toBankAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = BankAccount.builder().
                id(bankAccountDTO.getId()).
                schet(bankAccountDTO.getSchet()).
                build();
        return bankAccount;
    }

    public BankAccountDTO toBankAccountDTO(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder().
                id(bankAccount.getId()).
                schet(bankAccount.getSchet()).
                build();
        return bankAccountDTO;
    }
}
