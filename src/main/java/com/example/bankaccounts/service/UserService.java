package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.Email;
import com.example.bankaccounts.entity.Phone;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.exception.LastEmailException;
import com.example.bankaccounts.exception.LastPhoneException;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.exception.PhoneExistsException;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface UserService {
    public void createUserFromDTO(UserDTO userDTO);

    public void createUser(User user);
    public void addTelephoneNumber(String string, Principal principal);

    public void addEmail(String string,Principal principal);

    public void changePhone(String phone, Principal principal);

    public void changeEmail(String email, Principal principal);

    public void deletePhone(String phone, Principal principal);

    public void deleteEmail(String email, Principal principal);

    public List<UserDTO> filterByBirthday(LocalDateTime birthday);

    public UserDTO findByPhone(String phone);

    public UserDTO findByEmail(String email);

    public List<UserDTO> findByFio(String fio);

   // public void transferMoney(SendMoneyRequest request, Long for_id, Principal principal);

    public User getUserByPrincipal(Principal principal);

    public User getUserByUsername(String username);

}
