package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.Card;
import com.example.bankaccounts.entity.Deposite;
import com.example.bankaccounts.entity.HistoryItem;
import com.example.bankaccounts.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDTO {
    private int id;

    private int UUID;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    private UserDTO user;

    private Deposite deposite;

    private Card card;

    private List<HistoryItem> pets= new ArrayList<>();
}
