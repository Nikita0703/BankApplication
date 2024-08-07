package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CardMapper.class, HistoryItemMapper.class, DepositeMapper.class})
public interface BankAccountMapper {

    @Named("fullBankAccount")
    @Mapping(target = "user", source = "user",qualifiedByName = "basicUser")
    BankAccountDTO toFullBankAccountDTO(BankAccount bankAccount);

    @Named("basicBankAccount")
    @Mapping(target = "user", ignore = true)
    BankAccountDTO toBasicBankAccountDTO(BankAccount bankAccount);

}
