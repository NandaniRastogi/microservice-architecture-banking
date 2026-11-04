package com.nandani.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nandani.gateway.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findByClientId(String clientId);

}
