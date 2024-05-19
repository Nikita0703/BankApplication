package com.example.bankaccounts.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Data
public class SendMoneyRequest {
    @Min(value = 0,message = "Only more then 0")
    private int amount;

}
