package com.ace.repository;

import com.ace.entity.User;

import java.util.List;

public interface UserRepository {

    public boolean registerUser(User user);

    public boolean updateUser(User user);

    public boolean deleteUser(int id);

    public List<User> getAllUsers();

    public List<User> getById(int id);

    public List<User> getByEmail(String email);

    public List<User> getByName(String name);
}
