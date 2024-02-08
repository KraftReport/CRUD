package com.ace.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ace.model.Student;
import com.ace.model.User;

public class UserService {

	private Connection connection;

	public UserService(Connection connection) {
		this.connection = connection;
	}

	public boolean register(User user) {
		var b = false;
		var sql = "insert into user values (?,?,?,?,?)";
		try {
			var ps = connection.prepareStatement(sql); 
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getRole());
			int i = ps.executeUpdate();
			if (i == 1) {
				System.out.println("user registered successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	

	public List<User> searchByIdAndName(User user) {
		List<User> userList = new ArrayList<>();
		String sql = "select * from user where id = ? and name = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			var rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("search user by id and name is found");
				var returnUser = new User();
				returnUser.setId(rs.getString("id"));
				returnUser.setName(rs.getString("name"));
				returnUser.setEmail(rs.getString("email"));
				returnUser.setPassword(rs.getString("password"));
				returnUser.setRole(rs.getString("role"));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public List<User> searchById(String id) {
		List<User> userList = new ArrayList<>();
		var sql = "select * from user where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			var rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("user is found search by id");
				var returnUser = new User();
				returnUser.setId(rs.getString("id"));
				returnUser.setName(rs.getString("name"));
				returnUser.setEmail(rs.getString("email"));
				returnUser.setPassword(rs.getString("password"));
				returnUser.setRole(rs.getString("role"));
				userList.add(returnUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public List<User> searchByName(User student){
		List<User> userList = new ArrayList<>();
		var sql = "select * from user where name like ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1,"%"+student.getName()+"%");
			var rs = ps.executeQuery();
			while(rs.next()) {
				var user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	public User searchByEmailAndPassword(User user) {
		var returnUser = new User();
		String sql = "select * from user where email = ? and password = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			var rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("search user by id and name is found");
				returnUser.setId(rs.getString("id"));
				returnUser.setName(rs.getString("name"));
				returnUser.setEmail(rs.getString("email"));
				returnUser.setPassword(rs.getString("password"));
				returnUser.setRole(rs.getString("role"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnUser;
	}
	
	

	public List<User> getUserAllUser() {
		List<User> userList = new ArrayList<>();
		String sql = "select * from user";
		int num = 0;
		try {
			var ps = connection.prepareStatement(sql);
			var rs = ps.executeQuery();
			while (rs.next()) {
				var user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				userList.add(user);
				num++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("number of user found -> " + num);
		return userList;
	}

	public boolean update(User user) {
		var b = false;
		String sql = "update user set name = ?, email = ? ,password = ?,role = ? where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			ps.setString(5, user.getId());
			int i = ps.executeUpdate();
			if (i == 1) {
				System.out.println("user is updated successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean delete(User user) {
		var b = false;
		var sql = "delete from user where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, user.getId());
			var i = ps.executeUpdate();
			if (i == 1) {
				System.out.println("user is deleted successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;

	}
}
