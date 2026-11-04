package com.nandani.gateway.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nandani.gateway.domain.User;
import com.nandani.gateway.dto.RegisterRequest;
import com.nandani.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	   public User registerRequest(RegisterRequest req) {

		  return userRepository
		         .save(User.builder()
				   .username(req.getUsername())
				   .password(passwordEncoder.encode(req.getPassword()))
				   .branchId(req.getBranchId())
				   .employeeCode(req.getEmployeeCode())
				   .enabled(true)
				   .build());



	   }




}
