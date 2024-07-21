package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.dto.HistoryItemDTO;
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
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/account")
public interface BankAccountController {

    @Operation(summary = "serach for user", description = "Get the user by account", tags={ "findUserByAccount" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByAccount/{id}",produces = { "application/json" })
    public UserDTO getUserByAccount(@Parameter(in = ParameterIn.PATH, description = "The id of bankAccount" ,required=true,schema=@Schema(implementation = Integer.class))
                                        @PathVariable int id);

    @Operation(summary = "serach for user", description = "Get account by user", tags={ "findAccountByUser" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getAccountByUser",produces = { "application/json" })
    public BankAccountDTO getAccountByUser(Principal principal);


    @Operation(summary = "create card", description = "create card by user", tags={ "createCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/createCard",produces = { "application/json" })
    public Object createCard(Principal principal);

    @Operation(summary = "search for user", description = "get card by user", tags={ "getCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getCardByUser",produces = { "application/json" })
    public CardDTO getCardByUser(Principal principal);

    @Operation(summary = "search for user", description = "get user by card", tags={ "getUserByCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByCard/{cardNumber}",produces = { "application/json" })
    public UserDTO getUserByCard(@Parameter(in = ParameterIn.PATH, description = "The card Number" ,required=true,schema=@Schema(implementation = Integer.class))
                                     @PathVariable int cardNumber);

    @Operation(summary = "put money for user", description = "put money on users bankAccount", tags={ "putMoney" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/putMoney",produces = { "application/json" })
    public Object putMoneyOnCard(@Parameter(in = ParameterIn.QUERY, description = "The sum of money" ,required=true,schema=@Schema(implementation = Integer.class))
                                     @RequestParam int sum,
                                        Principal principal);


    @Operation(summary = "Transfer monyy from 1 account to another", description = "Get the amount of money and id of user who will get it ", tags={ "transfer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/transferMoney/{id}",produces = { "application/json" })
    public Object transferMoney(@Parameter( description = "The amount of money request", required=true, schema=@Schema(implementation = SendMoneyRequest.class))@Valid @RequestBody SendMoneyRequest request,
                                @Parameter(in = ParameterIn.PATH, description = "The id who will get money" ,required=true,schema=@Schema(implementation = Integer.class))@PathVariable("id") int cardNumber,
                                BindingResult bindingResult,
                                Principal principal);


    @Operation(summary = "get history of user", description = "Get the history of user ", tags={ "history" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoryItemDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getHistory",produces = { "application/json" })
    public List<HistoryItemDTO> getHistory(Principal principal);

    @Operation(summary = "create deposite", description = "create card by user", tags={ "createDeposite" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/createDeposite",produces = { "application/json" })
    public Object createDeposite(@Parameter(in = ParameterIn.QUERY, description = "The sum of money" ,required=true,schema=@Schema(implementation = Integer.class))@RequestParam int sum,
                                 Principal principal);

    @Operation(summary = "search for user", description = "get user by deposite", tags={ "getUserByDep" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByDeposite/{id}",produces = { "application/json" })
    public UserDTO getUserByDeposite(@Parameter(in = ParameterIn.PATH, description = "The id of deposite" ,required=true,schema=@Schema(implementation = Integer.class))@PathVariable int id);

    @GetMapping("/approveDeposite")
    public Object approveDeposite(int activationCode,Principal principal);
}
