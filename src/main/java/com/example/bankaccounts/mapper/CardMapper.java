package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardDTO toCardDTO(Card card){
        CardDTO cardDTO = CardDTO.builder()
                .cardNumber(card.getCardNumber())
                .cardHolderName(card.getCardHolderName())
                .expirationDate(card.getExpirationDate())
                .cvv(card.getCvv())
                .balance(card.getBalance())
                .isActive(card.isActive())
                .build();
        return cardDTO;
    }

    public Card toCard(CardDTO card){
        Card cardd = Card.builder()
                .cardNumber(card.getCardNumber())
                .cardHolderName(card.getCardHolderName())
                .expirationDate(card.getExpirationDate())
                .cvv(card.getCvv())
                .balance(card.getBalance())
                .isActive(card.isActive())
                .build();
        return cardd;
    }
}
