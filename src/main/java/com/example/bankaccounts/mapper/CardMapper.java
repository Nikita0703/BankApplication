package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDTO cardToCardDTO(Card card);

    Card cardDTOToCard(CardDTO cardDTO);
}
