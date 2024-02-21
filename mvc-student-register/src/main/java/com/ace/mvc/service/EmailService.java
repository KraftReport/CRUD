package com.ace.mvc.service;

import java.util.Random;

public interface EmailService {

    public String generateCode();

    public void sendEmail(String email,String code);
}
