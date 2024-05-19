package com.example.bankaccounts.service;

import com.example.bankaccounts.entity.Email;
import com.example.bankaccounts.entity.Phone;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.exception.LastEmailException;
import com.example.bankaccounts.exception.LastPhoneException;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.exception.PhoneExistsException;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.repository.EmailsRepository;
import com.example.bankaccounts.repository.PhonesRepository;
import com.example.bankaccounts.repository.UserRepository;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void createUser(User user){
        userRepository.save(user);
    }

    public void addTelephoneNumber(String string){
        User user = userRepository.findUserById(1L).orElse(null);
        user.getPhones().add(string);
        userRepository.save(user);
    }

    public void addEmail(String string){
        User user = userRepository.findUserById(1L).orElse(null);
        user.getEmails().add(string);
        userRepository.save(user);
    }

    public void changePhone(String phone){
        User user = userRepository.findUserById(2L).orElse(null);
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

    public void changeEmail(String email){
        User user = userRepository.findUserById(2L).orElse(null);
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

    public void deletePhone(String phone){
        User user = userRepository.findUserById(1L).orElse(null);
        List<String> phones1 = user.getPhones();
        if (phones1.size() ==  1) {
            throw new LastPhoneException("It is yours last phone you cuoldnt remove it");
        }else {
            user.getPhones().removeIf(phone1 -> phone1.equals(phone));
            userRepository.save(user);
        }
    }

    public void deleteEmail(String email){
        User user = userRepository.findUserById(1L).orElse(null);
        List<String> phones1 = user.getEmails();
        if (phones1.size() ==  1) {
            throw new LastEmailException("It is yours last phone you cuoldnt remove it");
        }else {
            user.getEmails().removeIf(email1 -> email1.equals(email));
            userRepository.save(user);
        }
    }

    public List<User> filterByBirthday(LocalDateTime birthday){
        List<User> users= userRepository.findAll();
        return users.stream()
                .filter(user -> user.getBirthday().isAfter(birthday))
                .collect(Collectors.toList());
    }

    public User findByPhone(String phone){
        List<User> users= userRepository.findAll();
        return users.stream()
                .filter(user -> user.getPhones().contains(phone))
                .findFirst()
                .orElse(null);
    }

    public User findByEmail(String email){
        List<User> users= userRepository.findAll();
        return users.stream()
                .filter(user -> user.getEmails().contains(email))
                .findFirst()
                .orElse(null);
    }

    public List<User> findByFio(String fio){
        List<User> users= userRepository.findAll();
        return users.stream()
                .filter(user -> user.getFio().startsWith(fio))
                .collect(Collectors.toList());
    }

    @Transactional
    public synchronized void transferMoney(SendMoneyRequest request, int for_id){
        int amount = request.getAmount();
        User sender = userRepository.findUserById(1L).orElse(null);
        User reciever = userRepository.findUserById(2L).orElse(null);
        if (sender.getBankAccount().getSchet() - amount < 0 ) {
            throw new NotEnoughMoneyException("It is not enough money in tours account");
        }else {
            sender.getBankAccount().setSchet(sender.getBankAccount().getSchet() - amount);
            reciever.getBankAccount().setSchet(reciever.getBankAccount().getSchet() + amount);
        }

    }

}
