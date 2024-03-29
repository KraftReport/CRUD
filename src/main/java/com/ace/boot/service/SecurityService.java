package com.ace.boot.service;

import com.ace.boot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    HttpSession httpSession;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findOneByEmail(email)
                .map(user-> User.withUsername(email)
                        .password(user.getPassword())
                        .authorities(AuthorityUtils.createAuthorityList(user.getRole().name()))
                        .build()).orElseThrow(()->new RuntimeException("user is not found"));
    }
}
