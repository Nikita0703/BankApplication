package com.example.bankaccounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneExistsException extends RuntimeException {
    public PhoneExistsException(String s){super(s);}
}
