package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Amortization;
import com.example.demo.entity.Payment;
import com.example.demo.service.AmortizationService;

@RestController
@RequestMapping("/simulation")

public class SimulationRestController  {
	
	@Autowired
	private AmortizationService amortizationService;
	
	@Autowired
	private ServletContext context;
	
	Amortization a;
	

	@PostMapping("/amortisation")
	public ResponseEntity<Amortization> retrieveAmortization(@RequestBody Amortization amortization) {
	this.a = amortization;
    amortizationService.initializeUnknownFields(amortization);
	amortization.setPaymentList(amortizationService.calculatePaymentList(amortization.getStartDate(), amortization.getInitialBalance(), amortization.getDurationInMonths(), amortization.getPaymentType(), amortization.getInterestRate(), amortization.getFutureValue()));
    return ResponseEntity.status(200).body(amortization);	
	}
	
	@GetMapping("/Scraping")
	public float GetTMMWithJsoup() throws IOException {
		Document doc = Jsoup.connect("http://www.stb.com.tn/fr/site/bourse-change/historique-des-tmm/").get();
		return Float.parseFloat(doc.select("td.achat-change").last().html());
		
		
	}
	@GetMapping("/CalculateInterest")
	public float CalculateInterest() throws IOException {
		final float marge= (float) 5.27;
		float i = marge + GetTMMWithJsoup();
		return i;
	}
	
	
	@GetMapping(value = "/createPdf")
    public void createPdf(Amortization amortization, HttpServletRequest request, HttpServletResponse response, Model model)
    {   	
    	
    	List<Payment> payments =amortizationService.calculatePaymentList(this.a.getStartDate(), this.a.getInitialBalance(), this.a.getDurationInMonths(), this.a.getPaymentType(), this.a.getInterestRate(), this.a.getFutureValue());
        boolean isFlag=amortizationService.createPdf(payments, context, request, response);
        
        if(isFlag){
        	String fullPath= request.getServletContext().getRealPath("/resources/reports/"+"payments"+".pdf");
        	filedownload(fullPath, response,"payments.pdf");
        }
    }
	
	
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE =4096;
		if(file.exists()){
			try{
				FileInputStream inputStream =new FileInputStream(file);
				String mimeType =context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachement; fileName="+ fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = inputStream.read(buffer))!= -1){
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
	
		
	}


	

}
