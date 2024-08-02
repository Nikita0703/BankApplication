package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.BankAccount;
import com.example.bankaccounts.entity.Email;
import com.example.bankaccounts.entity.Phone;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.entity.enums.ERole;
import com.example.bankaccounts.exception.LastEmailException;
import com.example.bankaccounts.exception.LastPhoneException;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.exception.PhoneExistsException;
import com.example.bankaccounts.mapper.BankAccountMapper;
import com.example.bankaccounts.mapper.PersonalInfoMapper;
import com.example.bankaccounts.mapper.UserMapper;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.repository.BankAccountRepository;
import com.example.bankaccounts.repository.EmailsRepository;
import com.example.bankaccounts.repository.PhonesRepository;
import com.example.bankaccounts.repository.UserRepository;

import com.example.bankaccounts.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PhonesRepository phonesRepository;
    private final EmailsRepository emailsRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BankAccountMapper bankAccountMapper;
    private final UserMapper userMapper;
    private final PersonalInfoMapper personalInfoMapper;
    private final BankAccountServiceImpl bankAccountService;

    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

    @Override
    @Transactional
    public void createUserFromDTO(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //user.setRoles(Collections.singleton(ERole.ROLE_USER));
        user.getRoles().add(ERole.ROLE_USER);
        user.setEmails(userDTO.getEmails());
        user.setPhones(userDTO.getPhones());
        BankAccount bankAccount = new BankAccount();
        Random rand = new Random();
        int ID = rand.nextInt(10000) + 1;
        bankAccount.setIdenticalNumber(ID);
        bankAccount.setCreationDate(LocalDateTime.now());
        user.setBankAccount(bankAccount);
        user.setPersonalInfo(personalInfoMapper.toPersonalInfo(userDTO.getPersonalInfoDTO()));
        bankAccount.setUser(user);
        bankAccountRepository.save(bankAccount);
        log.info("добавлен успешно");
        userRepository.save(user);
    }

    @Override
    public void createUser(User user){
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addTelephoneNumber(String string, Principal principal){
        User user = getUserByPrincipal(principal);
        user.getPhones().add(string);
        log.info("добавлен успешно");
       // userRepository.save(userMapper.toUser(user));
        userRepository.save(user);
    }

    @Override
    public void addEmail(String string,Principal principal){
        User user = getUserByPrincipal(principal);
        user.getEmails().add(string);
        log.info("добавлен успешно");
        userRepository.save(user);
    }

    @Override
    public void changePhone(String phone, Principal principal){
        User user = getUserByPrincipal(principal);
        List<Phone> phones1 = phonesRepository.findAll();
        List<String> phones = new ArrayList<>();
        for(Phone phonetemp:phones1){
            phones.add(phonetemp.getPhones());
        }
        user.getPhones().clear();
        if (phones.contains(phone)) {
            log.warn("Phone with value already exists in the list");
            throw new PhoneExistsException("Phone with value already exists in the list.");
        }else {
            log.info("изменен успешно");
            user.getPhones().add(phone);
            userRepository.save(user);
        }

    }

    @Override
    public void changeEmail(String email, Principal principal){
        User user = getUserByPrincipal(principal);
        List<Email> phones1 = emailsRepository.findAll();
        user.getEmails().clear();
        List<String> phones = new ArrayList<>();
        for(Email phonetemp:phones1){
            phones.add(phonetemp.getEmails());
        }
        if (phones.contains(email)) {
            log.warn("Email with value already exists in the list.");
            throw new PhoneExistsException("Email with value already exists in the list.");
        }else {
            log.info("изменен успешно");
            user.getEmails().add(email);
            userRepository.save(user);
        }
    }

    @Override
    public void deletePhone(String phone, Principal principal){
        User user = getUserByPrincipal(principal);
        Set<String> phones1 = user.getPhones();
        if (phones1.size() ==  1) {
            log.warn("It is yours last phone you cuoldnt remove it");
            throw new LastPhoneException("It is yours last phone you cuoldnt remove it");
        }else {
            log.info("Удален успешно");
            user.getPhones().removeIf(phone1 -> phone1.equals(phone));
            userRepository.save(user);
        }
    }

    @Override
    public void deleteEmail(String email, Principal principal){
        User user = getUserByPrincipal(principal);
        Set<String> phones1 = user.getEmails();
        if (phones1.size() ==  1) {
            log.warn("It is yours last email you cuoldnt remove it");
            throw new LastEmailException("It is yours last email you cuoldnt remove it");
        }else {
            log.info("Удален успешно");
            user.getEmails().removeIf(email1 -> email1.equals(email));
            userRepository.save(user);
        }
    }

    @Override
    public List<UserDTO> filterByBirthday(LocalDateTime birthday){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOList(users.stream()
                .filter(user -> user.getPersonalInfo().getBirthday().isAfter(birthday))
                .collect(Collectors.toList()));
    }

    @Override
    public UserDTO findByPhone(String phone){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOFull(users.stream()
                .filter(user -> user.getPhones().contains(phone))
                .findFirst()
                .orElse(null));
    }

    @Override
    public UserDTO findByEmail(String email){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOFull(users.stream()
                .filter(user -> user.getEmails().contains(email))
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<UserDTO> findByFio(String fio){
        List<User> users= userRepository.findAll();
        return userMapper.toUserDTOList(users.stream()
                .filter(user -> user.getPersonalInfo().getFio().startsWith(fio))
                .collect(Collectors.toList()));
    }

   // @Override
    @Transactional
    public synchronized void transferMoney(SendMoneyRequest request, Long for_id, Principal principal){
        int amount = request.getAmount();
        User sender = getUserByPrincipal(principal);
        User reciever = userRepository.findUserById(for_id).orElse(null);
        //UserDTO reciever = userMapper.toUserDTO(reciever1);
        if (sender.getBankAccount().getCard().getBalance() - amount < 0 ) {
            throw new NotEnoughMoneyException("It is not enough money in tours account");
        }else {
            sender.getBankAccount().getCard().setBalance(sender.getBankAccount().getCard().getBalance() - amount);
            reciever.getBankAccount().getCard().setBalance(reciever.getBankAccount().getCard().getBalance() + amount);
        }

    }

    public UserDTO getUserDTOByPrincipal(Principal principal) {
        String username = principal.getName();
        User user =  userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
        return userMapper.toUserDTOFull(user);
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        User user =  userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
        return user;
    }

    @Override
    public User getUserByUsername(String username){
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        return optionalUser.get();
    }
}
