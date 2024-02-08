package com.ace.repository;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ace.connection.ConnectorCaller;
import com.ace.model.Course;
import com.ace.model.Student;

public class StudentService {

	private Connection connection;

	public StudentService(Connection connection) {
		this.connection = connection;
	}

	public boolean register(Student student) {
		var b = false;
		var sql = "insert into student values (?,?,?,?,?,?,?,?)"; 
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			ps.setString(2, student.getName());
			ps.setString(3, student.getDob());
			ps.setString(4, student.getGender());
			ps.setString(5, student.getPhone());
			ps.setString(6, student.getEducation());
			ps.setString(7, student.getAttend());
			ps.setBlob(8, new ByteArrayInputStream(student.getPhoto()));
			var executeSuccess = ps.executeUpdate();
			if (executeSuccess == 1  ) {
				System.out.println("student is registered successfully");
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;

	}
	
	public boolean insertRecord(Student student,String courseName) {
		var b = false;
		var sql = "insert into record values (?,?)";
		var courseService = new CourseService(ConnectorCaller.getConnection());
		var stuId = student.getId();
		var courseId = courseService.findIdByName(courseName);
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, stuId);
			ps.setString(2, courseId);
			var insertRecord = ps.executeUpdate();
			if(insertRecord == 1 ) {
				System.out.println("record is added successfully");
				b =true;
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return b;
		
	}
	
	public List<Student> findStudentWithCourseIdFromRecord(Course course) {
		var sql ="select * from record where course_id = ?";
		List<Student> stuList = new ArrayList<>();
		var id = course.getId();
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			var rs = ps.executeQuery();
			
			while(rs.next()) {
				var student = new Student();
				student.setId(rs.getString("student_id"));
				System.out.println(rs.getString("student_id"));
				var found = findByIdNotList(student);
				System.out.println(found.getName());
				stuList.add(found);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuList;
		
	}
	
	public List<Student> findByIdNameCourse(Student student){
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student where id=? and name=? and course like ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			ps.setString(2, student.getName());
			ps.setString(3, "%"+student.getAttend()+"%");
			var rs = ps.executeQuery();
			while(rs.next()) {
				var returnStudent = new Student();
				returnStudent.setId(rs.getString("id"));
				returnStudent.setName(rs.getString("name"));
				returnStudent.setDob(rs.getString("dob"));
				returnStudent.setGender(rs.getString("gender"));
				returnStudent.setPhone(rs.getString("phone"));
				returnStudent.setEducation(rs.getString("education"));
				returnStudent.setAttend(rs.getString("course"));
				returnStudent.setPhoto(rs.getBytes("photo"));
				studentList.add(returnStudent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;
	}
	
	public Student findByIdNameNotList(Student student) {
		var returnStudent = new Student();
		var sql = "select * from student where id = ? and name = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			ps.setString(2, student.getName());
			var rs = ps.executeQuery();
			while (rs.next()) {
				returnStudent.setId(rs.getString("id"));
				returnStudent.setName(rs.getString("name"));
				returnStudent.setDob(rs.getString("dob"));
				returnStudent.setGender(rs.getString("gender"));
				returnStudent.setPhone(rs.getString("phone"));
				returnStudent.setEducation(rs.getString("education"));
				returnStudent.setAttend(rs.getString("course"));
				returnStudent.setPhoto(rs.getBytes("photo")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStudent ;

	}
	
	public Student findByIdNotList(Student student) {
		var returnStudent = new Student();
		var sql = "select * from student where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId()); 
			var rs = ps.executeQuery();
			while (rs.next()) {
				returnStudent.setId(rs.getString("id"));
				returnStudent.setName(rs.getString("name"));
				returnStudent.setDob(rs.getString("dob"));
				returnStudent.setGender(rs.getString("gender"));
				returnStudent.setPhone(rs.getString("phone"));
				returnStudent.setEducation(rs.getString("education"));
				returnStudent.setAttend(rs.getString("course"));
				returnStudent.setPhoto(rs.getBytes("photo")); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnStudent ;

	}
	

	public List<Student> findByIdName(Student student) {
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student where id = ? and name = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			ps.setString(2, student.getName());
			var rs = ps.executeQuery();
			while (rs.next()) {
				var returnStudent = new Student();
				returnStudent.setId(rs.getString("id"));
				returnStudent.setName(rs.getString("name"));
				returnStudent.setDob(rs.getString("dob"));
				returnStudent.setGender(rs.getString("gender"));
				returnStudent.setPhone(rs.getString("phone"));
				returnStudent.setEducation(rs.getString("education"));
				returnStudent.setAttend(rs.getString("course"));
				returnStudent.setPhoto(rs.getBytes("photo"));
				studentList.add(returnStudent);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;

	}

	public List<Student> getAllStudent() {
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student";
		try {
			var ps = connection.prepareStatement(sql);
			var rs = ps.executeQuery();
			while (rs.next()) {
				var student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setPhone(rs.getString("phone"));
				student.setEducation(rs.getString("education"));
				student.setAttend(rs.getString("course"));
				student.setPhoto(rs.getBytes("photo"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studentList;

	}
	
	public List<Student> findByCourse(Student inputStudent) {
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student where course like ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1,"%"+inputStudent.getAttend()+"%" );
			var rs = ps.executeQuery();
			while (rs.next()) {
				var student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setPhone(rs.getString("phone"));
				student.setEducation(rs.getString("education"));
				student.setAttend(rs.getString("course"));
				student.setPhoto(rs.getBytes("photo"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;

	}
	
	public List<Student> findByName(Student inputStudent) {
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student where name = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, inputStudent.getName());
			var rs = ps.executeQuery();
			while (rs.next()) {
				var student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setPhone(rs.getString("phone"));
				student.setEducation(rs.getString("education"));
				student.setAttend(rs.getString("course"));
				student.setPhoto(rs.getBytes("photo"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;

	}

	public List<Student> findById(String id) {
		List<Student> studentList = new ArrayList<>();
		var sql = "select * from student where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				var student = new Student();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setDob(rs.getString("dob"));
				student.setGender(rs.getString("gender"));
				student.setPhone(rs.getString("phone"));
				student.setEducation(rs.getString("education"));
				student.setAttend(rs.getString("course"));
				student.setPhoto(rs.getBytes("photo"));
				studentList.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;

	}

	public boolean update(Student student) {
		var b = false;
		var sql = "update student set name = ?, dob =? ,gender = ? ,phone = ?, education = ?,course = ?,photo = ? where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getDob());
			ps.setString(3, student.getGender());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getEducation());
			ps.setString(6, student.getAttend());
			ps.setBlob(7,	new ByteArrayInputStream(student.getPhoto()));
			ps.setString(8, student.getId());
			var updateOk = ps.executeUpdate();
			if (updateOk == 1) {
				System.out.println("student is updated successfully");
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;

	}

	public boolean delete(Student student) {
		var b = false;
		var sql = "delete from student where id = ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			var deleteSuccess = ps.executeUpdate();
			if (deleteSuccess == 1) {
				System.out.println("student is deleted successfully");
				b = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;

	}
	
	public String getOneStringFromArray(String[] array) {
		var arrays = "";
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				arrays += (array[i]).concat(",");
			}

		}
		return arrays.replaceAll(",$", "");
	}
	
	public boolean validateCourse(Student returnStudent,String attend) {
		var  foundCourse = returnStudent.getAttend().toLowerCase();
		var ok = foundCourse.contains(attend.toLowerCase());
		return ok;
	}
	
	public boolean deleteRecord(Student student) {
		var b= false;
		var sql = "delete from record where student_id= ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, student.getId());
			var i = ps.executeUpdate();
			if(i == 1) {
				System.out.println("a record is deleted successfully");
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}
	
	public boolean deleteRecordWithCourseId(Course course) {
		var b= false;
		var sql = "delete from record where course_id= ?";
		try {
			var ps = connection.prepareStatement(sql);
			ps.setString(1, course.getId());
			var i = ps.executeUpdate();
			if(i == 1) {
				System.out.println("a record is deleted successfully");
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}


}
