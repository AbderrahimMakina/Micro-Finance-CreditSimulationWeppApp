package com.example.demo.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import com.example.demo.entity.Amortization;
import com.example.demo.entity.Payment;
import com.example.demo.service.AmortizationService;



@Scope(value = "session")
@Controller(value = "SimulationController")
@ELBeanName(value = "SimulationController")
@Join(path = "/", to = "/login.jsf")
public class SimulationController {
	
	private static final Logger L=LogManager.getLogger(SimulationController.class);
	 
	private String startDate;
    private double initialBalance;
    private double interestRate;
    private int durationInMonths;
    private double futureValue;
    private int paymentType;
    private double monthlyPayment;
    private List<Payment> paymentList = new ArrayList<Payment>();
    private Amortization amortization = new Amortization();
    
    @Autowired
    private AmortizationService amortizationService;
    
    
    public String doLogout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";

		}
    
    
    public String doSimulate() throws ParseException{
    	amortization.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(startDate));
		amortization.setInitialBalance(initialBalance);
		amortization.setDurationInMonths(durationInMonths);
		amortization.setFutureValue(futureValue);
		amortization.setInterestRate(interestRate);
		amortization.setPaymentType(paymentType);

		amortizationService.initializeUnknownFields(amortization);
    	return "/simulation/schedule.xhtml?faces-redirect=true";
    }
    
    
    
    
	public Amortization getAmortization() {
		return amortization;
	}


	public void setAmortization(Amortization amortization) {
		this.amortization = amortization;
	}


	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public double getInitialBalance() {
		return initialBalance;
	}
	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public int getDurationInMonths() {
		return durationInMonths;
	}
	public void setDurationInMonths(int durationInMonths) {
		this.durationInMonths = durationInMonths;
	}
	public double getFutureValue() {
		return futureValue;
	}
	public void setFutureValue(double futureValue) {
		this.futureValue = futureValue;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public double getMonthlyPayment() {
		return monthlyPayment;
	}
	public void setMonthlyPayment(double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}
	public List<Payment> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = paymentList;
	}
	public static Logger getL() {
		return L;
	}
    
    
    
    

}
