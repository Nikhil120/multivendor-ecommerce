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

import com.faciotech.facio.dto.BusinessDTO;
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
	public ResponseEntity<BusinessDTO> getBusinessDetails() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		BusinessDTO businessDTO = businessService.getBusinessDetails(email);
		return new ResponseEntity<BusinessDTO>(businessDTO, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<BusinessDTO> updateBusiness(@PathVariable("id") String businessId,
			@RequestBody BusinessDTO businessDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		BusinessDTO updatedBusiness = businessService.updateBusiness(email, businessDTO);
		return new ResponseEntity<BusinessDTO>(updatedBusiness, HttpStatus.OK);
	}
}
