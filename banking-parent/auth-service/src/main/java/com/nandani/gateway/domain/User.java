package com.nandani.gateway.domain;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique =true ,nullable=false)
	private String username;
	@Column(nullable=false)
	private String password;
	private boolean enabled = true;

	private String branchId;
	private String employeeCode;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name ="user_roles" ,joinColumns = @JoinColumn(name= "users_id"),inverseJoinColumns = @JoinColumn(name="roles_id"))
	private Set<Role> role;

	private String email;

	private String phone;
	
	private boolean accountNonLocked = true;
	
	private int failedAttempts;
	
	private LocalDateTime lockTime;

}
