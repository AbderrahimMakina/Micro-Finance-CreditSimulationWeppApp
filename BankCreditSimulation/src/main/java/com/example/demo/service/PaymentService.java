package com.example.demo.service;

import java.text.ParseException;

public interface PaymentService {
	
	public String formatCurrency(double number);
	public String formatPercent(double number);
	public double stringToPercent(String s) throws ParseException;
	public double getMonthlyInterestRate(double interestRate);
	public double pmt(double r, int nper, double pv, double fv, int type);
	public double pmt(double r, int nper, double pv, double fv);
	public double pmt(double r, int nper, double pv);
	public double fv(double r, int nper, double c, double pv, int type);
	public double fv(double r, int nper, double c, double pv);
	public double ipmt(double r, int per, int nper, double pv, double fv, int type);
	public double ppmt(double r, int per, int nper, double pv, double fv, int type);

}
