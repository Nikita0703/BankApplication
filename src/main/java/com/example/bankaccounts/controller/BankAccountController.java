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

    @Operation(summary = "Поиск пользователя по аккаунту", description = "По id акаунта", tags={ "findUserByAccount" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByAccount/{id}",produces = { "application/json" })
    public UserDTO getUserByAccount(@Parameter(in = ParameterIn.PATH, description = "The id of bankAccount" ,required=true,schema=@Schema(implementation = Integer.class))
                                        @PathVariable int id);

    @Operation(summary = "Поиск аккаунта пользователя", description = "Возврат аккаунта пользователя", tags={ "findAccountByUser" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccountDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getAccountByUser",produces = { "application/json" })
    public BankAccountDTO getAccountByUser(Principal principal);


    @Operation(summary = "Создание карты", description = "cоздание карты для пользоваьеля", tags={ "createCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/createCard",produces = { "application/json" })
    public Object createCard(Principal principal);

    @Operation(summary = "Поиск карты пользоваьея", description = "возврат карты", tags={ "getCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getCardByUser",produces = { "application/json" })
    public CardDTO getCardByUser(Principal principal);

    @Operation(summary = "Получение пользователя по карте", description = "Получение пользователя по номеру карте", tags={ "getUserByCard" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByCard/{cardNumber}",produces = { "application/json" })
    public UserDTO getUserByCard(@Parameter(in = ParameterIn.PATH, description = "Номер карты" ,required=true,schema=@Schema(implementation = Integer.class))
                                     @PathVariable int cardNumber);

    @Operation(summary = "Положить денгт на счет", description = "положить определенную суму на свой счет", tags={ "putMoney" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/putMoney",produces = { "application/json" })
    public Object putMoneyOnCard(@Parameter(in = ParameterIn.QUERY, description = "Сумма денег" ,required=true,schema=@Schema(implementation = Integer.class))
                                     @RequestParam int sum,
                                        Principal principal);


    @Operation(summary = "Перевод денег с одной карты на другую", description = "По номеру карты ", tags={ "transfer" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PutMapping(value = "/transferMoney/{id}",produces = { "application/json" })
    public Object transferMoney(@Parameter( description = "The amount of money request", required=true, schema=@Schema(implementation = SendMoneyRequest.class))@Valid @RequestBody SendMoneyRequest request,
                                @Parameter(in = ParameterIn.PATH, description = "Номер карты" ,required=true,schema=@Schema(implementation = Integer.class))@PathVariable("id") int cardNumber,
                                BindingResult bindingResult,
                                Principal principal);


    @Operation(summary = "Получение истории карты пользователя", description = "история", tags={ "history" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HistoryItemDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getHistory",produces = { "application/json" })
    public List<HistoryItemDTO> getHistory(Principal principal);

    @Operation(summary = "создание депозита", description = "создание депозита пользователем", tags={ "createDeposite" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @PostMapping(value = "/createDeposite",produces = { "application/json" })
    public Object createDeposite(@Parameter(in = ParameterIn.QUERY, description = "сумма" ,required=true,schema=@Schema(implementation = Integer.class))@RequestParam int sum,
                                 Principal principal);

    @Operation(summary = "Поиск пользователя по депозитк", description = "по id", tags={ "getUserByDep" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
    @GetMapping(value = "/getUserByDeposite/{id}",produces = { "application/json" })
    public UserDTO getUserByDeposite(@Parameter(in = ParameterIn.PATH, description = "id депозита" ,required=true,schema=@Schema(implementation = Integer.class))@PathVariable int id);

    @PutMapping ("/approveDeposite")
    public Object approveDeposite(int activationCode,Principal principal);
}
