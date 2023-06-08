package com.edge.application.views.PDF;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.Table.SourceTagMappingTable;
import com.edge.application.views.service.EdgeService;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Controller
public class ControllerPDF {
	
	
	
	@Autowired
	private EdgeService service;
	
	public ControllerPDF()
	{
		
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("SourceTagPDF")
	@ResponseBody
	public String exportToPDF(HttpServletResponse response,HttpServletRequest rec) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        
        String para="",source_id="",source_name="";
        para = rec.getParameter("para");
        source_id=para.split("-")[0];
        source_name=para.split("-")[1];
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Source Ragister List.pdf";
        response.setHeader(headerKey, headerValue);
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(10);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph(source_name+" Ragister List", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
        
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.WHITE);
        cell.setPadding(5);
         
        Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
        font1.setColor(Color.BLUE);
        font1.setSize(10);
        
        Font fontdata = FontFactory.getFont(FontFactory.HELVETICA);
        fontdata.setColor(Color.BLACK);
        fontdata.setSize(8);
        
        cell.setPhrase(new Phrase("Name", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Address", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Lenght", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("DataType", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Multiplier", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Element", font1));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Modbus Point", font1));
        table.addCell(cell);
        
        List<SourceTagMappingTable> list =null;
		list = service.source_id_finadAll(source_id);
		
		for(SourceTagMappingTable st:list)
		{
			cell.setPhrase(new Phrase(""+st.getTag_name(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getRef_add(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getReg_len(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getDatatype(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getUnit(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getElement_name(), fontdata));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase(""+st.getModbus_point(), fontdata));
	        table.addCell(cell);
		}
		
		document.add(table);
        
        document.close();
        
        
        return "<HTML><script>window.close();</script></HTML>";
	}

}
