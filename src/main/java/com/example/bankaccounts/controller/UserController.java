package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> addPhone(@RequestParam("phone")String phone, Principal principal){
        userService.addTelephoneNumber(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone added successfully"));
    }

    @PutMapping("/addEmail")
    public ResponseEntity<Object> addEmail(@RequestParam("email")String email,Principal principal){
        userService.addEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email added successfully"));
    }

    @PutMapping("/changePhone")
    public ResponseEntity<Object> ChangePhone(@RequestParam("phone")String phone,Principal principal){
        userService.changePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone changed successfully"));
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<Object> ChangeEmail(@RequestParam("email")String email,Principal principal){
        userService.changeEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email changed successfully"));
    }

    @PutMapping("/deletePhone")
    public ResponseEntity<Object> DeletePhone(@RequestParam("phone")String phone,Principal principal){
        userService.deletePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone deleted successfully"));
    }

    @PutMapping("/deleteEmail")
    public ResponseEntity<Object> DeleteEmail(@RequestParam("email")String email,Principal principal){
        userService.deleteEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email deleted successfully"));
    }

    @GetMapping("/birthdayFilter")
    public ResponseEntity<Object> filterForBirthday(@RequestParam("birthday") LocalDateTime birthday){
        List<UserDTO> list = userService.filterByBirthday(birthday);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/tel")
    public ResponseEntity<Object> findByTel(@RequestParam("phone")String phone){
        UserDTO userDTO =  userService.findByPhone(phone);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<Object> findByEmail(@RequestParam("email")String email){
        UserDTO userDTO = userService.findByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/fio")
    public ResponseEntity<Object>  findByFio(@RequestParam("fio")String fio){
        List<UserDTO> list = userService.findByFio(fio);
        return new ResponseEntity<>(list, HttpStatus.OK);
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
