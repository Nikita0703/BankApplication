package com.example.bankaccounts.service;

import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.repository.BankAccountRepository;
import com.example.bankaccounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final UserRepository userRepository;
    private final UserService userService;

    @Scheduled(fixedRate = 60000)
    public void increaseBalance() {
        List<User> users = userRepository.findAll();
        List<Integer> initialDeposite = new ArrayList<>(users.size());
        double interestRate = 0.05;
        double maxInterestRate = 2.07;

        for (User user : users) {
            initialDeposite.add(user.getBankAccount().getSchet());
        }

        int i = 0;
        for (User user : users) {
            if (user.getBankAccount().getSchet() * interestRate < initialDeposite.get(i) * maxInterestRate) {
                user.getBankAccount().setSchet((int) ((int) user.getBankAccount().getSchet() * interestRate));
                userService.createUser(user);
            }
            i++;
        }
    }
}
