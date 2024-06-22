package com.pscodes.birthdaywisher.constant;

public class QueryConstant {

    public static final String SELECT_TODAY_BIRTHDAYS = "SELECT * FROM birthday_details b " +
            "WHERE EXTRACT(DAY FROM b.dob) = EXTRACT(DAY FROM CAST(:todayDate AS DATE)) " +
            "AND EXTRACT(MONTH FROM b.dob) = EXTRACT(MONTH FROM CAST(:todayDate AS DATE))";

}
