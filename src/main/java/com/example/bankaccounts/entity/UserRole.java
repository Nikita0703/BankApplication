package com.example.bankaccounts.entity;

import com.example.bankaccounts.entity.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //id пользователя кому принадлежит
    private int user_id;

    //значение
    @Enumerated(EnumType.STRING)
    private ERole roles;
}
