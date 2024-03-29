package com.ace.model;

public class Student {

	private String id;
	private String name;
	private String dob;
	private String gender;
	private String phone;
	private String education;
	private String attend;
	private byte[] photo;

	public Student() {
	}

	public Student(String id) {
		super();
		this.id = id;
	}

	public Student(String name, String dob, String gender, String attend) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.attend = attend;
	}

	public Student(String name, String dob, String gender, String phone, String education, String attend,
			byte[] photo) {
		super();
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.attend = attend;
		this.photo = photo;
	}

	public Student(String id, String name, String dob, String gender, String phone, String education, String attend,
			byte[] photo) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender = gender;
		this.phone = phone;
		this.education = education;
		this.attend = attend;
		this.photo = photo;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getAttend() {
		return attend;
	}

	public void setAttend(String attend) {
		this.attend = attend;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", phone=" + phone
				+ ", education=" + education + ", attend=" + attend + ", photo=" + photo + "]";
	}
	
	

}
