package com.ace.boot.controller;

import com.ace.boot.repository.UserRepository;
import com.ace.boot.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestOne {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRepository userRepository;

    @Test
    void test1() throws Exception {
        mockMvc.perform(post("/member/updateUser"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/member/userUpdate"));
    }
}
