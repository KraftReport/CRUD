package com.ace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.entity.User;
import com.ace.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void registerUser(User user) {
		userRepository.registerUser(user);
	}

	@Override
	public boolean loginUser(User user) {
		var found =  userRepository.getByEmail(user.getEmail());
		if(found.get(0).getPassword().equals(user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean forgotPassword(User user) {
		var found = userRepository.getByEmail(user.getEmail());
		if(found.get(0).getName().equals(user.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);
	}

	@Override
	public void deleteUser(int id) {
		userRepository.deleteUser(id);
	}

	@Override
	public List<User> getAllUsers() { 
		return userRepository.getAllUsers();
	}

	@Override
	public List<User> getById(int id) { 
		return userRepository.getById(id);
	}

	@Override
	public List<User> getByEmail(String email) { 
		return userRepository.getByEmail(email);
	}

	@Override
	public List<User> getByName(String name) { 
		return userRepository.getByName(name);
	}
}
