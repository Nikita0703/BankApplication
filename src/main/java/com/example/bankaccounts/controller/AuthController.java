package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.payload.request.LoginRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/auth")
public interface AuthController {
    @Operation(summary = "This is adding new user", description = "Get the UserDTO", tags={ "add" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/add", produces = { "application/json" })
    public ResponseEntity<Object> saveUser(@Parameter( description = "The given user for add", required=true, schema=@Schema(implementation = UserDTO.class)) @Valid @RequestBody UserDTO user,
                                           BindingResult result);

    @Operation(summary = "This is for sign in", description = "Get the username and password", tags={ "sigh" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/singin",produces = { "application/json" })
    public ResponseEntity<Object> authenticateUser(@Parameter( description = "The request for sign in", required=true, schema=@Schema(implementation = LoginRequest.class))@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult result);
}
