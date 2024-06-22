package com.pscodes.birthdaywisher.mapper;

import com.pscodes.birthdaywisher.entity.BirthdayDetails;
import com.pscodes.birthdaywisher.model.Birthday;
import org.springframework.stereotype.Component;

@Component
public class BirthdayDetailsMapper {

    public BirthdayDetails getBirthdayDetails(Birthday birthday) {
        BirthdayDetails birthdayDetails = new BirthdayDetails();
        birthdayDetails.setName(birthday.getName());
        birthdayDetails.setEmail(birthday.getEmail());
        birthdayDetails.setDob(birthday.getDob());
        return birthdayDetails;
    }
}
