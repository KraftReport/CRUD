package com.ace.mvc.model;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Student {


	private String id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private MultipartFile photo;
	private String[] course;
	
	
}
