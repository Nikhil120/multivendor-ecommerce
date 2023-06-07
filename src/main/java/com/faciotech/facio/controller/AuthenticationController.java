package com.faciotech.facio.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.dto.AuthenticationRequestDTO;
import com.faciotech.facio.dto.AuthenticationResponseDTO;
import com.faciotech.facio.dto.UserDTO;
import com.faciotech.facio.service.AuthenticationService;
import com.faciotech.facio.service.BusinessService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final BusinessService businessService;
	private String siteURL = "http://localhost:8080/api/v1/auth";

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDTO request)
			throws UnsupportedEncodingException, MessagingException {
		String responseMessage = authenticationService.register(request, siteURL);

		return ResponseEntity.ok(responseMessage);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequestDTO request) {
		ResponseEntity<Object> responseEntity;
		try {
			AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(request, siteURL);
			if (authenticationResponseDTO == null) {
				responseEntity = ResponseEntity.status(HttpStatus.OK)
						.body("User not verified. Verification email send.");
			} else {
				responseEntity = ResponseEntity.ok(authenticationResponseDTO);
			}
		} catch (Exception e) {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid email or password");
		}

		return responseEntity;
	}

	@GetMapping("/verify")
	public String verifyUser(@Param("email") String email, @Param("code") String code) {
		if (authenticationService.verify(email, code)) {
			businessService.addDefaultBusiness(email);
			return "verify_success";
		} else {
			return "verify_fail";
		}
	}

	@PostMapping("/refresh-token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		authenticationService.refreshToken(request, response);
	}
}
