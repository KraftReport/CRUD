package com.ace.mvc.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.ace.mvc.config.RootConfig;
import com.ace.mvc.config.ServletConfig;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
public class MvcApp extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {ServletConfig.class};
	}
	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) { 
		registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
