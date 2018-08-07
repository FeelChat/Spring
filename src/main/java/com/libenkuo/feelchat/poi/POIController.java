package com.libenkuo.feelchat.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utils.Constants;

@RestController
@RequestMapping("/poi")
public class POIController {
	
	@RequestMapping("/create_excel")
	public String createExcel() {
		
		Workbook wb = new HSSFWorkbook();
		Sheet export = wb.createSheet("信息导出");
		
		Row row = export.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("表头");
		try (OutputStream fileOut = new FileOutputStream(Constants.RESOURCE_PATH + "/excel/wb.xls")) {
			wb.write(fileOut);
		} catch(Exception e) {
			System.out.println("error");
		}
		
		return "success";
	}

}
