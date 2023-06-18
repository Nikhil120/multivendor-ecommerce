package com.faciotech.facio.service;

import com.faciotech.facio.dto.AuthenticationRequestDTO;
import com.faciotech.facio.dto.AuthenticationResponseDTO;
import com.faciotech.facio.dto.UserDTO;
import com.faciotech.facio.entity.Token;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.enums.Role;
import com.faciotech.facio.enums.TokenType;
import com.faciotech.facio.repository.TokenRepository;
import com.faciotech.facio.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final EmailService emailService;

	private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

	public String register(UserDTO request, String siteURL) throws UnsupportedEncodingException, MessagingException {
		byte[] array = new byte[7];
		new Random().nextBytes(array);
		String randomCode = generateVeificationCode();

		if (!validateEmail(request.getEmail())) {
			return "Invalid Email";
		}
		if (!validatePassword(request.getPassword())) {
			return "Password should contains atleast 8 character. It should contains atleast one uppercase character, one lowercase character, one digit and one special character";
		}

		User user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
				.verificationCode(randomCode).isVerified(false).build();

		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

		if (existingUser.isEmpty()) {
			repository.save(user);
			sendVerificationEmail(user, siteURL);
			return "User Successfully Registed. Please verify email address by clicking the link send to your mail.";
		}

		return "User already exists.";
	}

	public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request, String siteURL)
			throws UnsupportedEncodingException, MessagingException {
		User user = repository.findByEmail(request.getEmail()).orElseThrow();

		if (!user.getIsVerified()) {
			sendVerificationEmail(user, siteURL);
			return null;
		}

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponseDTO.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
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
				var authResponse = AuthenticationResponseDTO.builder().accessToken(accessToken)
						.refreshToken(refreshToken).build();
				new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
			}
		}
	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "My company name.";

		content = content.replace("[[name]]", user.getFullName());
		String verifyURL = siteURL + "/api/v1/auth/verify?email=" + user.getEmail() + "&code=" + user.getVerificationCode();

		content = content.replace("[[URL]]", verifyURL);

		emailService.sendMail(toAddress, subject, content);

	}

	public boolean verify(String email, String verificationCode) {
		Optional<User> user = userRepository.findByEmail(email);

		if (user.isEmpty() || user.get().getVerificationCode() == null
				|| !user.get().getVerificationCode().equals(verificationCode)) {
			return false;
		} else {
			User user1 = user.get();
			user1.setVerificationCode(null);
			user1.setIsVerified(true);
			userRepository.save(user1);

			return true;
		}

	}

	public boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
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
