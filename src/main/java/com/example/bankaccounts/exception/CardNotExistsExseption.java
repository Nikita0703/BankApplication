package com.example.bankaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardNotExistsExseption extends RuntimeException{
    public CardNotExistsExseption(String s){super(s);}
}
