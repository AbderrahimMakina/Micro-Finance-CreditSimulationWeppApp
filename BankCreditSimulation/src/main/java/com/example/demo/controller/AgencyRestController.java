package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Agency;
import com.example.demo.service.AgencyService;

@RestController
@RequestMapping("/agency")

public class AgencyRestController {
	
	@Autowired
	AgencyService agencyservice;
	
	@Autowired
	private ServletContext context;
	
	@GetMapping("/show-all-agengies")
	public List <Agency> retrieveAllAgency(@Param("keyword") String keyword){
		List <Agency> listAgencies = agencyservice.searchAgency(keyword);
		return listAgencies;
	}
	
	@GetMapping("/show-agency/{id}")

	public Agency getAgency(@PathVariable("id") long id) {
		Agency agency = agencyservice.getById(id);
		return agency;
	}
	
	@GetMapping("/show-agengies/{city}")

	public List<Agency> getAgenciesbyCity(@PathVariable("city") String city) {
		List<Agency> list = agencyservice.getByCity(city);
		return list;
	}
	
	
	
	@PostMapping("/save-agency")
	
	public Agency save(@RequestBody Agency ag){
		Agency agency = agencyservice.addAgency(ag);
		return agency;
	}
	
	@PutMapping("/update-agency")
	
	public Agency updateAgency(@RequestBody Agency agency){
		return agencyservice.updateAgency(agency);
	}
	
	@DeleteMapping("/remove-agency/{id}")
	
	public void removeAgency(@PathVariable("id") long id){
		agencyservice.deleteAgency(id);
	}
	
    @GetMapping("/Nbre-agency/{city}")
	
	public long getNombreAgency(@PathVariable("city") String city){
		return agencyservice.getNombreAgencyJPQL(city);
	}
    
    
    @PutMapping("/affecterClientAAgency/{userId}/{id}")
	public Agency affecterClientAAgency (@PathVariable("userId") long userId, @PathVariable("id") long id){
		return agencyservice.affecterClientAAgence(userId, id);
	}
    
   /* @GetMapping("/createPdf")
	public void createPdf (HttpServletRequest request, HttpServletResponse response){
		List <Agency> agencies = agencyservice.findAllAgency();
		boolean isFlag = agencyservice.createPdf(agencies, context, request, response);
		 if(isFlag){
	        	String fullPath= request.getServletContext().getRealPath("/resources/reports/"+"agencies"+".pdf");
	        	filedownload(fullPath, response,"agencies.pdf");
	        }
	}*/
    
    /*
    @GetMapping("/createExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response){
		List <Agency> agencies = agencyservice.findAllAgency();
		boolean isFlag = agencyservice.createExcel(agencies, context, request, response);
		if(isFlag){
        	String fullPath= request.getServletContext().getRealPath("/resources/reports/"+"agencies"+".xls");
        	filedownload(fullPath, response,"agencies.xls");
        }
	}*/
    
/*private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		
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

}*/

	@GetMapping("/displayBarGraph")
	public Map<String, Integer> barGraph() {
	List<Agency> agencies = agencyservice.findAllAgency();
	Map<String, Integer> surveyMap = new LinkedHashMap<>();
	
	for (Agency agency: agencies){
	surveyMap.put(agency.getCity(),(int) agencyservice.getNombreAgencyJPQL(agency.getCity()) );
	}
	return surveyMap;
}
	@GetMapping("/displayUserBarGraph")
	public Map<String, Integer> userBarGraph() {
	List<Agency> agencies = agencyservice.findAllAgency();
	Map<String, Integer> surveyMap = new LinkedHashMap<>();
	
	for (Agency agency: agencies){
	surveyMap.put(agency.getAgencyName(),(int) agency.getUser().size());
	}
	return surveyMap;
}

}
