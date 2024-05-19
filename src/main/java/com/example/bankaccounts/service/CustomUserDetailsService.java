package com.example.bankaccounts.service;

import com.example.bankaccounts.repository.UserRepository;
import com.example.bankaccounts.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.bankaccounts.entity.User;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public static final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Поиск по username");
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return build(user);
    }

    public User loadUserById(Long id) {
        log.info("Поиск по id");
        return userRepository.findUserById(id).orElse(null);
    }

    public static User build(User user) {

        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword()
                );
    }
}
