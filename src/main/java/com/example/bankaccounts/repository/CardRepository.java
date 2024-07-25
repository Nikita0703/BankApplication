package com.example.bankaccounts.repository;

import com.example.bankaccounts.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByCvv(int cardNumber);

    Optional<Card> findByCardNumber(int cardNumber);
}
