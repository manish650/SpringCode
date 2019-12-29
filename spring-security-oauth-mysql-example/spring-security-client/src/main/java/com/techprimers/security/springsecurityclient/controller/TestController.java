package com.techprimers.security.springsecurityclient.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class TestController {
	@GetMapping("/hello")
	public String helloMessage() {
		
		return "Hello Manish";
	}
	@GetMapping("/secure/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String hello() {
		System.err.println("test");
		return "Hello Manish"; 
	}
}
