package com.nandani.gateway.service;

import java.time.LocalDateTime;

import org.apache.commons.codec.language.ColognePhonetic;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nandani.gateway.domain.User;
import com.nandani.gateway.dto.LoginRequestDto;
import com.nandani.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	  private final UserRepository userRepository;
	  private final PasswordEncoder passwordEncoder;
	  private final EmailService emailService;
	  private final PhoneOtpService phoneOtpService;
	  private final OtpService optService;
	  
	
	
	   // Business Logic of Login Service 
	
	  public void loginService(LoginRequestDto login) {

		    User user = userRepository.findByUsername(login.getUsername())
		            .orElseThrow(() ->
		                new UsernameNotFoundException("Username Not Found")
		            );

		    // Step 1: Check whether lock time has expired
		    if (!user.isAccountNonLocked()) {

		        if (user.getLockTime() != null &&
		                LocalDateTime.now().isAfter(user.getLockTime())) {

		            user.setAccountNonLocked(true);
		            user.setFailedAttempts(0);
		            user.setLockTime(null);

		            userRepository.save(user);

		        } else {
		            throw new RuntimeException("Account is Locked. Try After 15 Minutes");
		        }
		    }

		    // Step 2: Check password
		    if (!passwordEncoder.matches(
		            login.getPassword(),
		            user.getPassword())) {

		        user.setFailedAttempts(user.getFailedAttempts() + 1);

		        // Step 3: Lock account after 5 failed attempts
		        if (user.getFailedAttempts() >= 5) {

		            user.setAccountNonLocked(false);
		            user.setLockTime(LocalDateTime.now().plusMinutes(15));

		        }

		        userRepository.save(user);

		        throw new RuntimeException("Invalid Password");
		    }

		    // Step 4: Reset attempts after successful password verification
		    user.setFailedAttempts(0);
		    user.setLockTime(null);
		    
		    
		    userRepository.save(user);
		    
	        // Retrieve device ID from a custom request header
	        String deviceId = login.getRequest().getHeader("X-Device-ID");
	        
	    
		    
		    String otp = optService.generateOtp
		    		(login.getUsername(), login.getRequest().getRemoteAddr(),deviceId);
		    
		    emailService.sendOtp(user.getEmail(),otp);
		    
		   phoneOtpService.sendOtp(user.getPhone(),otp);
		    
		    
		    
		    
		    

		   
		}
	

}
