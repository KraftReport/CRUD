package com.ace.mvc.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	private String id;
	private String name;
	private String status;
	private List<Student> student;
}
