package com.example.bankaccounts.dto;

import com.example.bankaccounts.entity.BankAccount;
import lombok.*;
import org.hibernate.annotations.Check;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepositeDTO {

    private int sum;

    private int term;

    private int interestRate;

    private int activationCode;

    private boolean isActive;

}
