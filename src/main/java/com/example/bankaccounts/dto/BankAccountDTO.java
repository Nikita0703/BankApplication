package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.Deposite;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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

    private DepositeDTO deposite;

    private CardDTO card;

    private List<HistoryItemDTO> historyItems= new ArrayList<>();
}
