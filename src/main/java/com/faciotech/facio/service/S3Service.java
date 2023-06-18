package com.faciotech.facio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

	@Value("${aws.region}")
	private String awsRegion;

	private final S3Client s3Client;

	public void upload(String bucketName, String key, byte[] file) {

		PutObjectRequest objectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();

		s3Client.putObject(objectRequest, RequestBody.fromBytes(file));
	}

	public String getObjectURL(String bucketName, String key) {
		String url = "https://%s.s3.%s.amazonaws.com/%s";

		return String.format(url, bucketName, awsRegion, key);
	}
}
