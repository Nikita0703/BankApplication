package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.Card;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.BankAccountServiceImpl;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class BankAccountControllerImpl {
    private final BankAccountServiceImpl bankAccountService;
    private final ResponseErrorValidation responseErrorValidation;

  //  @PostMapping("/createCard")
   // public Object create(@RequestBody Card card,Principal principal) {
       // User user = userService.getUserByPrincipal(principal);
      //  Card card = new Card();
       // card.setCardNumber("1234567890123456");
       // card.setCardHolderName("John Doe");
       // card.setExpirationDate(LocalDateTime.of(2023, 12, 31, 23, 59, 59));
       // card.setCvv(123);
       // card.setBalance(1000.00);
       // user.getBankAccount().setCard(card);
       // userService.createUser(user);
      //  return ResponseEntity.ok(new MessageResponse("Card added successfully"));
  // }

   // @GetMapping("/getCard")
   // public Object getCardOfUSer(Principal principal){
      //  User user = userService.getUserByPrincipal(principal);
       // return user.getBankAccount().getCard();
   // }

    @GetMapping("/getUserByAccount/{id}")
    public UserDTO getUserByAccount(@PathVariable int id){
       return bankAccountService.getUserByAccount(id);
    }

    @GetMapping("/getAccountByUser")
    public BankAccountDTO getAccountByUser(Principal principal){
        return bankAccountService.getAccountByUser(principal);
    }

    @PostMapping("/createCard")
    public Object createCard(Principal principal){
        bankAccountService.createCard(principal);
        return ResponseEntity.ok(new MessageResponse("Card ceeate successfully"));
    }

    @GetMapping("/getCardByUser")
    public CardDTO getCardByUser(Principal principal){
        return bankAccountService.getCardByUser(principal);
    }

    @GetMapping("/getUserByCard/{cardNumber}")
    public UserDTO getUserByCard(@PathVariable int cardNumber){
        return bankAccountService.getUserByCard(cardNumber);
    }

    @PutMapping("/putMoney")
    public Object putMoneyOnCard(@RequestParam int sum,Principal principal) {
        bankAccountService.putMoneyOnCard(sum,principal);
        return ResponseEntity.ok(new MessageResponse("Put money on your balance"));
    }

    @PutMapping
    public Object transferMoney(@Valid @RequestBody SendMoneyRequest request,
                                @PathVariable("id") int cardNumber,
                                BindingResult bindingResult,
                                Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        bankAccountService.transferMoney(request,cardNumber,principal);
        return ResponseEntity.ok("Success Tranfer");
    }



}
