package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1/user")
public interface UserController {
    @Operation(summary = "adding new phone for user", description = "Get the phone", tags={ "addPhone" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/addPhone",produces = { "application/json" })
    public ResponseEntity<Object> addPhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for adding" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                           Principal principal);

    @Operation(summary = "adding new email for user", description = "Get the email", tags={ "addEmail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/addEmail",produces = { "application/json" })
    public ResponseEntity<Object> addEmail(@Parameter(in = ParameterIn.QUERY, description = "The phone for adding" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                           Principal principal);

    @Operation(summary = "change phone for user", description = "Get the phone", tags={ "ChangePhone" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/changePhone",produces = { "application/json" })
    public ResponseEntity<Object> ChangePhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for changing" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                              Principal principal);

    @Operation(summary = "change email for user", description = "Get the email", tags={ "ChangeEmail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/changeEmail",produces = { "application/json" })
    public ResponseEntity<Object> ChangeEmail(@Parameter(in = ParameterIn.QUERY, description = "The email for changing" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                              Principal principal);

    @Operation(summary = "delete phone for user", description = "Get the phone", tags={ "deletePhone" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/deletePhone",produces = { "application/json" })
    public ResponseEntity<Object> DeletePhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for deleting" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                              Principal principal);

    @Operation(summary = "delete email for user", description = "Get the email", tags={ "deleteEmail" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/deleteEmail",produces = { "application/json" })
    public ResponseEntity<Object> DeleteEmail(@Parameter(in = ParameterIn.QUERY, description = "The email for deleting" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                              Principal principal);

    @Operation(summary = "collect list of users that more then given birthday", description = "Get the birthday", tags={ "birthday" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/birthdayFilter",produces = { "application/json" })
    public ResponseEntity<Object> filterForBirthday(@Parameter(in = ParameterIn.QUERY, description = "The date for filter" ,required=true,schema=@Schema(implementation = LocalDateTime.class))@RequestParam("birthday") LocalDateTime birthday);

    @Operation(summary = "find the user by phone", description = "Get the phone", tags={ "phone" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/tel",produces = { "application/json" })
    public ResponseEntity<Object> findByTel(@Parameter(in = ParameterIn.QUERY, description = "The phone foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone);


    @Operation(summary = "find the user by email", description = "Get the email", tags={ "email" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/email",produces = { "application/json" })
    public ResponseEntity<Object> findByEmail(@Parameter(in = ParameterIn.QUERY, description = "The phone foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email);

    @Operation(summary = "find the users that fio starts with given string", description = "Get the fio", tags={ "fio" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/fio",produces = { "application/json" })
    public ResponseEntity<Object>  findByFio(@Parameter(in = ParameterIn.QUERY, description = "The fio foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("fio")String fio);

    @Operation(summary = "Transfer monyy from 1 account to another", description = "Get the amount of money and id of user who will get it ", tags={ "transfer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/transfer/{id}",produces = { "application/json" })
    public ResponseEntity<Object> transfer(@Parameter( description = "The amount of money request", required=true, schema=@Schema(implementation = SendMoneyRequest.class))@Valid @RequestBody SendMoneyRequest request,
                                           @Parameter(in = ParameterIn.PATH, description = "The id who will get money" ,required=true,schema=@Schema(implementation = Long.class))@PathVariable("id") Long id,
                                           BindingResult bindingResult,
                                           Principal principal);

}
