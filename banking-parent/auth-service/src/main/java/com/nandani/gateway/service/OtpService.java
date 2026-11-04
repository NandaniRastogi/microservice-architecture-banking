package com.nandani.gateway.service;

import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {
	
	private final StringRedisTemplate redisTemplate;
	private static final String OTP_KEY = "otp";
	private static final String META_KEY = "meta-key";
	private final SecureRandom secureRandom = new SecureRandom();
	private final PasswordEncoder passwordEncoder;
	
	
	public String generateOtp(String username,String ip,String deviceId) {
		

	    int otpI = secureRandom.nextInt(1000000);

	    String otp = String.format("%06d", otpI);
	    
	    
	    redisTemplate.opsForValue().set(OTP_KEY+username, 
	    		otp,
	    		Duration.ofMinutes(30));
	    
	    String meta = ip + "|" + deviceId;
	    
	    redisTemplate.opsForValue().set(META_KEY+username, meta,
	    		Duration.ofMinutes(30));
	    
	
		return otp;
	}
	
	
	public boolean verifyOtp(String userName, String otp, String ip, String deviceId) {
		String storedOtp = redisTemplate.opsForValue().get(OTP_KEY+userName);
		String meta = redisTemplate.opsForValue().get(META_KEY+userName);
		if(storedOtp== null || meta== null) {
			return false;
		}
		String []parts = meta.split("\\|");
		String storedIp = parts[0];
		String storedDeviceId = parts[1];
		boolean valid = storedOtp.equals(otp) && storedIp.equals(ip) && storedDeviceId.equals(deviceId);
		redisTemplate.delete(OTP_KEY+userName);
		redisTemplate.delete(META_KEY+userName);
		return valid;
	}

}
