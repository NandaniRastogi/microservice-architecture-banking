package com.nandani.gateway.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.nandani.gateway.audit.LoginAudit;
import com.nandani.gateway.repository.LoginAuditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {

	private final LoginAuditRepository loginAuditRepository;

	public void LogSuccess(String username,String ip) {

		loginAuditRepository.save(
				LoginAudit
				.builder()
				.username(username)
				.ipAddress(ip)
				.success(true)
				.loginTime(LocalDateTime.now())
				.reason("Login Success")
				.build()
				);

	}

	public void LogFailure(String username,String ip,String reason) {

		loginAuditRepository.save(
				LoginAudit
				.builder()
				.username(username)
				.success(false)
				.ipAddress(ip)
				.loginTime(LocalDateTime.now())
				.reason(reason)
				.build()

				);

	}



}
