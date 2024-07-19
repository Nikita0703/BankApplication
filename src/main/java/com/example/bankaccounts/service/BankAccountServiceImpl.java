package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.BankAccount;
import com.example.bankaccounts.entity.User;
import com.example.bankaccounts.mapper.BankAccountMapper;
import com.example.bankaccounts.repository.BankAccountRepository;
import com.example.bankaccounts.repository.UserRepository;
import com.example.bankaccounts.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountMapper bankAccountMapper;
    private final UserServiceImpl userService;

    public BankAccountServiceImpl(@Lazy UserServiceImpl userService,
                                  BankAccountRepository bankAccountRepository,
                                  UserRepository userRepository,
                                  BankAccountMapper bankAccountMapper){
        this.userService = userService;
        this.bankAccountMapper = bankAccountMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;

    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void increaseBalance() {
        List<User> users = userRepository.findAll();
        List<Integer> initialDeposite = new ArrayList<>(users.size());
        double interestRate = 0.05;
        double maxInterestRate = 2.07;

        for (User user : users) {
            initialDeposite.add((int) user.getBankAccount().getCard().getBalance());
        }

       int i = 0;
        for (User user : users) {
           // if (user.getBankAccount().getCard().getBalance() * interestRate < initialDeposite.get(i) * maxInterestRate) {
             //   user.getBankAccount().getCard().setBalance((int) ((int) user.getBankAccount().getCard().getBalance() * interestRate));
            //    userService.createUser(user);
            //    log.info("Увеличен на 5%");
            }
           // i++;
       // }

   }

   public UserDTO getUserByAccount(int id){
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);;
        BankAccountDTO bankAccountDTO = bankAccountMapper.toBankAccountDTOFull(bankAccount);
        return bankAccountDTO.getUser();
   }

   public BankAccountDTO getAccountByUser(Principal principal){
        UserDTO userDTO = userService.getUserDTOByPrincipal(principal);
        return userDTO.getBankAccountDTO();
   }

}
