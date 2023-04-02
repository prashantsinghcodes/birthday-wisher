package com.pscodes.birthdaywisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pscodes.birthdaywisher.model.BirthdayEmailDetails;

@Service
public class BirthdayEmailServiceImpl implements BirthdayEmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
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
			System.out.print("Mail sent successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getLocalizedMessage());
		}
		//return null;
	}

	@Override
	public String sendBirthdayEmailWithAttachment(BirthdayEmailDetails birthdayEmailDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}
