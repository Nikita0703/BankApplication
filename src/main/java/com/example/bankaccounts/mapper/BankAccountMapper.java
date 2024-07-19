package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.entity.BankAccount;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    private final UserMapper userMapper;
    private final CardMapper cardMapper;

    public BankAccountMapper(@Lazy UserMapper adressMapper,
                             CardMapper cardMapper) {
        this.userMapper = adressMapper;
        this.cardMapper = cardMapper;
    }
    public BankAccount toBankAccount(BankAccountDTO bankAccount){
    BankAccount bankAccountt = BankAccount.builder()
        .id(bankAccount.getId())
            .identicalNumber(bankAccount.getUUID())
                .creationDate(bankAccount.getCreationDate())
                    .card(cardMapper.toCard(bankAccount.getCard()))
                        .deposite(bankAccount.getDeposite())
                              .pets(bankAccount.getPets()).build();
        return bankAccountt;
    }

    public BankAccountDTO toBankAccountDTO(BankAccount bankAccount){
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder().
                id(bankAccount.getId())
                   .UUID(bankAccount.getIdenticalNumber())
                        .creationDate(bankAccount.getCreationDate())
                                .card(cardMapper.toCardDTO(bankAccount.getCard()))
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
                .card(cardMapper.toCardDTO(bankAccount.getCard()))
                .deposite(bankAccount.getDeposite())
                .pets(bankAccount.getPets())
                .build();
        return bankAccountDTO;
    }
}
