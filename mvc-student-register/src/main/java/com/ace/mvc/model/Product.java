package com.ace.mvc.model;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	private String name;
	private MultipartFile photo;
	@Override
	public String toString() {
		return "Product [name=" + name + ", photo=" + photo + "]";
	}
	
	
}
