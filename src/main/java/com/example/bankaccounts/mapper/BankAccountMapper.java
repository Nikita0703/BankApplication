package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.entity.BankAccount;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    private final UserMapper userMapper;

    public BankAccountMapper(@Lazy UserMapper adressMapper) {
        this.userMapper = adressMapper;
    }
    public BankAccount toBankAccount(BankAccountDTO bankAccount){
    BankAccount bankAccountt = BankAccount.builder()
        .id(bankAccount.getId())
            .identicalNumber(bankAccount.getUUID())
                .creationDate(bankAccount.getCreationDate())
                    .card(bankAccount.getCard())
                        .deposite(bankAccount.getDeposite())
                              .pets(bankAccount.getPets()).build();
        return bankAccountt;
    }

    public BankAccountDTO toBankAccountDTO(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder().
                id(bankAccount.getId())
                   .UUID(bankAccount.getIdenticalNumber())
                        .creationDate(bankAccount.getCreationDate())
                                .card(bankAccount.getCard())
                                        .deposite(bankAccount.getDeposite())
                                                .pets(bankAccount.getPets())
                .build();
        return bankAccountDTO;
    }


    public BankAccountDTO toBankAccountDTOFull(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder().
                id(bankAccount.getId())
                .UUID(bankAccount.getIdenticalNumber())
                .creationDate(bankAccount.getCreationDate())
                .user(userMapper.toUserDTO(bankAccount.getUser()))
                .card(bankAccount.getCard())
                .deposite(bankAccount.getDeposite())
                .pets(bankAccount.getPets())
                .build();
        return bankAccountDTO;
    }
}
