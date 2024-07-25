package com.example.bankaccounts.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_Accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //UUID аккаунта
    @Column(nullable = false,unique = true)
    private int identicalNumber;

    //дата создания аккаунта
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime creationDate;

    //пользователь чей этот аккаунт
    @OneToOne(mappedBy = "bankAccount",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;

    //депозит этого аккаунта
    @OneToOne(mappedBy = "bankAccount",cascade = CascadeType.ALL)
    private Deposite deposite;

    //карта аккаунта
    @OneToOne(mappedBy = "bankAccount",cascade = CascadeType.ALL)
    private Card card;

    //история операций
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private List<HistoryItem> historyItems= new ArrayList<>();

}
