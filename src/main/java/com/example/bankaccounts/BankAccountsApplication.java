package com.example.bankaccounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BankAccountsApplication {
    @Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		ApplicationContext context =SpringApplication.run(BankAccountsApplication.class, args);
	//	BCryptPasswordEncoder bCryptPasswordEncoder = context.getBean(BCryptPasswordEncoder.class);
	//	String rawPassword = "nikita";
		//String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
	}

}
