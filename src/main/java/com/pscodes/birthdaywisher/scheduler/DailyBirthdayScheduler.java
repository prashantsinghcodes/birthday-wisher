package com.pscodes.birthdaywisher.scheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pscodes.birthdaywisher.model.Birthday;
import com.pscodes.birthdaywisher.model.BirthdayEmailDetails;
import com.pscodes.birthdaywisher.service.BirthdayEmailServiceImpl;
import com.pscodes.birthdaywisher.service.ExcelReaderService;

@Component
public class DailyBirthdayScheduler {

	@Autowired
	ExcelReaderService excelReaderService;
	
	@Autowired
	BirthdayEmailServiceImpl birthdayEmailServiceImpl;
	
	@Scheduled(cron = "0 16 23 * * ?")
	public void dailyBirthdayChecker() {
		List<Birthday> birthdays = excelReaderService.excelReader();
		List<Birthday> todayBirthdays = new ArrayList<>();
		birthdays.forEach(birthday -> {
			LocalDate dob = birthday.getDob();
			LocalDate today = LocalDate.now();
			if (dob.getMonthValue() == today.getMonthValue() 
					&& dob.getDayOfMonth() == today.getDayOfMonth()) {
				todayBirthdays.add(birthday);
			}
		});
		todayBirthdays.forEach(birthday -> {
			sendBirthdayWish(birthday);
		});
	}
	
	private void sendBirthdayWish(Birthday birthday) {
		BirthdayEmailDetails birthdayEmailDetails = new BirthdayEmailDetails();
		birthdayEmailDetails.setTo(birthday.getEmail());
		birthdayEmailDetails.setSubject("Happy Birthday, " + birthday.getName().split(" ")[0]);
		birthdayEmailDetails.setBody("Wish you a very happy birthday to you!");
		birthdayEmailServiceImpl.sendSimpleBirthdayEmail(birthdayEmailDetails);
	}
}
