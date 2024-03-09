package com.ace.repository;

import com.ace.entity.User;

import java.util.List;

public interface UserRepository {
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public User findById(int id);
    public User findByEmail(String email);
    public List<User> getAllUser();
}
