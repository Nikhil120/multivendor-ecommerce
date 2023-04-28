package com.faciotech.facio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.faciotech.facio.business.entity.Business;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {

//	public ResponseEntity<Business> addBusiness(@RequestBody Business business) {
//		return null;
//	}
	
	 @GetMapping
	  public ResponseEntity<String> sayHello() {
	    return ResponseEntity.ok("Hello from secured business endpoint");
	  }

}
