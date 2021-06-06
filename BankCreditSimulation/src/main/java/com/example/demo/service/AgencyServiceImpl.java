package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Agency;
import com.example.demo.entity.User;
import com.example.demo.repository.AgencyRepository;
import com.example.demo.repository.UserRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class AgencyServiceImpl implements AgencyService{
	
	@Autowired
	AgencyRepository agencyRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<Agency> findAllAgency() {
		return (List<Agency>)agencyRepository.findAll();
	}

	@Override
	public Agency getById(long id) {
		
		return agencyRepository.findById(id).get();
	}

	@Override
	public Agency addAgency(Agency agency) {
		return agencyRepository.save(agency);
		
	}

	@Override
	public void deleteAgency(long id) {
		agencyRepository.deleteById(id);
		
	}

	@Override
	public Agency updateAgency (Agency agency) {
		return agencyRepository.save(agency);
	}

	@Override
	public List<Agency> getByCity(String city) {
		return (List<Agency>)agencyRepository.getallAgencybyCity(city);
	}
	
	@Override
	public Agency affecterClientAAgence(long userId, long id) {
		 Agency agency = agencyRepository.findById(id).orElse(null);
		   User client = userRepository.findById(userId).orElse(null);
		   List<User> le = agency.getUser();
			le.add(client);
			agency.setUser(le);
			agencyRepository.save(agency);	
			return agency;
	}

	@Override
	public boolean createPdf() {
		List<Agency> agencies=agencyRepository.findAll();
		Document document=new Document(PageSize.A4, 15, 15, 45, 30);
		try{
			
	    //String filPath= context.getRealPath("/resources/reports");
		String filPath="C:\\Users\\MonPc\\Documents\\workspace-sts-3.8.4.RELEASE\\piProjet\\src\\main\\resources\\reports";
		//String filPath="C:/Users/MonPc/Documents/workspace-sts-3.8.4.RELEASE/piProjet/src/main/resources/reports";
	    File file =new File(filPath);
	    boolean exists= new File(filPath).exists();
	    if(!exists){
	    	new File(filPath).mkdirs();
	    }
	    
	    
	    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"agencies"+".pdf"));
	    document.open();
	    
	    Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
	    
	    Paragraph paragraph= new Paragraph("All Agencies", mainFont);
	    paragraph.setAlignment(Element.ALIGN_CENTER);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph);
	    
	    
	    PdfPTable table = new PdfPTable(6);
	    table.setWidthPercentage(100);
	    table.setSpacingBefore(10f);
	    table.setSpacingAfter(10);
	    
	    Font tableHeader = FontFactory.getFont("Arial",10, BaseColor.BLACK);
	    Font tableBody = FontFactory.getFont("Arial",9, BaseColor.BLACK);
	    
	    float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f};
	    table.setWidths(columnWidths);
	    
	    PdfPCell id =new PdfPCell(new Paragraph("Num", tableHeader));
	    id.setBorderColor(BaseColor.BLACK);
	    id.setPaddingLeft(10);
	    id.setHorizontalAlignment(Element.ALIGN_CENTER);
	    id.setVerticalAlignment(Element.ALIGN_CENTER);
	    id.setBackgroundColor(BaseColor.GRAY);
	    id.setExtraParagraphSpace(5);
	    table.addCell(id);
	    
	    PdfPCell agencyName =new PdfPCell(new Paragraph("Agency Name", tableHeader));
	    agencyName.setBorderColor(BaseColor.BLACK);
	    agencyName.setPaddingLeft(10);
	    agencyName.setHorizontalAlignment(Element.ALIGN_CENTER);
	    agencyName.setVerticalAlignment(Element.ALIGN_CENTER);
	    agencyName.setBackgroundColor(BaseColor.GRAY);
	    agencyName.setExtraParagraphSpace(5);
	    table.addCell(agencyName);
	    
	    PdfPCell city =new PdfPCell(new Paragraph("City", tableHeader));
	    city.setBorderColor(BaseColor.BLACK);
	    city.setPaddingLeft(10);
	    city.setHorizontalAlignment(Element.ALIGN_CENTER);
	    city.setVerticalAlignment(Element.ALIGN_CENTER);
	    city.setBackgroundColor(BaseColor.GRAY);
	    city.setExtraParagraphSpace(5);
	    table.addCell(city);
	    
	    PdfPCell adress =new PdfPCell(new Paragraph("Address", tableHeader));
	    adress.setBorderColor(BaseColor.BLACK);
	    adress.setPaddingLeft(10);
	    adress.setHorizontalAlignment(Element.ALIGN_CENTER);
	    adress.setVerticalAlignment(Element.ALIGN_CENTER);
	    adress.setBackgroundColor(BaseColor.GRAY);
	    adress.setExtraParagraphSpace(5);
	    table.addCell(adress);
	    
	    PdfPCell contact =new PdfPCell(new Paragraph("Contact", tableHeader));
	    contact.setBorderColor(BaseColor.BLACK);
	    contact.setPaddingLeft(10);
	    contact.setHorizontalAlignment(Element.ALIGN_CENTER);
	    contact.setVerticalAlignment(Element.ALIGN_CENTER);
	    contact.setBackgroundColor(BaseColor.GRAY);
	    contact.setExtraParagraphSpace(5);
	    table.addCell(contact);
	    
	    
	    PdfPCell email =new PdfPCell(new Paragraph("Email", tableHeader));
	    email.setBorderColor(BaseColor.BLACK);
	    email.setPaddingLeft(10);
	    email.setHorizontalAlignment(Element.ALIGN_CENTER);
	    email.setVerticalAlignment(Element.ALIGN_CENTER);
	    email.setBackgroundColor(BaseColor.GRAY);
	    email.setExtraParagraphSpace(5);
	    table.addCell(email);
	    
	     for (Agency agency: agencies){
	    	
	        PdfPCell idValue =new PdfPCell(new Paragraph(agency.getId()+"", tableBody));
	    	idValue.setBorderColor(BaseColor.BLACK);
	    	idValue.setPaddingLeft(10);
	    	idValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	idValue.setVerticalAlignment(Element.ALIGN_CENTER);
	    	idValue.setBackgroundColor(BaseColor.WHITE);
	    	idValue.setExtraParagraphSpace(5);
	 	    table.addCell(idValue);
	 	    
	 	   PdfPCell agencyNameValue =new PdfPCell(new Paragraph(agency.getAgencyName(), tableBody));
	 	   agencyNameValue.setBorderColor(BaseColor.BLACK);
	 	   agencyNameValue.setPaddingLeft(10);
	 	   agencyNameValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	   agencyNameValue.setVerticalAlignment(Element.ALIGN_CENTER);
	 	   agencyNameValue.setBackgroundColor(BaseColor.WHITE);
	 	   agencyNameValue.setExtraParagraphSpace(5);
	 	   table.addCell(agencyNameValue);
	 	    
	 	   PdfPCell cityValue =new PdfPCell(new Paragraph(agency.getCity(), tableBody));
	 	   cityValue.setBorderColor(BaseColor.BLACK);
	 	   cityValue.setPaddingLeft(10);
	 	   cityValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	   cityValue.setVerticalAlignment(Element.ALIGN_CENTER);
	 	   cityValue.setBackgroundColor(BaseColor.WHITE);
	 	   cityValue.setExtraParagraphSpace(5);
	 	   table.addCell(cityValue);
	 	   
	 	  PdfPCell adressValue =new PdfPCell(new Paragraph(agency.getAdress(), tableBody));
	 	  adressValue.setBorderColor(BaseColor.BLACK);
	 	  adressValue.setPaddingLeft(10);
	 	  adressValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	  adressValue.setVerticalAlignment(Element.ALIGN_CENTER);
	 	  adressValue.setBackgroundColor(BaseColor.WHITE);
	 	  adressValue.setExtraParagraphSpace(5);
	 	   table.addCell(adressValue);
	 	   
	 	  PdfPCell contactValue =new PdfPCell(new Paragraph(agency.getContact()+"", tableBody));
	 	  contactValue.setBorderColor(BaseColor.BLACK);
	 	  contactValue.setPaddingLeft(10);
	 	  contactValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	  contactValue.setVerticalAlignment(Element.ALIGN_CENTER);
	 	  contactValue.setBackgroundColor(BaseColor.WHITE);
	 	  contactValue.setExtraParagraphSpace(5);
	 	   table.addCell(contactValue);
	 	   
	 	  PdfPCell emailValue =new PdfPCell(new Paragraph(agency.getEmail(), tableBody));
	 	  emailValue.setBorderColor(BaseColor.BLACK);
	 	  emailValue.setPaddingLeft(10);
	 	  emailValue.setHorizontalAlignment(Element.ALIGN_CENTER);
	 	  emailValue.setVerticalAlignment(Element.ALIGN_CENTER);
	 	  emailValue.setBackgroundColor(BaseColor.WHITE);
	 	  emailValue.setExtraParagraphSpace(5);
	 	   table.addCell(emailValue);
	    	 
	     }
	     
	     document.add(table);
	     document.close();
	     writer.close();
	     return true;
	
		}catch (Exception e) {
			return false;
		}
		
		
	}

	@Override
	public boolean createExcel(List<Agency> agencies, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if(!exists){
			new File(filePath).mkdir();
			
		}
		
		try{
			
			FileOutputStream outputStream = new FileOutputStream(file +"/"+"agencies"+".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet workSheet = workbook.createSheet("agencies");
			workSheet.setDefaultColumnWidth(30);
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
			headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFRow headerRow = workSheet.createRow(0);
			
			HSSFCell id = headerRow.createCell(0);
			id.setCellValue("Num");
			id.setCellStyle(headerCellStyle);
			
			HSSFCell agencyName = headerRow.createCell(1);
			agencyName.setCellValue("Agency Name");
			agencyName.setCellStyle(headerCellStyle);
			
			HSSFCell city = headerRow.createCell(2);
			city.setCellValue("City");
			city.setCellStyle(headerCellStyle);
			
			HSSFCell adress = headerRow.createCell(3);
			adress.setCellValue("Adress");
			adress.setCellStyle(headerCellStyle);
			
			HSSFCell contact = headerRow.createCell(4);
			contact.setCellValue("Contact");
			contact.setCellStyle(headerCellStyle);
			
			HSSFCell email = headerRow.createCell(5);
			email.setCellValue("Email");
			email.setCellStyle(headerCellStyle);
			
			int i=1;
			for(Agency agency : agencies){
				
				HSSFRow bodyRow =workSheet.createRow(i);
				HSSFCellStyle bodyCelleStyle = workbook.createCellStyle();
				bodyCelleStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				
				HSSFCell idValue = bodyRow.createCell(0);
				idValue.setCellValue(agency.getId());
				idValue.setCellStyle(bodyCelleStyle);
				
				HSSFCell agencyNameValue = bodyRow.createCell(1);
				agencyNameValue.setCellValue(agency.getAgencyName());
				agencyNameValue.setCellStyle(bodyCelleStyle);
				
				HSSFCell cityValue = bodyRow.createCell(2);
				cityValue.setCellValue(agency.getCity());
				cityValue.setCellStyle(bodyCelleStyle);
				
				HSSFCell adressValue = bodyRow.createCell(3);
				adressValue.setCellValue(agency.getAdress());
				adressValue.setCellStyle(bodyCelleStyle);
				
				HSSFCell contactValue = bodyRow.createCell(4);
				contactValue.setCellValue(agency.getContact());
				contactValue.setCellStyle(bodyCelleStyle);
				
				HSSFCell emailValue = bodyRow.createCell(5);
				emailValue.setCellValue(agency.getEmail());
				emailValue.setCellStyle(bodyCelleStyle);
				
				i++;
				 
			}
			
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return true;
			
		}catch (Exception e) {
			return false;
		}
	
	}

	@Override
	public long getNombreAgencyJPQL(String city) {
		return agencyRepository.getNombreAgencyJPQL(city);
	}

	
	@Override
	public List<Agency> searchAgency(String keyword) {
		if(keyword != null){
			
		return agencyRepository.searchAgencybyName(keyword);
		}
		return agencyRepository.findAll();
	}
	

}
