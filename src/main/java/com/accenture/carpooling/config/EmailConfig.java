package com.accenture.carpooling.config;

import java.util.Properties; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/* Author: Eugene, Kevin Tian
 * Purpose: To configure email system
 */
@Configuration
public class EmailConfig {
	
	@Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true"); 
        properties.setProperty("mail.smtp.starttls.enable", "true");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("tompang.application@gmail.com");
        javaMailSender.setPassword("sgiijrshomasfxuh");
        javaMailSender.setJavaMailProperties(properties); 
        
        return javaMailSender;
    }
}
