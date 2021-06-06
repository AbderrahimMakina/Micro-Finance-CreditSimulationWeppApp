package com.example.demo.service;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.entity.Amortization;
import com.example.demo.entity.Payment;

public interface AmortizationService {

	public void initializeUnknownFields(Amortization amortization);
	public List<Payment> calculatePaymentList(Date startDate, double initialBalance, int durationInMonths, int paymentType, double interestRate, double futureValue);
	public Date addOneMonth(Date date);
	public boolean createPdf(List<Payment> payments, ServletContext context, HttpServletRequest request,
			HttpServletResponse response);
}
