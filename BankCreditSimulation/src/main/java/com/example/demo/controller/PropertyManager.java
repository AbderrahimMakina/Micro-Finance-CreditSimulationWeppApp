package com.example.demo.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.entity.Agency;
import com.example.demo.entity.Amortization;
import com.example.demo.entity.Payment;
import com.example.demo.repository.AgencyRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


@Named(value = "manager")
@ViewScoped
public class PropertyManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String agencyName;
	private String city;
	private String adress;
	private long contact;
	private String email;
	
	private int paymentNumber;
    @DateTimeFormat(pattern = "MM/dd/yyyy") 
    private Date paymentDate;
	private double balance;
    private double principalPaid;
    private double interestPaid;
    private double accumulatedInterest;
    
    private Amortization amortization = new Amortization();
	
	@Autowired
	AgencyRepository agencyRepository;
	
	
	 public void postProcessPDF(Object document) throws com.itextpdf.text.DocumentException {
		 
		 List<Agency> agencies=agencyRepository.findAll();
		
		    
		    Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
		    
		    Paragraph paragraph= new Paragraph("All Agencies", mainFont);
		    paragraph.setAlignment(Element.ALIGN_CENTER);
		    paragraph.setIndentationLeft(50);
		    paragraph.setIndentationRight(50);
		    paragraph.setSpacingAfter(10);
		  
		    
		    
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
		     
		    
		 
		 
	 }
	 
	 public void postProcessXLS(Object document) {
		 
		 List<Agency> agencies=agencyRepository.findAll();
		 
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
	 }
	 
	 
	 public void postProcessPDFSimulation(Object document) throws com.itextpdf.text.DocumentException {
		 
		 
		 List<Payment> payments=amortization.getPaymentList();
		 Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
		    
		    
		    
		    
		    PdfPTable table = new PdfPTable(6);
		    table.setWidthPercentage(100);
		    table.setSpacingBefore(10f);
		    table.setSpacingAfter(10);
		    
		    Font tableHeader = FontFactory.getFont("Arial",10, BaseColor.BLACK);
		    Font tableBody = FontFactory.getFont("Arial",9, BaseColor.BLACK);
		    
		    float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f};
		    table.setWidths(columnWidths);
		    
		    PdfPCell paymentNumber =new PdfPCell(new Paragraph("paymentNumber", tableHeader));
		    paymentNumber.setBorderColor(BaseColor.BLACK);
		    paymentNumber.setPaddingLeft(10);
		    paymentNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
		    paymentNumber.setVerticalAlignment(Element.ALIGN_CENTER);
		    paymentNumber.setBackgroundColor(BaseColor.GRAY);
		    paymentNumber.setExtraParagraphSpace(5);
		    table.addCell(paymentNumber);
		    
		    
		    PdfPCell paymentDate =new PdfPCell(new Paragraph("paymentDate", tableHeader));
		    paymentDate.setBorderColor(BaseColor.BLACK);
		    paymentDate.setPaddingLeft(10);
		    paymentDate.setHorizontalAlignment(Element.ALIGN_CENTER);
		    paymentDate.setVerticalAlignment(Element.ALIGN_CENTER);
		    paymentDate.setBackgroundColor(BaseColor.GRAY);
		    paymentDate.setExtraParagraphSpace(5);
		    table.addCell(paymentDate);
		    
		    PdfPCell balance =new PdfPCell(new Paragraph("balance", tableHeader));
		    balance.setBorderColor(BaseColor.BLACK);
		    balance.setPaddingLeft(10);
		    balance.setHorizontalAlignment(Element.ALIGN_CENTER);
		    balance.setVerticalAlignment(Element.ALIGN_CENTER);
		    balance.setBackgroundColor(BaseColor.GRAY);
		    balance.setExtraParagraphSpace(5);
		    table.addCell(balance);
		    
		    PdfPCell principalPaid =new PdfPCell(new Paragraph("principalPaid", tableHeader));
		    principalPaid.setBorderColor(BaseColor.BLACK);
		    principalPaid.setPaddingLeft(10);
		    principalPaid.setHorizontalAlignment(Element.ALIGN_CENTER);
		    principalPaid.setVerticalAlignment(Element.ALIGN_CENTER);
		    principalPaid.setBackgroundColor(BaseColor.GRAY);
		    principalPaid.setExtraParagraphSpace(5);
		    table.addCell(principalPaid);
		    
		    PdfPCell interestPaid =new PdfPCell(new Paragraph("interestPaid", tableHeader));
		    interestPaid.setBorderColor(BaseColor.BLACK);
		    interestPaid.setPaddingLeft(10);
		    interestPaid.setHorizontalAlignment(Element.ALIGN_CENTER);
		    interestPaid.setVerticalAlignment(Element.ALIGN_CENTER);
		    interestPaid.setBackgroundColor(BaseColor.GRAY);
		    interestPaid.setExtraParagraphSpace(5);
		    table.addCell(interestPaid);
		    
		    
		    PdfPCell accumulatedInterest =new PdfPCell(new Paragraph("accumulatedInterest", tableHeader));
		    accumulatedInterest.setBorderColor(BaseColor.BLACK);
		    accumulatedInterest.setPaddingLeft(10);
		    accumulatedInterest.setHorizontalAlignment(Element.ALIGN_CENTER);
		    accumulatedInterest.setVerticalAlignment(Element.ALIGN_CENTER);
		    accumulatedInterest.setBackgroundColor(BaseColor.GRAY);
		    accumulatedInterest.setExtraParagraphSpace(5);
		    table.addCell(accumulatedInterest);
		    
	          for (Payment payment: payments){
	        	System.out.println(payments);
	        	 DecimalFormat df = new DecimalFormat("########.00");
		        PdfPCell paymentNumberValue =new PdfPCell(new Paragraph(payment.getPaymentNumber()+"", tableBody));
		        paymentNumberValue.setBorderColor(BaseColor.BLACK);
		        paymentNumberValue.setPaddingLeft(10);
		        paymentNumberValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		        paymentNumberValue.setVerticalAlignment(Element.ALIGN_CENTER);
		        paymentNumberValue.setBackgroundColor(BaseColor.WHITE);
		        paymentNumberValue.setExtraParagraphSpace(5);
		 	    table.addCell(paymentNumberValue);
		 	    
		 	   SimpleDateFormat DateF = new SimpleDateFormat("MM/dd/yyyy");
		 	   PdfPCell startDateValue =new PdfPCell(new Paragraph(DateF.format(payment.getPaymentDate())+"", tableBody));
		 	   startDateValue.setBorderColor(BaseColor.BLACK);
		 	   startDateValue.setPaddingLeft(10);
		 	   startDateValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		 	   startDateValue.setVerticalAlignment(Element.ALIGN_CENTER);
		 	   startDateValue.setBackgroundColor(BaseColor.WHITE);
		 	   startDateValue.setExtraParagraphSpace(5);
		 	   table.addCell(startDateValue);
		 	    
		 	   
		 	    PdfPCell balanceValue =new PdfPCell(new Paragraph(df.format(payment.getBalance())+"", tableBody));
		 	    balanceValue.setBorderColor(BaseColor.BLACK);
		 	    balanceValue.setPaddingLeft(10);
		 	    balanceValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		 	    balanceValue.setVerticalAlignment(Element.ALIGN_CENTER);
		 	    balanceValue.setBackgroundColor(BaseColor.WHITE);
		 	    balanceValue.setExtraParagraphSpace(5);
		 	  
		 	    table.addCell(balanceValue);
		 	    
		 	    PdfPCell principalPaidValue =new PdfPCell(new Paragraph(df.format(payment.getPrincipalPaid())+"", tableBody));
		 	    principalPaidValue.setBorderColor(BaseColor.BLACK);
		 	    principalPaidValue.setPaddingLeft(10);
		 	    principalPaidValue.setHorizontalAlignment(Element.ALIGN_CENTER);
		 	    principalPaidValue.setVerticalAlignment(Element.ALIGN_CENTER);
		 	    principalPaidValue.setBackgroundColor(BaseColor.WHITE);
		 	    principalPaidValue.setExtraParagraphSpace(5);
			 	table.addCell(principalPaidValue);
			 	    
			 	PdfPCell interestPaidValue =new PdfPCell(new Paragraph(df.format(payment.getInterestPaid())+"", tableBody));
			 	interestPaidValue.setBorderColor(BaseColor.BLACK);
			 	interestPaidValue.setPaddingLeft(10);
			 	interestPaidValue.setHorizontalAlignment(Element.ALIGN_CENTER);
			 	interestPaidValue.setVerticalAlignment(Element.ALIGN_CENTER);
			 	interestPaidValue.setBackgroundColor(BaseColor.WHITE);
			 	interestPaidValue.setExtraParagraphSpace(5);
				table.addCell(interestPaidValue);
					 	    
				 PdfPCell accumulatedInterestValue =new PdfPCell(new Paragraph(df.format(payment.getAccumulatedInterest())+"", tableBody));
				 accumulatedInterestValue.setBorderColor(BaseColor.BLACK);
				 accumulatedInterestValue.setPaddingLeft(10);
				 accumulatedInterestValue.setHorizontalAlignment(Element.ALIGN_CENTER);
				 accumulatedInterestValue.setVerticalAlignment(Element.ALIGN_CENTER);
				 accumulatedInterestValue.setBackgroundColor(BaseColor.WHITE);
				 accumulatedInterestValue.setExtraParagraphSpace(5);
			     table.addCell(accumulatedInterestValue);
	          }
		 
	 }

    public long getId() {
		return id;
	}




	public void setId(long id) {
		this.id = id;
	}




	public String getAgencyName() {
		return agencyName;
	}




	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public String getAdress() {
		return adress;
	}




	public void setAdress(String adress) {
		this.adress = adress;
	}




	public long getContact() {
		return contact;
	}




	public void setContact(long contact) {
		this.contact = contact;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}


	public int getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(int paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getPrincipalPaid() {
		return principalPaid;
	}

	public void setPrincipalPaid(double principalPaid) {
		this.principalPaid = principalPaid;
	}

	public double getInterestPaid() {
		return interestPaid;
	}

	public void setInterestPaid(double interestPaid) {
		this.interestPaid = interestPaid;
	}

	public double getAccumulatedInterest() {
		return accumulatedInterest;
	}

	public void setAccumulatedInterest(double accumulatedInterest) {
		this.accumulatedInterest = accumulatedInterest;
	}





	

}