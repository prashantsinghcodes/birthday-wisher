package com.pscodes.birthdaywisher.service;

import com.pscodes.birthdaywisher.entity.BirthdayDetails;
import com.pscodes.birthdaywisher.mapper.BirthdayDetailsMapper;
import com.pscodes.birthdaywisher.model.Birthday;
import com.pscodes.birthdaywisher.repository.BirthdayDetailsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BirthdayService {

    private static Logger logger = LogManager.getLogger(BirthdayService.class);


    @Autowired
    private BirthdayDetailsMapper birthdayDetailsMapper;

    @Autowired
    private BirthdayDetailsRepository birthdayDetailsRepository;

    public boolean saveBirthday(Birthday birthday) {
        try {
            BirthdayDetails birthdayDetails = birthdayDetailsMapper.getBirthdayDetails(birthday);
            birthdayDetailsRepository.save(birthdayDetails);
            logger.info("[BirthdayService][saveBirthday] Birthday saved successfully...");
            return true;
        } catch (Exception ex) {
            logger.error("[BirthdayService][saveBirthday] Exception while saving birthday to database..", ex);
            return false;
        }
    }

    public List<BirthdayDetails> getTodayBirthdayDetails() {
        try {
            LocalDate todayDate = LocalDate.now();
            List<BirthdayDetails> birthdayDetails = birthdayDetailsRepository.fetchTodayBirthdayDetails(todayDate);
            logger.info("[BirthdayService][getTodayBirthdayDetails] fetched today's birthday details successfully...");
            return birthdayDetails;
        } catch (Exception ex) {
            logger.error("[BirthdayService][getTodayBirthdayDetails] Exception while fetching today's birthday details from database..", ex);
            return new ArrayList<>();
        }
    }
}
