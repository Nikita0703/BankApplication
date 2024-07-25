package com.example.bankaccounts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    //дата перевода
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime creationDate;

    //сумма перевода
    @Column(nullable = false,updatable = false)
    private int sum;

    //описание перевода(кому,куда,цель)
    @Column(nullable = false,updatable = false)
    private String description;
}
