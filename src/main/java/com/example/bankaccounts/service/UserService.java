package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.Email;
import com.example.bankaccounts.entity.Phone;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.exception.LastEmailException;
import com.example.bankaccounts.exception.LastPhoneException;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.exception.PhoneExistsException;
import com.example.bankaccounts.mapper.BankAccountMapper;
import com.example.bankaccounts.mapper.UserMapper;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.repository.EmailsRepository;
import com.example.bankaccounts.repository.PhonesRepository;
import com.example.bankaccounts.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PhonesRepository phonesRepository;
    private final EmailsRepository emailsRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BankAccountMapper bankAccountMapper;
    private final UserMapper userMapper;

    public void createUserFromDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmails(userDTO.getEmails());
        user.setPhones(userDTO.getPhones());
        user.setBankAccount(bankAccountMapper.toBankAccount(userDTO.getBankAccount()));
        user.setBirthday(userDTO.getBirthday());
        user.setFio(userDTO.getFio());
        userRepository.save(user);
    }

    public void createUser(User user){
        userRepository.save(user);
    }
    public void addTelephoneNumber(String string, Principal principal){
        User user = getUserByPrincipal(principal);
        user.getPhones().add(string);
        userRepository.save(user);
    }

    public void addEmail(String string,Principal principal){
        User user = getUserByPrincipal(principal);
        user.getEmails().add(string);
        userRepository.save(user);
    }

    public void changePhone(String phone, Principal principal){
        User user = getUserByPrincipal(principal);
        List<Phone> phones1 = phonesRepository.findAll();
        List<String> phones = new ArrayList<>();
        for(Phone phonetemp:phones1){
            phones.add(phonetemp.getPhones());
        }
        user.getPhones().clear();
        if (phones.contains(phone)) {
            throw new PhoneExistsException("Phone with value already exists in the list.");
        }else {
            user.getPhones().add(phone);
            userRepository.save(user);
        }

    }

    public void changeEmail(String email, Principal principal){
        User user = getUserByPrincipal(principal);
        List<Email> phones1 = emailsRepository.findAll();
        user.getEmails().clear();
        List<String> phones = new ArrayList<>();
        for(Email phonetemp:phones1){
            phones.add(phonetemp.getEmails());
        }
        if (phones.contains(email)) {
            throw new PhoneExistsException("Phone with value already exists in the list.");
        }else {
            user.getEmails().add(email);
            userRepository.save(user);
        }
    }

    public void deletePhone(String phone, Principal principal){
        User user = getUserByPrincipal(principal);
        List<String> phones1 = user.getPhones();
        if (phones1.size() ==  1) {
            throw new LastPhoneException("It is yours last phone you cuoldnt remove it");
        }else {
            user.getPhones().removeIf(phone1 -> phone1.equals(phone));
            userRepository.save(user);
        }
    }

    public void deleteEmail(String email, Principal principal){
        User user = getUserByPrincipal(principal);
        List<String> phones1 = user.getEmails();
        if (phones1.size() ==  1) {
            throw new LastEmailException("It is yours last phone you cuoldnt remove it");
        }else {
            user.getEmails().removeIf(email1 -> email1.equals(email));
            userRepository.save(user);
        }
    }

    public List<UserDTO> filterByBirthday(LocalDateTime birthday){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOList(users.stream()
                .filter(user -> user.getBirthday().isAfter(birthday))
                .collect(Collectors.toList()));
    }

    public UserDTO findByPhone(String phone){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTO(users.stream()
                .filter(user -> user.getPhones().contains(phone))
                .findFirst()
                .orElse(null));
    }

    public UserDTO findByEmail(String email){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTO(users.stream()
                .filter(user -> user.getEmails().contains(email))
                .findFirst()
                .orElse(null));
    }

    public List<UserDTO> findByFio(String fio){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOList(users.stream()
                .filter(user -> user.getFio().startsWith(fio))
                .collect(Collectors.toList()));
    }

    @Transactional
    public synchronized void transferMoney(SendMoneyRequest request, Long for_id, Principal principal){
        int amount = request.getAmount();
        User sender = getUserByPrincipal(principal);
        User reciever = userRepository.findUserById(for_id).orElse(null);
        if (sender.getBankAccount().getSchet() - amount < 0 ) {
            throw new NotEnoughMoneyException("It is not enough money in tours account");
        }else {
            sender.getBankAccount().setSchet(sender.getBankAccount().getSchet() - amount);
            reciever.getBankAccount().setSchet(reciever.getBankAccount().getSchet() + amount);
        }

    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

}
