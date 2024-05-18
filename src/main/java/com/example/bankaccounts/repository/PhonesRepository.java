package com.example.bankaccounts.repository;

import com.example.bankaccounts.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhonesRepository extends JpaRepository<Phone,Long> {
}
