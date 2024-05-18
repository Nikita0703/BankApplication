package com.example.bankaccounts.repository;

import com.example.bankaccounts.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailsRepository extends JpaRepository<Email,Long> {
}
