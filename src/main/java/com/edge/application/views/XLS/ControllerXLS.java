package com.edge.application.views.XLS;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edge.application.views.Table.SourceTagMappingTable;
import com.edge.application.views.service.EdgeService;

@Controller
public class ControllerXLS {
	
	@Autowired
	private EdgeService service;
	
	public ControllerXLS()
	{
		
	}
	
	@SuppressWarnings("unused")
	@RequestMapping("SourceTagXLS")
	@ResponseBody
	public String exportMonthly(HttpServletResponse response,HttpServletRequest rec) throws IOException {
		
		String para="",source_id="",source_name="";
        para = rec.getParameter("para");
        source_id=para.split("-")[0];
        source_name=para.split("-")[1];
		/*Starting code for create xls file and file name*/
		response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Source Ragister List.xls";
        response.setHeader(headerKey, headerValue);
        
        /*End File name*/
        
        ///declare the workbook 
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet;
	    sheet = workbook.createSheet("Ragister XLS");// work sheet name
	    /* create column and heading name*/
	    
	    Row row = sheet.createRow(0);
        
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        
        int i=0;
         
        createCell(row, i, "Name", style,sheet);
        i=i+1;
        createCell(row, i, "Address", style,sheet);
        i=i+1;
        createCell(row, i, "Lenght", style,sheet); 
        i=i+1;
        createCell(row, i, "DataType", style,sheet);
        i=i+1;
        createCell(row, i, "Multiplier", style,sheet);
        i=i+1;
        createCell(row, i, "Element", style,sheet);
        i=i+1;
        createCell(row, i, "Modbus Point", style,sheet);
        i=i+1;
		
        
        int rowCount = 1,rows=0;
        CellStyle style1 = workbook.createCellStyle();
        XSSFFont font1 = workbook.createFont();
        font1.setFontHeight(12);
        style1.setFont(font1);
        style1.setAlignment(HorizontalAlignment.CENTER);
        
        List<SourceTagMappingTable> list =null;
		list = service.source_id_finadAll(source_id);
		
		for(SourceTagMappingTable st:list)
		{
			Row row1 = sheet.createRow(rowCount++);
	        int columnCount = 0;
	        createCell(row1, columnCount++,""+st.getTag_name(), style1,sheet);
	        createCell(row1, columnCount++,Integer.parseInt(st.getRef_add()), style1,sheet);
	        createCell(row1, columnCount++,Integer.parseInt(st.getReg_len()), style1,sheet);
	        createCell(row1, columnCount++,""+st.getDatatype(), style1,sheet);
	        createCell(row1, columnCount++,Integer.parseInt(st.getUnit()), style1,sheet);
	        createCell(row1, columnCount++,""+st.getElement_name(), style1,sheet);
	        createCell(row1, columnCount++,""+st.getModbus_point(), style1,sheet);
		}
	    
	    
	    
	    
		 ServletOutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	         
	        outputStream.close();
	        return "<HTML><script>window.close();</script></HTML>";
	}
	
	public void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
		
		 sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        }else if (value instanceof Double) {
	            cell.setCellValue((Double) value);
	        }
	        else {
	            cell.setCellValue((String) value);
	        }
	        cell.setCellStyle(style);
	    }

}
