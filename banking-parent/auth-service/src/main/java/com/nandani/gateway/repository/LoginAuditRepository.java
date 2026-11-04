package com.nandani.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nandani.gateway.audit.LoginAudit;

public interface LoginAuditRepository extends JpaRepository<LoginAudit, Long> {
	            Optional<LoginAudit> findByUsername(String username);

}
