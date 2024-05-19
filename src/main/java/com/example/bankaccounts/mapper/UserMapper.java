package com.example.bankaccounts.mapper;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.BankAccount;
import com.example.bankaccounts.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final BankAccountMapper bankAccountMapper;

    public User toUser(UserDTO userDTO){
        BankAccount bankAccount = bankAccountMapper.toBankAccount(userDTO.getBankAccount());
        User user = User.builder().
                id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .emails(userDTO.getEmails())
                .phones(userDTO.getPhones())
                .bankAccount(bankAccount)
                .birthday(userDTO.getBirthday())
                .fio(userDTO.getFio())
                .build();
        return user;
    }

    public UserDTO toUserDTO(User user){
        BankAccountDTO bankAccount = bankAccountMapper.toBankAccountDTO(user.getBankAccount());
        UserDTO userDTO = UserDTO.builder().
                id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .emails(user.getEmails())
                .phones(user.getPhones())
                .bankAccount(bankAccount)
                .birthday(user.getBirthday())
                .fio(user.getFio())
                .build();
        return userDTO;
    }

    public List<User> toUserList(List<UserDTO> dtoList){
        List<User> list = new ArrayList<>();
        for (UserDTO dto:dtoList){
            list.add(toUser(dto));
        }
        return list;
    }

    public List<UserDTO> toUserDTOList(List<User> list){
        List<UserDTO> listDTO = new ArrayList<>();
        for (User user:list){
            listDTO.add(toUserDTO(user));
        }
        return listDTO;
    }

}
