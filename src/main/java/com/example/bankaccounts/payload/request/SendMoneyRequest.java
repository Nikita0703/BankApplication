package com.example.bankaccounts.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
public class SendMoneyRequest {
    @Min(0)
    private int amount;

}
