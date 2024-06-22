package com.pscodes.birthdaywisher.repository;

import com.pscodes.birthdaywisher.constant.QueryConstant;
import com.pscodes.birthdaywisher.entity.BirthdayDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BirthdayDetailsRepository extends JpaRepository<BirthdayDetails, Long> {

    @Query(value = QueryConstant.SELECT_TODAY_BIRTHDAYS, nativeQuery = true)
    public List<BirthdayDetails> fetchTodayBirthdayDetails(@Param("todayDate") LocalDate todayDate);
}
