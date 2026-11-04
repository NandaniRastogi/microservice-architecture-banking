package com.nandani.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Permission extends JpaRepository<Permission, Long> {

}
