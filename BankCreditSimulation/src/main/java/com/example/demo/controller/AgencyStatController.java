package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Agency;
import com.example.demo.service.AgencyService;

@Controller
public class AgencyStatController {
	
	@Autowired
	private AgencyService agencyService;
	
	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		
		List<Agency> agencies = agencyService.findAllAgency();
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		
		for (Agency agency: agencies){
		surveyMap.put(agency.getCity(),(int) agencyService.getNombreAgencyJPQL(agency.getCity()) );
		
		model.addAttribute("surveyMap", surveyMap);
		}
		return "agencyBarGraph";
	}

	


}
