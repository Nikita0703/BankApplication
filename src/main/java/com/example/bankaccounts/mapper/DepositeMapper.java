package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.DepositeDTO;
import com.example.bankaccounts.entity.Deposite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepositeMapper {
    DepositeDTO depositeToDepositeDTO(Deposite deposite);
    Deposite depositeDTOToDeposite(DepositeDTO depositeDTO);
}
