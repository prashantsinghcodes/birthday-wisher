package com.pscodes.birthdaywisher.scheduler;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pscodes.birthdaywisher.model.Birthday;
import com.pscodes.birthdaywisher.model.BirthdayEmailDetails;
import com.pscodes.birthdaywisher.service.BirthdayEmailServiceImpl;
import com.pscodes.birthdaywisher.service.ExcelReaderService;

@Component
public class DailyBirthdayScheduler {
	
	private static Logger logger = LogManager.getLogger(DailyBirthdayScheduler.class);

	@Autowired
	ExcelReaderService excelReaderService;
	
	@Autowired
	BirthdayEmailServiceImpl birthdayEmailServiceImpl;
	
	@Scheduled(cron = "${cron.expression}")
	public void dailyBirthdayChecker() {
		try {
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
				Thread thread = new Thread(() -> sendBirthdayWish(birthday, true));
				thread.start();
			});
		} catch (Exception ex) {
			logger.info("Exception occurred : {}", ex.getMessage());
		}
	}
	
	private void sendBirthdayWish(Birthday birthday, boolean isHtml) {
		Period period = Period.between(birthday.getDob(), LocalDate.now());
		BirthdayEmailDetails birthdayEmailDetails = new BirthdayEmailDetails();
		birthdayEmailDetails.setTo(birthday.getEmail());
		birthdayEmailDetails.setSubject("Happy Birthday, " + birthday.getName().split(" ")[0]);
		String body = !isHtml ? "Wish you a very happy birthday to you!" : "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ " <title>Happy Birthday</title>\r\n"
				+ " <meta charset=\"utf-8\" />\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ " <div id=\"birthdayCard\">\r\n"
				+ "  <h1>Happy Birthday, <span class=\"redText\">" + birthday.getName().split(" ")[0] + "</span>!</h1>\r\n"
				+ "  <p>You are <span class=\"redText\">" + period.getYears() + "</span> years old now.</p>\r\n"
				+ "  <p>I sincerely wish you <strong>success</strong> and <strong>happiness</strong> in your future life.</p>\r\n"
				+ "  <p>You're a great person!</p>\r\n"
				+ "  <p class=\"signature\">–Your friend, Ankit</p>\r\n"
				+ " </div>\r\n"
				+ "</body>\r\n"
				+ "</html>";
		birthdayEmailDetails.setBody(body);
		//birthdayEmailServiceImpl.sendSimpleBirthdayEmail(birthdayEmailDetails);
		//birthdayEmailServiceImpl.sendBirthdayEmailWithAttachment(birthdayEmailDetails);
		birthdayEmailServiceImpl.sendBirthdayEmailWithHtml(birthdayEmailDetails);
	}
}
