package com.example.bankaccounts.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "deposite6")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Check(constraints = "sum > 0")
    private int sum;

    @Column(nullable = false)
    private int term;

    @Column(nullable = false)
    @Check(constraints = "interestRate >= 5 AND interestRate <= 10")
    private int interestRate;

    @Column
    private int activationCode;

    @Column(nullable = false,columnDefinition = "boolean default false")
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;
}
