package com.accenture.carpooling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	@Autowired private JavaMailSender javaMailSender;
	
	public void sendEmail(String toEmail, String subject, String message) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(toEmail);
		smm.setSubject(subject);
		smm.setText(message); 
		this.javaMailSender.send(smm);
	}
}
