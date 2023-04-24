package com.faciotech.facio.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.service.AuthenticationService;
import com.faciotech.facio.util.AuthenticationRequest;
import com.faciotech.facio.util.AuthenticationResponse;
import com.faciotech.facio.util.RegisterRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request)
			throws UnsupportedEncodingException, MessagingException {
		boolean status = authenticationService.register(request, "http://localhost:8080/api/v1/auth");

		if (status) {
			return ResponseEntity.ok(
					"User Successfully Registed. Please verify email address by clicking the link send to your mail.");
		}
		return ResponseEntity.ok("User Already Exist.");
	}

	@PostMapping("/login")
	public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
		ResponseEntity<Object> responseEntity;
		try {
			responseEntity = ResponseEntity.ok(authenticationService.authenticate(request));
		} catch (Exception e) {
			responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or email not verified");
		}

		return responseEntity;
	}

	@GetMapping("/verify")
	public String verifyUser(@Param("email") String email, @Param("code") String code) {
		if (authenticationService.verify(email, code)) {
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
