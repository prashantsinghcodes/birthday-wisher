package com.pscodes.birthdaywisher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pscodes.birthdaywisher.model.Birthday;
import com.pscodes.birthdaywisher.service.ExcelReaderService;

@RestController
@RequestMapping("v1")
public class BirthdayController {

	@Autowired
	private ExcelReaderService readerService;

	@GetMapping("/extract")
	public void getBirthdayList() {
		List<Birthday> birthdays = readerService.excelReader();
		System.out.print(birthdays.toString());
	}
}
