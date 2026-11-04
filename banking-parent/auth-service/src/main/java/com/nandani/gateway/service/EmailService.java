package com.nandani.gateway.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	       
	     private final JavaMailSender mailSender;

	
	            public void sendOtp(String toEmail,String otp) {
	            	
	            	SimpleMailMessage message = new SimpleMailMessage();
	            	
	            	message.setTo(toEmail);
	            	message.setSubject("Your Verification Otp");
	            	message.setText(  "Your OTP is: " + otp +
	                        "\nThis OTP is valid for 30 minutes.");
	            	
	            	
	            	
	            	mailSender.send(message);
	            }
}
