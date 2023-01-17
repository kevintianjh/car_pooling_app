package com.accenture.carpooling.service;

import java.util.Date;
import java.util.Random; 
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component; 

@Component
public class AuthenticationHandler {  
	public static final long SESSION_EXPIRY = 3600000;
	
	private String secret = AuthenticationHandler.generateRandomSecret();
	
	public String generateSignature(String role, String username, String expiry) {  
		return BCrypt.hashpw(role + username + expiry + this.secret, BCrypt.gensalt()); 
	}
	
	private boolean verifySignature(String role, String username, String expiry, String signature) {
		try {
			return BCrypt.checkpw(role + username + expiry + this.secret, signature);
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean verifyRequest(String role, String username, String expiry, String signature) {
		 
		if(role == null || username == null || expiry == null || signature == null) {
			return false;
		}
		
		if(verifySignature(role, username, expiry, signature)) { 
			long expiryTime = Long.parseLong(expiry);
			long currentTime = (new Date()).getTime(); 
			
			if(expiryTime < currentTime) {
				return false;
			}

			return true;
		}
		
		return false;
	} 
	
	public String generateUpdatedExpiry() {
		long currentTime = (new Date()).getTime(); 
		return String.valueOf(currentTime+AuthenticationHandler.SESSION_EXPIRY);
	}
	
	public static String generateRandomSecret() {
		StringBuilder secret = new StringBuilder(); 
		Random rand = new Random();
		
		for(int count = 0; count < 20; count++) { 
			int choice = rand.nextInt(1, 5);
			
			switch(choice) {
				case 1:
					secret.append((char)rand.nextInt(97, 123));
					break;
				case 2: 
					secret.append((char)rand.nextInt(65, 91)); 
					break;
				case 3:
					secret.append((char)rand.nextInt(48, 58)); 
					break;
				case 4:  
					secret.append((char)rand.nextInt(33, 48));
					break;
			}
		}
		
		return secret.toString();
	}
}
