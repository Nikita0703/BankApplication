package com.example.bankaccounts.controller;

import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @PutMapping("/addPhone")
    public void addPhone(@RequestParam("phone")String phone, Principal principal){
        userService.addTelephoneNumber(phone,principal);
    }

    @PutMapping("/addEmail")
    public void addEmail(@RequestParam("email")String email,Principal principal){
        userService.addEmail(email,principal);
    }

    @PutMapping("/changePhone")
    public void ChangePhone(@RequestParam("phone")String phone,Principal principal){
        userService.changePhone(phone,principal);
    }

    @PutMapping("/changeEmail")
    public void ChangeEmail(@RequestParam("email")String email,Principal principal){
        userService.changeEmail(email,principal);
    }

    @PutMapping("/deletePhone")
    public void DeletePhone(@RequestParam("phone")String phone,Principal principal){
        userService.deletePhone(phone,principal);
    }

    @PutMapping("/deleteEmail")
    public void DeleteEmail(@RequestParam("email")String email,Principal principal){
        userService.deleteEmail(email,principal);
    }

    @GetMapping("/birthdayFilter")
    public List<User> filterForBirthday(@RequestParam("birthday") LocalDateTime birthday){
        return userService.filterByBirthday(birthday);
    }

    @GetMapping("/tel")
    public User findByTel(@RequestParam("phone")String phone){
        return userService.findByPhone(phone);
    }

    @GetMapping("/email")
    public User findByEmail(@RequestParam("email")String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/fio")
    public List<User> findByFio(@RequestParam("fio")String fio){
        return userService.findByFio(fio);
    }

    @PutMapping("/transfer")
    public ResponseEntity<Object> transfer(@Valid @RequestBody SendMoneyRequest request,
                                           @RequestParam("id") Long id,
                                           BindingResult bindingResult,
                                           Principal principal)
    {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.transferMoney(request,id,principal);
        return ResponseEntity.ok("Success Tranfer");
    }

}
