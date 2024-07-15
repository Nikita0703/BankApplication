package com.example.bankaccounts.entity;

import com.example.bankaccounts.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table( name = "users2",
        indexes = {@Index(name = "usernameIndex",columnList = "username")})
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
    @CollectionTable(name = "user_role2",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_emails2",
            joinColumns = @JoinColumn(name = "user_id"))
    private List<String> emails = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_phones2",
            joinColumns = @JoinColumn(name = "user_id"))
    private List<String> phones = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private String fio;

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
