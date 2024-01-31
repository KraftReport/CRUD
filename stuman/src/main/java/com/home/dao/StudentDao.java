package com.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.home.dto.Student;

public class StudentDao {

	private Connection con;

	public StudentDao(Connection con) {
		this.con = con;
	}

	public boolean insert(Student student) throws SQLException {
		boolean b = false;
		String sql = "insert into stutbl (name,dob,address,qualification,email) values (?,?,?,?,?)";

		try {
			var ps = con.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getDob());
			ps.setString(3, student.getAddress());
			ps.setString(4, student.getQualification());
			ps.setString(5, student.getEmail());
			int i = ps.executeUpdate();
			if (i == 1) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;

	}

	public boolean update(Student student) {
		boolean b = false;
		String sql = "update stutbl set name=?,dob=?,address=?,qualification=?,email=? where id=?";
		try {
			var ps = con.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getDob());
			ps.setString(3, student.getAddress());
			ps.setString(4, student.getQualification());
			ps.setString(5, student.getEmail());
			ps.setInt(6, student.getId());
			var i = ps.executeUpdate();
			if (i == 1) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public Student searchById(int id) {
		Student student = null;
		String sql = "select * from stutbl where id = ? ";
		try {
			var ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				student = new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setAddress(rs.getString("address"));
				student.setQualification(rs.getString("qualification"));
				student.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}

	public List<Student> getAllStudent() {
		Student student = null;
		List<Student> stuList = new ArrayList<>();
		String sql = "select * from stutbl";

		try {
			var ps = con.prepareStatement(sql);
			var rs = ps.executeQuery();
			while (rs.next()) {
				student = new Student();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setAddress(rs.getString("address"));
				student.setQualification(rs.getString("qualification"));
				student.setEmail(rs.getString("email"));
				stuList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stuList;
	}
	
	public boolean delete(int id) {
		boolean b = false; 
		String sql = "delete from stutbl where id = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			int i = ps.executeUpdate();
			if(i == 1) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}
}
