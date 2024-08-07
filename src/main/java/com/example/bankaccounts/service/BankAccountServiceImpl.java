package com.example.bankaccounts.service;

import com.example.bankaccounts.dto.BankAccountDTO;
import com.example.bankaccounts.dto.CardDTO;
import com.example.bankaccounts.dto.HistoryItemDTO;
import com.example.bankaccounts.dto.UserDTO;
import com.example.bankaccounts.entity.*;
import com.example.bankaccounts.exception.CardNotActiveException;
import com.example.bankaccounts.exception.CardNotExistsExseption;
import com.example.bankaccounts.exception.DepositeNotActiveException;
import com.example.bankaccounts.exception.NotEnoughMoneyException;
import com.example.bankaccounts.mapper.BankAccountMapper;
import com.example.bankaccounts.mapper.CardMapper;
import com.example.bankaccounts.mapper.HistoryItemMapper;
import com.example.bankaccounts.payload.request.SendMoneyRequest;
import com.example.bankaccounts.payload.response.MessageResponse;
import com.example.bankaccounts.repository.BankAccountRepository;
import com.example.bankaccounts.repository.CardRepository;
import com.example.bankaccounts.repository.DepositeRepository;
import com.example.bankaccounts.repository.UserRepository;
import com.example.bankaccounts.security.JWTTokenProvider;
import com.example.bankaccounts.utils.ApplicationConstants;
import com.example.bankaccounts.utils.RandomCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);
    @Value("${mail.username}")
    private String emailFrom;

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BankAccountMapper bankAccountMapper;
    private final UserServiceImpl userService;
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final HistoryItemMapper historyItemMapper;
    private final DepositeRepository depositeRepository;

    private final MailSender mailSender;

    public BankAccountServiceImpl(@Lazy UserServiceImpl userService,
                                  BankAccountRepository bankAccountRepository,
                                  UserRepository userRepository,
                                  BankAccountMapper bankAccountMapper,
                                  CardRepository cardRepository,
                                  CardMapper cardMapper,
                                  HistoryItemMapper historyItemMapper,
                                  DepositeRepository depositeRepository,
                                  MailSender mailSender){
        this.userService = userService;
        this.bankAccountMapper = bankAccountMapper;
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.historyItemMapper = historyItemMapper;
        this.depositeRepository = depositeRepository;
        this.mailSender = mailSender;
    }

    @Override
   public UserDTO getUserByAccount(int id){
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);;
        BankAccountDTO bankAccountDTO = bankAccountMapper.toFullBankAccountDTO(bankAccount);
        return bankAccountDTO.getUser();
   }

   @Override
   public BankAccountDTO getAccountByUser(Principal principal){
        UserDTO userDTO = userService.getUserDTOByPrincipal(principal);
        return userDTO.getBankAccountDTO();
   }

    @Override
   public void createCard(Principal principal){
        User user = userService.getUserByPrincipal(principal);
        Card card = new Card();
        Random rand = new Random();
        card.setCardNumber(rand.nextInt(10000) + 1);
        card.setCvv(rand.nextInt(1000) + 1);
        String holderName = user.getPersonalInfo().getFirstName()+
                            user.getPersonalInfo().getLastName();
        card.setCardHolderName(holderName);
        card.setActive(true);
        card.setBankAccount(user.getBankAccount());
        user.getBankAccount().setCard(card);
        cardRepository.save(card);
        bankAccountRepository.save(user.getBankAccount());
   }

    @Override
    public CardDTO getCardByUser(Principal principal){
        UserDTO userDTO = userService.getUserDTOByPrincipal(principal);
        return userDTO.getBankAccountDTO().getCard();
    }

    @Override
    public UserDTO getUserByCard(int cardNumber){
        Optional<Card> cardOptional = cardRepository.findByCardNumber(cardNumber);
        Card card = cardOptional.orElseThrow(() -> new CardNotExistsExseption(ApplicationConstants.CardNotExists));
        UserDTO user = bankAccountMapper.toFullBankAccountDTO(card.getBankAccount()).getUser();
        return user;
    }

    @Override
    public void putMoneyOnCard(int sum,Principal principal){
        User user = userService.getUserByPrincipal(principal);
        if(user.getBankAccount().getCard()!=null && user.getBankAccount().getCard().getActive()) {
            int currentBalance = (int) user.getBankAccount().getCard().getBalance();
            user.getBankAccount().getCard().setBalance(currentBalance + sum);
            cardRepository.save(user.getBankAccount().getCard());
        }else {
            throw new CardNotActiveException("you dont have card or it is does not active");
        }
    }

    @Override
    @Transactional
    public synchronized void transferMoney(SendMoneyRequest request, int cardNumber, Principal principal){
        int amount = request.getAmount();
        User sender = userService.getUserByPrincipal(principal);
        Optional<Card> cardOptional = cardRepository.findByCardNumber(cardNumber);
        Card card = cardOptional.orElseThrow(() -> new CardNotExistsExseption(ApplicationConstants.CardNotExists));
        if(sender.getBankAccount().getCard()==null){
            throw new CardNotActiveException("you dont have card or it is does not active");
        }
        User reciever = card.getBankAccount().getUser();

        if(sender.getBankAccount().getCard().getActive()) {
            if (sender.getBankAccount().getCard().getBalance() - amount < 0) {
                throw new NotEnoughMoneyException("It is not enough money in tours account");
            } else {
                sender.getBankAccount().getCard().setBalance(sender.getBankAccount().getCard().getBalance() - amount);
                reciever.getBankAccount().getCard().setBalance(reciever.getBankAccount().getCard().getBalance() + amount);
            }
            cardRepository.save(sender.getBankAccount().getCard());
            cardRepository.save(reciever.getBankAccount().getCard());

            HistoryItem senderHistoryItem = new HistoryItem();
            senderHistoryItem.setSum(amount);
            senderHistoryItem.setCreationDate(LocalDateTime.now());
            senderHistoryItem.setDescription("send money on the cardNumber" + reciever.getBankAccount().getCard().getCardNumber());
            sender.getBankAccount().getHistoryItems().add(senderHistoryItem);
            bankAccountRepository.save(sender.getBankAccount());

            HistoryItem recieverHistoryItem = new HistoryItem();
            recieverHistoryItem.setSum(amount);
            recieverHistoryItem.setCreationDate(LocalDateTime.now());
            recieverHistoryItem.setDescription("recieved money from the cardNumber" + sender.getBankAccount().getCard().getCardNumber());
            reciever.getBankAccount().getHistoryItems().add(senderHistoryItem);
            bankAccountRepository.save(reciever.getBankAccount());
        } else {
            throw new CardNotActiveException("you dont have card or it is does not active");}

    }

    @Override
    public List<HistoryItemDTO>getHistory(Principal principal){
        User user = userService.getUserByPrincipal(principal);
        return  historyItemMapper.toHistoryItemDTOList(user.getBankAccount().getHistoryItems());
    }

    @Override
    public void createDeposite(int sum,Principal principal){
        User user = userService.getUserByPrincipal(principal);
        if (user.getBankAccount().getCard().getBalance() - sum < 0 ) {
            throw new NotEnoughMoneyException("It is not enough money in tours account");
        }else {
            user.getBankAccount().getCard().setBalance(user.getBankAccount().getCard().getBalance() - sum );
            Deposite deposite = new Deposite();
            deposite.setSum(sum);
            deposite.setTerm(12);
            deposite.setInterestRate(5);
            deposite.setActive(false);
            int activationCode = RandomCodeGenerator.generateCode();
            deposite.setActivationCode(activationCode);
            deposite.setBankAccount(user.getBankAccount());
            depositeRepository.save(deposite);
            bankAccountRepository.save(user.getBankAccount());
            for (String email: user.getEmails()) {
              //  sendEmailMessage(email,1111);
            }
        }
    }

    @Override
    public UserDTO getUserByDeposite(int id){
        Optional<Deposite> depositeOptional = depositeRepository.findById(id);
        Deposite deposite = depositeOptional.orElseThrow(() -> new DepositeNotActiveException(ApplicationConstants.DepositeNotActive));
        UserDTO user = bankAccountMapper.toFullBankAccountDTO(deposite.getBankAccount()).getUser();
        return user;
    }

    @Override
    @Scheduled(fixedRate = 600000)
    public void increaseBalance() {
        List<User> users = userRepository.findAll();

        int i = 0;
        for (User user : users) {
            if(user.getBankAccount().getDeposite()!=null && user.getBankAccount().getDeposite().getActive()) {
                if (user.getBankAccount().getDeposite().getTerm() > 0) {
                    Deposite deposite =  user.getBankAccount().getDeposite();
                    deposite.setSum((int) (deposite.getSum() +
                            deposite.getSum() * 0.01 *
                                    deposite.getInterestRate()));
                    user.getBankAccount().getDeposite().setTerm(user.getBankAccount().getDeposite().getTerm() - 1);
                    depositeRepository.save(user.getBankAccount().getDeposite());
                }
                if (user.getBankAccount().getDeposite().getTerm() == 0) {
                    Card card = user.getBankAccount().getCard();
                    card.setBalance(card.getBalance() +
                            user.getBankAccount().getDeposite().getSum());
                    cardRepository.save(user.getBankAccount().getCard());
                    user.getBankAccount().getDeposite().setActive(false);
                    depositeRepository.save(user.getBankAccount().getDeposite());
                }
            }
        }

    }

    @Override
    public MessageResponse approveDeposite(int activationCode,Principal principal){
        User user = userService.getUserByPrincipal(principal);
        if(activationCode == user.getBankAccount().getDeposite().getActivationCode()){
            user.getBankAccount().getDeposite().setActive(true);
            return new MessageResponse("deposite is active");
        }else {
            return new MessageResponse("Not valid activation code");
        }
    }

    @Override
    public void sendEmailMessage(String userEmail,int activationCode) {
        String messageText = String.format(ApplicationConstants.EmailMessage+"%d", activationCode);
        SimpleMailMessage messageToActivateUser = new SimpleMailMessage();
        messageToActivateUser.setTo(userEmail);
        messageToActivateUser.setFrom(emailFrom);
        messageToActivateUser.setSubject(ApplicationConstants.Sublect);
        messageToActivateUser.setText(messageText);

        mailSender.send(messageToActivateUser);
    }

    @Override
    @Scheduled(fixedRate = 600000)
    public void CheckCardIsActive() {
        List<User> users = userRepository.findAll();

        int i = 0;
        for (User user : users) {
            if(user.getBankAccount().getCard()!=null){
                if(user.getBankAccount().getCard().getActive() ) {
                    if (Objects.equals(user.getBankAccount().getCard().getExpirationDate(), LocalDateTime.now())) {
                        user.getBankAccount().getCard().setActive(false);
                        cardRepository.save(user.getBankAccount().getCard());
                    }
                }
            }
        }

    }
}
