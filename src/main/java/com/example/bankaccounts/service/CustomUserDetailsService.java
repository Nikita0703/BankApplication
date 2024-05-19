package com.example.bankaccounts.service;

import com.example.bankaccounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return build(user);
    }

    public User loadUserById(Long id) {
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
