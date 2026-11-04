package com.nandani.gateway.event;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginFailureEventListener {


	@EventListener
	public void loginSuccess(AuthenticationSuccessEvent event) {

		log.info("Login Failed User {}",event.getAuthentication().getName());



	}





}
