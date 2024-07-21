package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.dto.HistoryItemDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.Card;
import com.example.bankaccounts.entity.HistoryItem;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.BankAccountServiceImpl;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BankAccountControllerImpl implements BankAccountController{
    private final BankAccountServiceImpl bankAccountService;
    private final ResponseErrorValidation responseErrorValidation;

    @Override
    public UserDTO getUserByAccount(@PathVariable int id){
       return bankAccountService.getUserByAccount(id);
    }

    @Override
    public BankAccountDTO getAccountByUser(Principal principal){
        return bankAccountService.getAccountByUser(principal);
    }

    @Override
    public Object createCard(Principal principal){
        bankAccountService.createCard(principal);
        return ResponseEntity.ok(new MessageResponse("Card ceeate successfully"));
    }

    @Override
    public CardDTO getCardByUser(Principal principal){
        return bankAccountService.getCardByUser(principal);
    }

    @Override
    public UserDTO getUserByCard(@PathVariable int cardNumber){
        return bankAccountService.getUserByCard(cardNumber);
    }

    @Override
    public Object putMoneyOnCard(@RequestParam int sum,Principal principal) {
        bankAccountService.putMoneyOnCard(sum,principal);
        return ResponseEntity.ok(new MessageResponse("Put money on your balance"));
    }

    @Override
    public Object transferMoney(@Valid @RequestBody SendMoneyRequest request,
                                @PathVariable("id") int cardNumber,
                                BindingResult bindingResult,
                                Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        bankAccountService.transferMoney(request,cardNumber,principal);
        return ResponseEntity.ok("Success Tranfer");
    }

    @Override
    public List<HistoryItemDTO> getHistory(Principal principal){
        return bankAccountService.getHistory(principal);
    }

    @Override
    public Object createDeposite(@RequestParam int sum, Principal principal){
        bankAccountService.createDeposite(sum,principal);
        return ResponseEntity.ok(new MessageResponse("Deposite ceeate successfully"));
    }

    @Override
    public UserDTO getUserByDeposite(@PathVariable int id){
        return bankAccountService.getUserByDeposite(id);
    }

}
