package com.pscodes.birthdaywisher.service;

import com.pscodes.birthdaywisher.model.BirthdayEmailDetails;

public interface BirthdayEmailService {
	
	void sendSimpleBirthdayEmail(BirthdayEmailDetails birthdayEmailDetails);
	
	void sendBirthdayEmailWithAttachment(BirthdayEmailDetails birthdayEmailDetails);
	
}
