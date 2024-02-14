package com.ace.mvc.serviceImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//import com.ace.mvc.connection.ConnectionCaller;
import com.ace.mvc.model.User;
//import com.ace.mvc.model.User.Role;
import com.ace.mvc.service.UserService;

public class UserServiceImpl implements UserService {

	JdbcTemplate jdbcTemplate;
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UserServiceImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public boolean addUser(User user) throws SQLException {
		var sql = "insert into user values (?,?,?,?,?)";
		var i = jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword(), user.getEmail(),
				user.getRole());
		return i == 1;
	}

	@Override
	public List<User> getAllUser() {
		var sql = "select * from user";
		List<User> userList = jdbcTemplate.query(sql,mapRow());
		return userList;

	}

	@Override
	public boolean updateUser(User user) {
		var sql = "update user set name = :name,password = :password,email =:email,role = :role where id = :id";
		var map = new MapSqlParameterSource().addValue("name", user.getName());
		map.addValue("password", user.getPassword());
		map.addValue("email", user.getEmail());
		map.addValue("role", user.getRole());
		map.addValue("id", user.getId());
		var i = namedParameterJdbcTemplate.update(sql, map);
		return i==1;
	}

	@Override
	public boolean deleteUser(int id) {
		var sql = "delete from user where id = :id";
		var map = new MapSqlParameterSource().addValue("id", id);
		var i = namedParameterJdbcTemplate.update(sql, map);
		System.out.println(i);
		return i==1;
	}

	@Override
	public List<User> findByName(String name) {
		var sql = "select * from user where name = :name";
		var map = new MapSqlParameterSource().addValue("name", name);
		var found = namedParameterJdbcTemplate.query(sql,map,mapRow());
		return found;
	}

	@Override
	public User findById(int id) {
		var sql = "select * from user where id = :id";
		var map = new MapSqlParameterSource().addValue("id", id);
		var user = namedParameterJdbcTemplate.queryForObject(sql, map, mapRow());
		return user;
	}

	@Override
	public User searchByEmail(String email) {
		User user = null;
		var sql = "select * from user where email = :email";
		var map = new MapSqlParameterSource().addValue("email", email);
		var rs = namedParameterJdbcTemplate.query(sql, map, mapRow());
		if (!rs.isEmpty()) {
			user = rs.get(0);
		}
		return user;
	}

	private RowMapper<User> mapRow() {
		return new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				return user;
			}
		};
	}

	@Override
	public List<User> findPasswordByEmail(String email) {
		var sql = "select * from user where email = :email";
		var map = new MapSqlParameterSource().addValue("email",email);
		return namedParameterJdbcTemplate.query(sql, map,mapRow());
	}
 

}
