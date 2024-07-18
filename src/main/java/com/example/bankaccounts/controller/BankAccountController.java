package com.example.bankaccounts.controller;

import com.example.bankaccounts.entity.Card;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class BankAccountController {
    private final UserService userService;
    @PostMapping("/create")
    public Object create(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Card card = new Card();
        card.setCardNumber("1234567890123456");
        card.setCardHolderName("John Doe");
        card.setExpirationDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59));
        card.setCvv(123);
        card.setBalance(1000.00);
        user.getBankAccount().setCard(card);
        userService.createUser(user);
        return ResponseEntity.ok(new MessageResponse("Card added successfully"));
    }

    @GetMapping("/getCard")
    public Object getCargOfUSer(Principal principal){
        User user = userService.getUserByPrincipal(principal);
        return user.getBankAccount().getCard();
    }
}
