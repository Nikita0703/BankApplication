package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.payload.response.JWTTokenSuccessResponse;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.service.AuthService;
import com.example.bankaccounts.service.AuthServiceImpl;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.service.UserServiceImpl;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController{
    private final ResponseErrorValidation responseErrorValidation;
    private final UserService userService;
    private final AuthService authService;

    @Override
    public ResponseEntity<Object> saveUser( UserDTO user, BindingResult result){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.createUserFromDTO(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully"));
    }

    @Override
    public ResponseEntity<Object> authenticateUser(LoginRequest loginRequest, BindingResult result) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        String jwt = authService.auth(loginRequest);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt, true));
    }

}
