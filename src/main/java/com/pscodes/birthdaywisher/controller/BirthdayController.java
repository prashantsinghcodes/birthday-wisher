package com.pscodes.birthdaywisher.controller;

import com.pscodes.birthdaywisher.model.Birthday;
import com.pscodes.birthdaywisher.service.BirthdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/birthday")
public class BirthdayController {

    @Autowired
    private BirthdayService birthdayService;

    @PostMapping
    @RequestMapping(value = "/save")
    public ResponseEntity<String> save(@RequestBody Birthday birthday) {
        boolean isBirthdaySaved = birthdayService.saveBirthday(birthday);
        if(isBirthdaySaved) {
            return ResponseEntity.ok("Birthday saved successfully..");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Issue while saving the request");
        }
    }
}
