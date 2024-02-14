package com.ace.mvc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.ace.mvc.service.CourseService;
import com.ace.mvc.service.StudentService;
import com.ace.mvc.service.UserService;
import com.ace.mvc.serviceImpl.CourseServiceImpl;
import com.ace.mvc.serviceImpl.StudentServiceImpl;
import com.ace.mvc.serviceImpl.UserServiceImpl;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import jakarta.servlet.annotation.MultipartConfig;

@Configuration
@MultipartConfig
@ComponentScan(basePackages = "com.ace.mvc")
public class RootConfig {
	
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/mvc_student");
		ds.setUsername("root");
		ds.setPassword("admin");
		return ds;
	}
	
	@Bean
	public UserService getUserService() {
		return new UserServiceImpl(getDataSource());
	}
	
	@Bean
	public StudentService getStudentService() {
		return new StudentServiceImpl(getDataSource());
	}
	
	@Bean
	public CourseService getCourseService() {
		return new CourseServiceImpl(getDataSource());
	}
	
	
	@Bean
	public MultipartResolver mtrs() {
		return new StandardServletMultipartResolver();
	}
}
