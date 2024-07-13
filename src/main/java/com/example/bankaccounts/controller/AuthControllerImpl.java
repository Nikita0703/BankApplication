package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.payload.response.JWTTokenSuccessResponse;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.security.JWTTokenProvider;
import com.example.bankaccounts.security.SecurityConstants;
import com.example.bankaccounts.service.UserServiceImpl;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController{
    private final ResponseErrorValidation responseErrorValidation;
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<Object> saveUser(@Parameter( description = "The given user for add", required=true, schema=@Schema(implementation = UserDTO.class)) @Valid @RequestBody UserDTO user,
                                           BindingResult result){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.createUserFromDTO(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully"));
    }

    @Override
    public ResponseEntity<Object> authenticateUser(@Parameter( description = "The request for sign in", required=true, schema=@Schema(implementation = LoginRequest.class))@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult result) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateWebToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt, true));
    }

}
