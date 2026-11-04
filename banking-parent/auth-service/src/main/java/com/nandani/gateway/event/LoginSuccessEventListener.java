package com.nandani.gateway.event;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginSuccessEventListener {


	@EventListener
	   public void loginFailure(AuthenticationFailureBadCredentialsEvent event) {

		log.warn("Login Failed user{}",event.getAuthentication().getName());

	   }

}
