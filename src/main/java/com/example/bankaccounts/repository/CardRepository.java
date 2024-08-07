package com.example.bankaccounts.repository;

import com.example.bankaccounts.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
    Optional<Card> findByCvv(int cvv);

    Optional<Card> findByCardNumber(int cardNumber);

    @Query(value = "SELECT c FROM Card c WHERE c.balance = (SELECT MIN(c1.balance) FROM Card c1)")
    Optional<Card> findCardWithMinBalance();

    @Query("SELECT AVG(p.balance) FROM Card p")
    Double findAvgBalance();
}
