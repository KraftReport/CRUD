package com.ace.mvc.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ace.mvc.model.User;

@Service
public interface UserService {
	
	
	
	public boolean addUser(User user) throws SQLException;
	
	public User searchByEmail(String email);
	
	public List<User> findByName(String name);
	
	public User findById(int id);
	
	public List<User> getAllUser();
	
	public List<User> findPasswordByEmail(String email);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(int id);

}
