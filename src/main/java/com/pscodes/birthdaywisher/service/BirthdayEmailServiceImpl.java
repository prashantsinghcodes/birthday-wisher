package com.pscodes.birthdaywisher.service;

import java.io.File;

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

	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	@Value("${image.location}")
	private String imageLocation;
	
	@Override
	public void sendSimpleBirthdayEmail(BirthdayEmailDetails birthdayEmailDetails) {
		// TODO Auto-generated method stub
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(sender);
			simpleMailMessage.setTo(birthdayEmailDetails.getTo());
			simpleMailMessage.setText(birthdayEmailDetails.getBody());
			simpleMailMessage.setSubject(birthdayEmailDetails.getSubject());
			javaMailSender.send(simpleMailMessage);
			System.out.println("Mail sent successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getLocalizedMessage());
		}
		//return null;
	}

	@Override
	public void sendBirthdayEmailWithAttachment(BirthdayEmailDetails birthdayEmailDetails) {
		// TODO Auto-generated method stub
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
			// TODO: handle exception
			System.out.print("Error occurred : " + e.getLocalizedMessage());
		}
	}

	@Override
	public void sendBirthdayEmailWithHtml(BirthdayEmailDetails birthdayEmailDetails) {
		// TODO Auto-generated method stub
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
			helper.setText(birthdayEmailDetails.getBody(), true); // Use this or above line.
			helper.setTo(birthdayEmailDetails.getTo());
			helper.setSubject(birthdayEmailDetails.getSubject());
			helper.setFrom(sender);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Error occurred : " + e.getLocalizedMessage());
		}
	}

}
