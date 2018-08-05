package hello.pdfbox;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdfbox")
public class PDFBoxController {
	
	@RequestMapping("/generate_pdf")
	public String generatePDF() {
		
		try(PDDocument pdfDoc = new PDDocument()) {
			PDPage page = new PDPage();
			pdfDoc.addPage(page);
			
			PDFont font = PDType0Font.load(pdfDoc, new File("./fonts/STGB2312.ttf"));
			
			try (PDPageContentStream contents = new PDPageContentStream(pdfDoc, page)) {
				contents.beginText();
				contents.setFont(font, 12);
				contents.newLineAtOffset(100, 700);
				contents.showText("My PDF, 测试！");
				contents.endText();
			}
			
			pdfDoc.save("./pdf/first.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}

}
