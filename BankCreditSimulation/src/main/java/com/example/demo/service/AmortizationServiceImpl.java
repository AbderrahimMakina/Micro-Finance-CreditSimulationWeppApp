package com.example.demo.service;


import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.entity.Amortization;
import com.example.demo.entity.Payment;
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
public class AmortizationServiceImpl implements AmortizationService {
	
	@Autowired
    private PaymentService paymentService;

	@Override
	public void initializeUnknownFields(Amortization amortization) {
		// extract required parameters
        Date startDate = amortization.getStartDate();
        double initialBalance = amortization.getInitialBalance();
        double interestRate = amortization.getInterestRate();
        int durationInMonths = amortization.getDurationInMonths();
        double futureValue = amortization.getFutureValue();
        int paymentType = amortization.getPaymentType();

        // compute monthly payment
        double monthlyPayment = paymentService.pmt(paymentService.getMonthlyInterestRate(interestRate), durationInMonths, initialBalance, futureValue, paymentType);
        amortization.setMonthlyPayment(monthlyPayment);

        // calculate detailed payment list
        List<Payment> paymentList = calculatePaymentList(startDate, initialBalance, durationInMonths, paymentType, interestRate, futureValue);
        amortization.addAllPayments(paymentList);
		
	}

	@Override
	public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths,
			int paymentType, double interestRate, double futureValue) {
		List<Payment> paymentList = new ArrayList<Payment>();
        Date loopDate = startDate;
        double balance = initialBalance;
        double accumulatedInterest = 0;
        for (int paymentNumber = 1; paymentNumber <= durationInMonths; paymentNumber++)
        {
            if (paymentType == 0)
            {
                loopDate = addOneMonth(loopDate);
            }
            double principalPaid = paymentService.ppmt(paymentService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            double interestPaid = paymentService.ipmt(paymentService.getMonthlyInterestRate(interestRate), paymentNumber, durationInMonths, initialBalance, futureValue, paymentType);
            balance = balance + principalPaid;
            accumulatedInterest += interestPaid;

            Payment payment = new Payment(paymentNumber, loopDate, balance, principalPaid, interestPaid, accumulatedInterest);

            paymentList.add(payment);

            if (paymentType == 1)
            {
                loopDate = addOneMonth(loopDate);
            }
        }
        return paymentList;
	}

	@Override
	public Date addOneMonth(Date date) {
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
	}

	@Override
	public boolean createPdf(List<Payment> payments, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		Document document=new Document(PageSize.A4,15,15,45,30);
		try{
			
	    String filPath= context.getRealPath("/resources/reports");
	    File file =new File(filPath);
	    boolean exists= new File(filPath).exists();
	    if(!exists){
	    	new File(filPath).mkdirs();
	    }
	    
	    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file+"/"+"payments"+".pdf"));
	    document.open();
	    
	    Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);
	    
	    Paragraph paragraph= new Paragraph("Credit monthly payments", mainFont);
	    paragraph.setAlignment(Element.ALIGN_CENTER);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph);
	    
	    Paragraph paragraph1= new Paragraph("Start date of credit:", mainFont);
	    paragraph.setAlignment(Element.ALIGN_LEFT);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph1);
	    
	    Paragraph paragraph2= new Paragraph("InterstRate: 11.5%", mainFont);
	    paragraph.setAlignment(Element.ALIGN_LEFT);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph2);
	    
	    Paragraph paragraph3= new Paragraph("Amount of credit requested:", mainFont);
	    paragraph.setAlignment(Element.ALIGN_LEFT);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph3);
	    
	    Paragraph paragraph4= new Paragraph("Duration in months:", mainFont);
	    paragraph.setAlignment(Element.ALIGN_LEFT);
	    paragraph.setIndentationLeft(50);
	    paragraph.setIndentationRight(50);
	    paragraph.setSpacingAfter(10);
	    document.add(paragraph4);
	    
	    
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
		     document.add(table);
		     document.close();
		     writer.close();
		     return true;
          
          
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}
}

