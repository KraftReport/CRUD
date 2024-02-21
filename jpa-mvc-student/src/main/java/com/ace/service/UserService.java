package com.ace.service;

import java.util.List;

import com.ace.entity.User;

public interface UserService {

    public void registerUser(User user);
    
    public boolean loginUser(User user);
    
    public boolean forgotPassword(User user);

    public void updateUser(User user);

    public void deleteUser(int id);

    public List<User> getAllUsers();
    
    public List<User> getById(int id);
    
    public List<User> getByEmail(String email);
    
    public List<User> getByName(String name);
}
