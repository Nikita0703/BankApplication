package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.dto.HistoryItemDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.*;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public interface BankAccountService {
    public UserDTO getUserByAccount(int id);
    public BankAccountDTO getAccountByUser(Principal principal);
    public void createCard(Principal principal);
    public CardDTO getCardByUser(Principal principal);
    public UserDTO getUserByCard(int cardNumber);
    public void putMoneyOnCard(int sum,Principal principal);
    public  void transferMoney(SendMoneyRequest request, int cardNumber, Principal principal);
    public List<HistoryItemDTO> getHistory(Principal principal);
    public void createDeposite(int sum,Principal principal);
    public UserDTO getUserByDeposite(int id);
    public void increaseBalance();
    public MessageResponse approveDeposite(int activationCode, Principal principal);
    public void sendEmailMessage(String userEmail,int activationCode);
}
