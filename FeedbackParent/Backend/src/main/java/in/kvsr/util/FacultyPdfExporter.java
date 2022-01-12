package in.kvsr.util;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.kvsr.common.entity.Faculty;

public class FacultyPdfExporter extends AbstractExporter {
	
	public void export(HttpServletResponse httpServletResponse,
			            List<Faculty> facultyList) throws IOException {
		super.setResponseHeader(httpServletResponse, "application/pdf", ".pdf");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = dateFormat.format(new Date());
		Document document = new Document();
		
		PdfWriter.getInstance(document, httpServletResponse.getOutputStream());
		
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE);
		font.setSize(10);
		font.setColor(0,0,128);
		
		Paragraph date = new Paragraph(timestamp, font);
		date.setAlignment(Paragraph.ALIGN_RIGHT);
		document.add(date);
		
		font = FontFactory.getFont(FontFactory.COURIER_BOLDOBLIQUE);
		font.setSize(18);
		font.setColor(0,0,128);
		Paragraph center = new Paragraph("Faculty Averages", font);
		center.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(center);
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(15);
		float[] widths = {3.0f, 3.5f, 2.5f, 2.2f};
		table.setWidths(widths);
		writeTableHeader(table);
		writeTableData(table, facultyList);
		document.add(table);
		document.close();
	}

	private void writeTableData(PdfPTable table, List<Faculty> facultyList) {
		for(Faculty faculty: facultyList) {
			table.addCell(faculty.getRegId());
			table.addCell(faculty.getFirstName());
			table.addCell(faculty.getDepartment());
			table.addCell(String.valueOf(faculty.getAverage()));
		}
		
	}

	private void writeTableHeader(PdfPTable table) {
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.black);
		
		Font font = new Font();
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("Reg. Id",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Name",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Department",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Average",font));
		table.addCell(cell);
		
	}
	
}
