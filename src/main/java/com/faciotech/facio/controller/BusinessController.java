package com.faciotech.facio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.entity.Business;
import com.faciotech.facio.service.BusinessService;

import jakarta.websocket.server.PathParam;

//import com.faciotech.facio.business.entity.Business;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
public class BusinessController {
	private final BusinessService businessService;

	@GetMapping
	public ResponseEntity<Business> getBusinessDetails() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Business business = businessService.getBusinessDetails(email);
		return new ResponseEntity<Business>(business, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Business> updateBusiness(@PathVariable("id") String businessId, @RequestBody Business business) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Business updatedBusiness = businessService.updateBusiness(email, business);
		return new ResponseEntity<Business>(updatedBusiness, HttpStatus.OK);
	}
}
