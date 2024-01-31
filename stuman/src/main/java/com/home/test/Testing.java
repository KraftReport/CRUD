package com.home.test;

import java.sql.SQLException;

import com.home.connection.GetConnection;
import com.home.dao.StudentDao;
import com.home.dto.Student;

public class Testing {

	public static void main(String[] args) throws SQLException {
		var student = new Student( 1,"name1", "dob", "address", "qualification", "email");
		var student1 = new Student( 2,"name1", "dob", "address", "qualification", "email");
		var dao = new StudentDao(GetConnection.getConnection());
//		System.out.println(dao.insert(student1));
		System.out.println(dao.update(student));
		System.out.println(dao.searchById(1));
		System.out.println(dao.getAllStudent());
		System.out.println(dao.delete(1));
	}
}
