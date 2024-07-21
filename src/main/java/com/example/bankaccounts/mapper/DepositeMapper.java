package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.DepositeDTO;
import com.example.bankaccounts.entity.Deposite;
import org.springframework.stereotype.Component;

@Component
public class DepositeMapper {

    DepositeDTO toDepositeDTO(Deposite deposite){
        DepositeDTO depositeDTO = DepositeDTO.builder()
                .id(deposite.getId())
                .sum(deposite.getSum())
                .term(deposite.getTerm())
                .interestRate(deposite.getInterestRate())
                .activationCode(deposite.getActivationCode())
                .isActive(deposite.isActive())
                .build();
        return depositeDTO;
    }

    Deposite toDeposite(DepositeDTO deposite){
        Deposite depositee = Deposite.builder()
                .id(deposite.getId())
                .sum(deposite.getSum())
                .term(deposite.getTerm())
                .interestRate(deposite.getInterestRate())
                .activationCode(deposite.getActivationCode())
                .isActive(deposite.isActive())
                .build();
        return depositee;
    }
}
