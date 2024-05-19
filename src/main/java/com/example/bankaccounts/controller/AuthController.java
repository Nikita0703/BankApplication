package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.payload.response.JWTTokenSuccessResponse;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.security.JWTTokenProvider;
import com.example.bankaccounts.security.SecurityConstants;
import com.example.bankaccounts.service.UserService;
import com.example.bankaccounts.validation.ResponseErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ResponseErrorValidation responseErrorValidation;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;


    @Operation(summary = "This is adding new user", description = "Get the UserDTO", tags={ "add" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/add", produces = { "application/json" })
    public ResponseEntity<Object> saveUser(@Parameter( description = "The given user for add", required=true, schema=@Schema(implementation = UserDTO.class)) @Valid @RequestBody UserDTO user,
                                           BindingResult result){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.createUserFromDTO(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully"));
    }

    @Operation(summary = "This is for sign in", description = "Get the username and password", tags={ "sigh" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/singin",produces = { "application/json" })
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
