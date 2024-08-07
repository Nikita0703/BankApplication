package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.BankAccount;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Builder
public class CardDTO {
    private int id;
    private int cardNumber;
    private String cardHolderName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationDate;
    private int cvv;
    private double balance;
    private boolean isActive;
}
