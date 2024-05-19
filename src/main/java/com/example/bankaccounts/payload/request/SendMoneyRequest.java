package com.example.bankaccounts.payload.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Min;

@Data
public class SendMoneyRequest {
    @Min(0)
    private int amount;
}
