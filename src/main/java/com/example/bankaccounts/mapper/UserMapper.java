package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.PersonalInfoDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.BankAccount;
import com.example.bankaccounts.entity.PersonalInfo;
import com.example.bankaccounts.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BankAccountMapper bankAccountMapper;
    private final PersonalInfoMapper personalInfoMapper;

    public User toUserFull(UserDTO userDTO){
        BankAccount bankAccount = bankAccountMapper.toBankAccount(userDTO.getBankAccountDTO());
        PersonalInfo personalInfo = personalInfoMapper.toPersonalInfo(userDTO.getPersonalInfoDTO());
        User user = User.builder().
                id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .emails(userDTO.getEmails())
                .phones(userDTO.getPhones())
                .personalInfo(personalInfo)
                .bankAccount(bankAccount)
                .build();
        return user;
    }

  //  public Us
    public UserDTO toUserDTOFull(User user){
        BankAccountDTO bankAccountDTO = bankAccountMapper.toBankAccountDTO(user.getBankAccount());
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toPersonalInfoDTO(user.getPersonalInfo());
        UserDTO userDTO = UserDTO.builder().
                id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .emails(user.getEmails())
                .phones(user.getPhones())
                .personalInfoDTO(personalInfoDTO)
                .bankAccountDTO(bankAccountDTO)
                .build();
        return userDTO;
    }

    public List<User> toUserList(List<UserDTO> dtoList){
        List<User> list = new ArrayList<>();
        for (UserDTO dto:dtoList){
            list.add(toUserFull(dto));
        }
        return list;
    }

    public List<UserDTO> toUserDTOList(List<User> list){
        List<UserDTO> listDTO = new ArrayList<>();
        for (User user:list){
            listDTO.add(toUserDTOFull(user));
        }
        return listDTO;
    }

    public UserDTO toUserDTO(User user){
        PersonalInfoDTO personalInfoDTO = personalInfoMapper.toPersonalInfoDTO(user.getPersonalInfo());
        UserDTO userDTO = UserDTO.builder().
                id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .emails(user.getEmails())
                .phones(user.getPhones())
                .personalInfoDTO(personalInfoDTO)
                .build();
        return userDTO;
    }

}
