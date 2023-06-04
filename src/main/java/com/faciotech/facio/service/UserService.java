package com.faciotech.facio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

	private final UserRepository userRepository;

	public void addUser(String email, String roleName) {
		log.info("Adding role {} to user {}", roleName, email);
//		Optional<User> user = userRepository.findByEmail(email);
//		Optional<Role> role = roleRepository.findByName(roleName);

	}
}
