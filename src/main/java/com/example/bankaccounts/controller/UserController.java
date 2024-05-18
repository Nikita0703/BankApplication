package com.example.bankaccounts.controller;

import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public void saveUser(@RequestBody User user){
        userService.createUser(user);
    }

    @PutMapping("/addPhone")
    public void addPhone(@RequestParam("phone")String phone){
        userService.addTelephoneNumber(phone);
    }

    @PutMapping("/addEmail")
    public void addEmail(@RequestParam("email")String email){
        userService.addEmail(email);
    }

    @PutMapping("/changePhone")
    public void ChangePhone(@RequestParam("phone")String phone){
        userService.changePhone(phone);
    }

    @PutMapping("/changeEmail")
    public void ChangeEmail(@RequestParam("email")String email){
        userService.changeEmail(email);
    }

    @PutMapping("/deletePhone")
    public void DeletePhone(@RequestParam("phone")String phone){
        userService.deletePhone(phone);
    }

    @PutMapping("/deleteEmail")
    public void DeleteEmail(@RequestParam("email")String email){
        userService.deleteEmail(email);
    }



}
