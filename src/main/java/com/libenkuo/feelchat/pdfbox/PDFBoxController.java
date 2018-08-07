package com.libenkuo.feelchat.pdfbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.tools.OverlayPDF;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utils.Constants;

@RestController
@RequestMapping("/pdfbox")
public class PDFBoxController {
	
	@RequestMapping("/watermark_pdf")
	public String watermarkPDF() {
		
		try(PDDocument pdfDoc = new PDDocument()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);
			
			try (PDPageContentStream contents = new PDPageContentStream(pdfDoc, page)) {
				PDImageXObject pdImage = PDImageXObject.createFromFile(Constants.RESOURCE_PATH + "/image/logo.png", pdfDoc);
				PDRectangle mediaBox = page.getMediaBox();
				float startX = (mediaBox.getWidth() - pdImage.getWidth()) / 2;
				float startY = (mediaBox.getHeight() - pdImage.getHeight()) / 2;

				contents.drawImage(pdImage, startX, startY);
			}
			
			pdfDoc.save(Constants.RESOURCE_PATH + "/pdf/logo.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping("/text_pdf")
	public String textPDF() {
		
		try(PDDocument pdfDoc = new PDDocument()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);
			
			PDFont font = PDType0Font.load(pdfDoc, new File(Constants.RESOURCE_PATH + "/fonts/STGB2312.ttf"));

			try (PDPageContentStream contents = new PDPageContentStream(pdfDoc, page)) {
				contents.beginText();
				contents.setFont(font, 12);
				contents.newLineAtOffset(100, 700);
				contents.showText("My PDF, 测试！");
				contents.endText();
			}
			
			pdfDoc.save(Constants.RESOURCE_PATH + "/pdf/text.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	@RequestMapping("/form_pdf")
	public String formPDF() {
		try(PDDocument pdfDoc = new PDDocument()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);
			PDAcroForm acroForm = new PDAcroForm(pdfDoc);
			pdfDoc.getDocumentCatalog().setAcroForm(acroForm);

			PDFont font = PDType0Font.load(pdfDoc, new FileInputStream(Constants.RESOURCE_PATH + "/fonts/STGB2312.ttf"), false);
			
			final PDResources resources = new PDResources();
			acroForm.setDefaultResources(resources);
			final String fontName = resources.add(font).getName();
			
			acroForm.setDefaultResources(resources);
			String defaultAppearancesString = "/" + fontName + " 0 Tf 0 g";
			
			PDTextField textBox = new PDTextField(acroForm);
			textBox.setPartialName("test");
			textBox.setDefaultAppearance(defaultAppearancesString);
			acroForm.getFields().add(textBox);
			
			PDAnnotationWidget widget = textBox.getWidgets().get(0);
			PDRectangle rect = new PDRectangle(50, 700, 200, 50);
			widget.setRectangle(rect);
			widget.setPage(page);
			
			PDAppearanceCharacteristicsDictionary fieldAppearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
			PDColor green = new PDColor(new float[] {0, 1, 0}, PDDeviceRGB.INSTANCE);
			fieldAppearance.setBorderColour(green);
			widget.setAppearanceCharacteristics(fieldAppearance);
			
			page.getAnnotations().add(widget);
			textBox.setValue("项目申报");

			pdfDoc.save(Constants.RESOURCE_PATH + "/pdf/form.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping("overlay_pdf")
	public String overlayPDF() throws Exception {
		String[] overlayArgs = new String[3];
		overlayArgs[0] = Constants.RESOURCE_PATH + "/pdf/text.pdf";
		overlayArgs[1] = Constants.RESOURCE_PATH + "/pdf/logo.pdf";
		overlayArgs[2] = Constants.RESOURCE_PATH + "/pdf/watermark.pdf";
		OverlayPDF.main(overlayArgs);
		return "success";
	}
	
}
