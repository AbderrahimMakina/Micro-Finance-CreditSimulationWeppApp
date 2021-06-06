package com.example.demo.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Agency;
import com.example.demo.service.AgencyService;

@Named
@ViewScoped
public class ManageDialog implements Serializable{
	
	private List<Agency> agencies;
	private Agency selectedAgency;
	
	@Autowired
	AgencyService agencyservice;
	


	public List<Agency> getAgencies() {
		return agencies;
	}



	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}
	
	public Agency getSelectedAgency() {
		return selectedAgency;
	}



	public void setSelectedAgency(Agency selectedAgency) {
		this.selectedAgency = selectedAgency;
	}


	public void openNew() {
        this.selectedAgency = new Agency();
        
    }
	
	  public void deleteAgency() {
	        this.agencies.remove(this.selectedAgency);
	        this.selectedAgency = null;
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Agency Removed"));
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-agencies");
	


}


}
