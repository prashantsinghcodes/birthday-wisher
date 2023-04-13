package com.pscodes.birthdaywisher.service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pscodes.birthdaywisher.model.Birthday;

@SuppressWarnings("deprecation")
@Service
public class ExcelReaderService {
	
	private static Logger logger = LogManager.getLogger(ExcelReaderService.class);
	
	@Value("${excel.location}")
	private String excelLocation;
	
	public List<Birthday> excelReader() {
		List<Birthday> birthDayList = new ArrayList<>();
		try {
			File file = new File(excelLocation);
			try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					extractBirthday(row, birthDayList);
				}
			}
		} catch (Exception e) {
			logger.info("Exception occurred : {}", e.getMessage());
		}
		return birthDayList;
	}
	
	private void extractBirthday(Row row, List<Birthday> birthdays) {
		String name = row.getCell(1).getStringCellValue();
		String email = row.getCell(2).getStringCellValue();
		LocalDate dob = HSSFDateUtil.isCellDateFormatted(row.getCell(3)) ? 
				row.getCell(3).getLocalDateTimeCellValue().toLocalDate() : LocalDate.of(1900, 01, 01);
		birthdays.add(new Birthday(name, dob, email));
	}
}
