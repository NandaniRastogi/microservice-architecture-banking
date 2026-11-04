package com.nandani.gateway.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class PhoneOtpService {
	

	@Value("${twilio.account-sid}")
	private String accountId;
	
	 @Value("${twilio.auth-token}")
	private String authToken;
	
	@Value("${twilio.phone-number}")
	private String fromNumber;
	
	
	@PostConstruct
	public void init() {
		Twilio.init(accountId,authToken);
	}
	public void sendOtp(String phone,String otp) {
		
		Message.creator(new PhoneNumber(phone),new PhoneNumber(fromNumber),
				"Hi My Baby Love You Baby "+ otp).create();	
	}

}
