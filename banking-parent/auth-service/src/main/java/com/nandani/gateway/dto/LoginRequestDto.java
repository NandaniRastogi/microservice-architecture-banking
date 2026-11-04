package com.nandani.gateway.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@Data
public class LoginRequestDto {
	
	
	private String username;
	private String password;
	private HttpServletRequest request;

}
