package com.faciotech.facio.service;

import com.faciotech.facio.entity.Token;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.enums.AppRole;
import com.faciotech.facio.enums.TokenType;
import com.faciotech.facio.repository.TokenRepository;
import com.faciotech.facio.repository.UserRepository;
import com.faciotech.facio.util.AuthenticationRequest;
import com.faciotech.facio.util.AuthenticationResponse;
import com.faciotech.facio.util.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final JavaMailSender mailSender;

	public boolean register(RegisterRequest request, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String randomCode = generateVeificationCode();

		User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).appRole(AppRole.USER)
				.verificationCode(randomCode).isVerified(false).build();

		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		if (existingUser.isEmpty()) {
			repository.save(user);
			sendVerificationEmail(user, siteURL);
			return true;
		}

		return false;
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false)
				.build();
		tokenRepository.save(token);
	}

	private void revokeAllUserTokens(User user) {
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByEmail(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				revokeAllUserTokens(user);
				saveUserToken(user, accessToken);
				var authResponse = AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
						.build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "nikhilbindal666@gmail.com";
		String senderName = "Your company name";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Your company name.";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("[[name]]", user.getFullName());
		String verifyURL = siteURL + "/verify?email=" + user.getEmail() + "&code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);

	}

	public boolean verify(String email, String verificationCode) {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isEmpty() || !user.get().getVerificationCode().equals(verificationCode)) {
			return false;
		} else {
			User user1 = user.get();
			user1.setVerificationCode(null);
			user1.setIsVerified(true);
			userRepository.save(user1);

			return true;
		}

	}

	protected String generateVeificationCode() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder code = new StringBuilder();
		Random rnd = new Random();
		while (code.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			code.append(SALTCHARS.charAt(index));
		}
		return code.toString();
	}
}
