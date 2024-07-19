package com.example.bankaccounts.entity;

import com.example.bankaccounts.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table( name = "users8",
        indexes = {@Index(name = "usernameIndex5",columnList = "username")})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password",unique = true)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role8",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_emails12",
           // indexes = { @Index(columnList = "list_index3") },
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> emails = new HashSet<>();;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_phones12",
           // indexes = { @Index(columnList = "list_index4") },
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> phones = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personalInfo_id")
    private PersonalInfo personalInfo;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * SECURITY
     */

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
