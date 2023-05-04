package com.faciotech.facio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.repository.UserRepository;
import com.faciotech.facio.service.BusinessService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/test")
public class DemoController {
	@Autowired
	BusinessService businessService;

	@Autowired
	UserRepository userRepository;


	@GetMapping("/data")
	public ResponseEntity<Business> sayHello(HttpServletRequest request, HttpServletResponse response) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Business business = businessService.getBusinessDetails(email);
		
		return new ResponseEntity<Business>(business, HttpStatus.OK);
	}

}
