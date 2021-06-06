package com.example.demo.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.Agency;

public interface AgencyService {
	
	public List<Agency> findAllAgency();
	
	public Agency getById(long id);
	
	public List<Agency> getByCity (String city);
	
	public List<Agency> searchAgency (String keyword);
	
	public Agency addAgency (Agency agency);
	
	public Agency updateAgency (Agency agency);
	
	public void deleteAgency(long id);
	
	public long getNombreAgencyJPQL(String city);
	
	public Agency affecterClientAAgence(long userId, long id);
	
	boolean createPdf();
	
	boolean createExcel(List<Agency> agencies, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);

}
