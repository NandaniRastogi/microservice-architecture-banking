package com.nandani.gateway.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nandani.gateway.domain.User;
import com.nandani.gateway.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{

	   private final UserRepository userRepository;




	       User getUser(String username) {

	    	  return  userRepository.findByUsername(username)
	    	   .orElseThrow(()-> new UsernameNotFoundException("User Not Found Exception"));

	       }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userdb = getUser(username);
		return new org.springframework.security.core.userdetails.User(
		        userdb.getUsername(),
		        userdb.getPassword(),
		        userdb.getRole()
		            .stream()
		            .flatMap(role ->
		                Stream.concat(
		                    Stream.of(
		                        new SimpleGrantedAuthority(role.getRole())
		                    ),
		                    role.getPermissions()
		                        .stream()
		                        .map(permission ->
		                            new SimpleGrantedAuthority(permission.getName())
		                        )
		                )
		            )
		            .collect(Collectors.toList())
		);
	}

}
