package com.ace.boot.repo;

import com.ace.boot.entity.Role;
import com.ace.boot.entity.User;
import com.ace.boot.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepoTest {
    @Autowired
    UserRepository userRepository;
    private User one;
    private User two;

    @BeforeEach
    public void create(){
        one = new User();
        one.setName("msp");
        one.setPassword("ok");
        one.setEmail("okkkk@gmail.com");
        one.setRole(Role.ADMIN);
        two = new User();
        two.setName("msp");
        two.setPassword("ok");
        two.setEmail("okkkk@gmail.com");
        two.setRole(Role.ADMIN);
    }
    @Test
    void saveUser(){
        var saveUser = userRepository.save(one);
        assertNotNull(saveUser.getId());
        userRepository.delete(one);
    }
}
