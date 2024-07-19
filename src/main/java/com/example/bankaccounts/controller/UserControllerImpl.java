package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.UserDTO;
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

    @Override
    public ResponseEntity<Object> addPhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for adding" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                           Principal principal){
        userService.addTelephoneNumber(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone added successfully"));
    }

    @Override
    public ResponseEntity<Object> addEmail(@Parameter(in = ParameterIn.QUERY, description = "The phone for adding" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                           Principal principal){
        userService.addEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email added successfully"));
    }

    @Override
    public ResponseEntity<Object> ChangePhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for changing" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                              Principal principal){
        userService.changePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone changed successfully"));
    }

    @Override
    public ResponseEntity<Object> ChangeEmail(@Parameter(in = ParameterIn.QUERY, description = "The email for changing" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                              Principal principal){
        userService.changeEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email changed successfully"));
    }

    @Override
    public ResponseEntity<Object> DeletePhone(@Parameter(in = ParameterIn.QUERY, description = "The phone for deleting" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone,
                                              Principal principal){
        userService.deletePhone(phone,principal);
        return ResponseEntity.ok(new MessageResponse("Phone deleted successfully"));
    }

    @Override
    public ResponseEntity<Object> DeleteEmail(@Parameter(in = ParameterIn.QUERY, description = "The email for deleting" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email,
                                              Principal principal){
        userService.deleteEmail(email,principal);
        return ResponseEntity.ok(new MessageResponse("Email deleted successfully"));
    }

    @Override
    public ResponseEntity<Object> filterForBirthday(@Parameter(in = ParameterIn.QUERY, description = "The date for filter" ,required=true,schema=@Schema(implementation = LocalDateTime.class))@RequestParam("birthday") LocalDateTime birthday){
        List<UserDTO> list = userService.filterByBirthday(birthday);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findByTel(@Parameter(in = ParameterIn.QUERY, description = "The phone foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("phone")String phone){
        UserDTO userDTO =  userService.findByPhone(phone);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findByEmail(@Parameter(in = ParameterIn.QUERY, description = "The phone foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("email")String email){
        UserDTO userDTO = userService.findByEmail(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object>  findByFio(@Parameter(in = ParameterIn.QUERY, description = "The fio foe search" ,required=true,schema=@Schema(implementation = String.class))@RequestParam("fio")String fio){
        List<UserDTO> list = userService.findByFio(fio);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
    @Override
    public ResponseEntity<Object> transfer(@Parameter( description = "The amount of money request", required=true, schema=@Schema(implementation = SendMoneyRequest.class))@Valid @RequestBody SendMoneyRequest request,
                                           @Parameter(in = ParameterIn.PATH, description = "The id who will get money" ,required=true,schema=@Schema(implementation = Long.class))@PathVariable("id") Long id,
                                           BindingResult bindingResult,
                                           Principal principal)
    {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        userService.transferMoney(request,id,principal);
        return ResponseEntity.ok("Success Tranfer");
    }
    */
}
