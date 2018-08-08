package com.libenkuo.feelchat.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utils.Constants;

@RestController
@RequestMapping("/poi/excel")
public class ExcelController {
	
	@RequestMapping("/create")
	public String createExcel() {
		
		Workbook wb = new XSSFWorkbook();
		Sheet export = wb.createSheet("信息导出");
		
		Row row = export.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("表头");
		try (OutputStream fileOut = new FileOutputStream(Constants.RESOURCE_PATH + "/excel/wb.xlsx")) {
			wb.write(fileOut);
			wb.close();
		} catch(Exception e) {
			System.out.println("error");
		}
		
		return "success";
	}

}
