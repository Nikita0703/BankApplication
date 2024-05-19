package com.example.bankaccounts.service;

import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.repository.BankAccountRepository;
import com.example.bankaccounts.repository.UserRepository;
import com.example.bankaccounts.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

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
                log.info("Увеличен на 5%");
            }
            i++;
        }

    }
}
