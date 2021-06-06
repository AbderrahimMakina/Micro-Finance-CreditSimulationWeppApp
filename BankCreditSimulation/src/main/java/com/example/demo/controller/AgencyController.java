package com.example.demo.controller;



import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.Agency;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.AgencyService;
import com.example.demo.service.UserService;




@Named
@RequestScoped
@Scope(value = "session")
@Controller(value = "AgencyController")
@ELBeanName(value = "AgencyController")
@Join(path = "/", to = "/login.jsf")
public class AgencyController {
	
	private static final Logger L=LogManager.getLogger(AgencyController.class);

	
	private String login;
	private String password; 
	
	private long id;
	private String agencyName;
	private String city;
	private String adress;
	private long contact;
	private String email;
	private Agency agency;
	
	private List<Agency> agencies;
	private long agencyIdToBeUpdated; // Ajouter getter et setter

	private User authenticatedUser;

	@Enumerated(EnumType.STRING) private Role role;
	
	private Boolean loggedIn;
	
	@Autowired
	AgencyService agencyService;
	UserService userService;
	

	
	public Role[] getRoles() { return Role.values(); }

	
	public String doLogin() {

		String navigateTo = "null";

		authenticatedUser=userService.authenticate(login, password);
		 if (authenticatedUser != null && authenticatedUser.getRole() ==
		Role.ADMINISTRATEUR) {
		navigateTo = "/admin/welcome.xhtml?faces-redirect=true";

		loggedIn = true; }

		else {

		FacesMessage facesMessage =

		new FacesMessage("Login Failed: please check your username/password and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);

		}

		return navigateTo;

		}
	
	public String doLogout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";

		}
	
	
	 public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
	        FacesContext.getCurrentInstance().
	                addMessage(null, new FacesMessage(severity, summary, detail));
	 }
	/*****crud*****/
	public void addAgency() {
		
		Agency agency =new Agency(id,agencyName,city, adress,contact,email);
		
		agencyService.addAgency(agency);
		addMessage(FacesMessage.SEVERITY_INFO, "Success", "Agency saved successfuly");

		}
	
	
	
	public void test() {
		Agency agency =new Agency(id,agencyName,city, adress,contact,email);

		L.info("zzazaezaeazzaa"+agency);

		}
	public List<Agency> getAgencies() {
		
		agencies = agencyService.findAllAgency();
		return agencies;
		}
	
	public void removeAgency(long id)
	{
		L.info("remove agency " +id);
	agencyService.deleteAgency(id);
	
	}
	

	public void displayAgency(Agency agency)
	{
	
	this.setAgencyName(agency.getAgencyName());
	this.setCity(agency.getCity());
	this.setAdress(agency.getAdress());
	this.setContact(agency.getContact());
	this.setEmail(agency.getEmail());
	this.setAgencyIdToBeUpdated(agency.getId());
	}
	
	public String updateAgency(long id)
	
	{ 
		Agency agency = agencyService.getById(id);
		displayAgency(agency);
		
		return "/admin/update_agency.xhtml?faces-redirect=true";
	}	
	
	
   public void SaveChangesAgency()
	
	{ agencyService.addAgency(new Agency(agencyIdToBeUpdated, agencyName,
			city, adress, contact, email));
	addMessage(FacesMessage.SEVERITY_INFO, "Success", "Agency updated successfuly");
	}	
   
   /*
   public void createPdf (){
		List <Agency> agencies = agencyService.findAllAgency();
		//boolean isFlag = agencyService.createPdf(agencies);
		 if(isFlag){
	        	String fullPath="C:/reports/agencies.pdf";
	        	filedownload(fullPath, response,"agencies.pdf");
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

}	*/
   
   private StreamedContent file;
   
   public StreamedContent getFile() {
	return file;
}


public void setFile(StreamedContent file) {
	this.file = file;
}


public void fileDownloadView() {
	  
	//agencyService.createPdf();
       file = DefaultStreamedContent.builder()
               .name("downloaded_boromir.jpg")
               .contentType("image/jpg")
               .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("C:/Users/MonPc/Documents/workspace-sts-3.8.4.RELEASE/piProjet/src/main/resources/reports/image.jpg"))
               .build();
   }

	

	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
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


	public Agency getAgency() {
		return agency;
	}


	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	public long getAgencyIdToBeUpdated() {
		return agencyIdToBeUpdated;
	}


	public void setAgencyIdToBeUpdated(long agencyIdToBeUpdated) {
		this.agencyIdToBeUpdated = agencyIdToBeUpdated;
	}


	public User getAuthenticatedUser() {
		return authenticatedUser;
	}


	public void setAuthenticatedUser(User authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Boolean getLoggedIn() {
		return loggedIn;
	}


	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}


	public AgencyService getAgencyService() {
		return agencyService;
	}


	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}


	public static Logger getL() {
		return L;
	}


	
	
	
	
}
