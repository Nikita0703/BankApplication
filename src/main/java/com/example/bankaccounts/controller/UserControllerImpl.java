package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.payload.request.DataRequest;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.service.UserServiceImpl;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController{
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> addPhone(String phone,
                                           Principal principal){
        userService.addTelephoneNumber(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone added successfully"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> addEmail(String email,
                                           Principal principal){
        userService.addEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email added successfully"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> ChangePhone(String phone,
                                              Principal principal){
        userService.changePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone changed successfully"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> ChangeEmail(String email,
                                              Principal principal){
        userService.changeEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email changed successfully"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> DeletePhone(String phone,
                                              Principal principal){
        userService.deletePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone deleted successfully"));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Override
    public ResponseEntity<Object> DeleteEmail(String email,
                                              Principal principal){
        userService.deleteEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email deleted successfully"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Object> filterForBirthday(DataRequest dataRequest,BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        List<UserDTO> list = userService.filterByBirthday(dataRequest.getDate());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Object> findByTel(String phone){
        UserDTO userDTO =  userService.findByPhone(phone);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Object> findByEmail(String email){
        UserDTO userDTO = userService.findByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseEntity<Object>  findByFio(String fio){
        List<UserDTO> list = userService.findByFio(fio);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
