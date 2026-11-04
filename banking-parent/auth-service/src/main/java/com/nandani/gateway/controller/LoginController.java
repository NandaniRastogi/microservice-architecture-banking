package com.nandani.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandani.gateway.dto.LoginRequestDto;
import com.nandani.gateway.service.AuditService;
import com.nandani.gateway.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
	private final AuditService auditService;
	private final AuthenticationService authen;

	@PostMapping("login-success")
	public void loginSuccess(String username,HttpServletRequest request) {
		auditService.LogSuccess(username, request.getRemoteAddr());
	}

	@PostMapping("login-failure")
	public void loginFailure(String username,HttpServletRequest request,String message) {
		auditService.LogFailure(username, request.getRemoteAddr(),message);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?>  loginMethode(@RequestBody LoginRequestDto login){
		
		
		authen.loginService(login);
		
		
		
		
		
		return ResponseEntity.ok("Login Pending");
	}




}
