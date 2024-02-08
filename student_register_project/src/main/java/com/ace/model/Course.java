package com.ace.model;

public class Course {

	private String id;
	private String name;
 

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public Course(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Course(String id) {
		super();
		this.id = id;
	}
 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}

}
