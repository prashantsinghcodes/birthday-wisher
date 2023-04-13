package com.pscodes.birthdaywisher.service;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pscodes.birthdaywisher.model.BirthdayEmailDetails;

import jakarta.mail.internet.MimeMessage;

@Service
public class BirthdayEmailServiceImpl implements BirthdayEmailService {

	private static Logger logger = LogManager.getLogger(BirthdayEmailServiceImpl.class);
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Value("${image.location}")
	private String imageLocation;
	
	@Override
	public void sendSimpleBirthdayEmail(BirthdayEmailDetails birthdayEmailDetails) {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(sender);
			simpleMailMessage.setTo(birthdayEmailDetails.getTo());
			simpleMailMessage.setText(birthdayEmailDetails.getBody());
			simpleMailMessage.setSubject(birthdayEmailDetails.getSubject());
			javaMailSender.send(simpleMailMessage);
			logger.info("Mail sent successfully!");
		} catch (Exception e) {
			logger.info("Exception occurred : {}", e.getLocalizedMessage());
		}
	}

	@Override
	public void sendBirthdayEmailWithAttachment(BirthdayEmailDetails birthdayEmailDetails) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(birthdayEmailDetails.getTo());
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setSubject(birthdayEmailDetails.getSubject());
			mimeMessageHelper.setText(birthdayEmailDetails.getBody());
			FileSystemResource file = new FileSystemResource(new File(imageLocation));
			mimeMessageHelper.addAttachment("HappyBirthday.jpg", file);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.print("Error occurred : " + e.getLocalizedMessage());
		}
	}

	@Override
	public void sendBirthdayEmailWithHtml(BirthdayEmailDetails birthdayEmailDetails) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(birthdayEmailDetails.getBody(), true);
			helper.setTo(birthdayEmailDetails.getTo());
			helper.setSubject(birthdayEmailDetails.getSubject());
			helper.setFrom(sender);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			logger.info("Error occurred : {}", e.getMessage());
		}
	}

}
