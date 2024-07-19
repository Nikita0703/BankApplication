package com.example.bankaccounts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "card8")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private int cvv;

    @Column(nullable = false)
    @Check(constraints = "balance > 0")
    private double balance;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;

    @PrePersist
    protected void onCreate() {
        this.expirationDate = LocalDateTime.now().plusYears(1);
        this.balance = 0;
    }
}
