package com.nandani.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nandani.gateway.domain.User;
import com.nandani.gateway.dto.RegisterRequest;
import com.nandani.gateway.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/public")
@RestController
public class PublicController {

    private final UserService userService;

@PostMapping("/register")
ResponseEntity<User> registerUser(@RequestBody RegisterRequest request){

User user =	   userService.registerRequest(request);

return   ResponseEntity.ok(user);


}

}
