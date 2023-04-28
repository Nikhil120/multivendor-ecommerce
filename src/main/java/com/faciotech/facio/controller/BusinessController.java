package com.faciotech.facio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.entity.Business;

//import com.faciotech.facio.business.entity.Business;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {

	public ResponseEntity<Business> updateBusiness(@RequestBody Business business) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return null;
	}
	
	 @GetMapping
	  public ResponseEntity<String> sayHello() {
	    return ResponseEntity.ok("Hello from secured business endpoint");
	  }

}
