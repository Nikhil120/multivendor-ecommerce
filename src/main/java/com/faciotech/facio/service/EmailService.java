package com.faciotech.facio.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;
	String from = "nikhilbindal666@gmail.com";
	String senderName = "My Company Name";

	@RequiredArgsConstructor
	private class EmailSender extends Thread {

		private final String to;
		private final String subject;
		private final String content;

		@Override
		public void run() {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			try {
				helper.setFrom(from, senderName);
				helper.setTo(to);
				helper.setSubject(subject);
				helper.setText(content, true);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			mailSender.send(message);
		}
	}

	public void sendMail(String to, String subject, String content) {
		EmailSender emailSender = new EmailSender(to, subject, content);
		emailSender.start();
	}
}
