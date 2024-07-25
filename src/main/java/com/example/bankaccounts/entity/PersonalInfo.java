package com.example.bankaccounts.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //тип паспорта
    @Column(nullable = false)
    private char passportType;

    //id паспорта
    @Column(nullable = false)
    private  String  passportID;

    //имя
    @Column(nullable = false)
    private String firstName;

    //фамилия
    @Column(nullable = false)
    private String lastName;

    //дата рождения
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime birthday;

    public String getFio(){
        return firstName+lastName;
    }

}
