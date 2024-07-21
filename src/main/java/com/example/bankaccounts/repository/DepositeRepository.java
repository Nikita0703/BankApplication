package com.example.bankaccounts.repository;

import com.example.bankaccounts.entity.Card;
import com.example.bankaccounts.entity.Deposite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositeRepository extends JpaRepository<Deposite,Integer> {
}
