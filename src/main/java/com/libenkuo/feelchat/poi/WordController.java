package com.libenkuo.feelchat.poi;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utils.Constants;

@RestController
@RequestMapping("/poi/word")
public class WordController {
	
	@RequestMapping("create")
	public String createWord() {
		
		try (XWPFDocument doc = new XWPFDocument()) {
			XWPFParagraph paragraph = doc.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			paragraph.setBorderBottom(Borders.DOUBLE);
			paragraph.setBorderBetween(Borders.SINGLE);
			paragraph.setVerticalAlignment(TextAlignment.TOP);
			
			XWPFRun run = paragraph.createRun();
			run.setBold(true);
			run.setText("第一个Word文档!");
			run.setBold(true);
			run.setFontFamily("宋体");
			run.setUnderline(UnderlinePatterns.DOUBLE);
			run.setTextPosition(100);
			
			try (FileOutputStream out = new FileOutputStream(Constants.RESOURCE_PATH + "/word/doc.docx")) {
				doc.write(out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}

}
