package com.faciotech.facio.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.faciotech.facio.entity.User;
import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaService {

	@Value("${aws.bucket}")
	private String awsBucket;

	private final S3Service s3Service;
	private final UserRepository userRepository;

	private void isFileEmpty(MultipartFile file) {
		if (file.isEmpty()) {
			throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
		}
	}

	private void isImage(MultipartFile file) {
		if (!Arrays.asList("image/png", "image/jpeg", "image/jpg", "image/svg").contains(file.getContentType())) {
			throw new IllegalStateException("File must be an image");
		}
	}

	public String uploadFile(String email, MultipartFile file) {
		User user = userRepository.findByEmail(email).orElseThrow();
		byte[] bytes;
		String key = "";

		isFileEmpty(file);

		isImage(file);

		key = String.format("%s/%s%s", user.getBusiness().getId(), String.valueOf(UUID.randomUUID()), ".png");

		try {
			bytes = file.getBytes();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		s3Service.upload(awsBucket, key, bytes);

		return s3Service.getObjectURL(awsBucket, key);
	}
}
