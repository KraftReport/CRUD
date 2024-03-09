package com.ace.config;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;

@Configuration
@ComponentScan("com.ace.controller")
@EnableWebMvc
@MultipartConfig
public class ServletConfig implements WebMvcConfigurer {
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		var bean = new SpringResourceTemplateResolver();
		bean.setPrefix("/views/");
		bean.setSuffix(".html");
		bean.setCacheable(false);
		return bean;
	}
	
	@Bean
	public SpringTemplateEngine getTemplateEngine(SpringResourceTemplateResolver resolver) {
		var bean = new SpringTemplateEngine();
		bean.setTemplateResolver(resolver);
		return bean;
	}
	
	@Bean
	public ThymeleafViewResolver viewResolver(SpringTemplateEngine springTemplateEngine) {
		var bean = new ThymeleafViewResolver();
		bean.setTemplateEngine(springTemplateEngine);
		return bean;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/views/**").addResourceLocations("/views/");
	}

	protected void customizeRegistration(ServletRegistration.Dynamic registration) { 
		registration.setMultipartConfig(new MultipartConfigElement("/temp"));
	}

	@Bean
	MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}


}
