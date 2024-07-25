package com.example.bankaccounts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "deposite")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Deposite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Сумма депозита
    @Column(nullable = false)
    @Check(constraints = "sum > 0")
    private int sum;

    //Временной срок
    @Column(nullable = false)
    private int term;

    //Процентная ставка
    @Column(nullable = false)
    @Check(constraints = "interestRate >= 5 AND interestRate <= 10")
    private int interestRate;

    //Активацмрнный код
    @Column
    private int activationCode;

    //Статус
    @Column(nullable = false,columnDefinition = "boolean default false")
    private boolean isActive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;
}
