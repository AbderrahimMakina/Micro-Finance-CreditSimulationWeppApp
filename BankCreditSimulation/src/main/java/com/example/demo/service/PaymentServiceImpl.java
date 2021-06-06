package com.example.demo.service;

import java.text.NumberFormat;
import java.text.ParseException;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private final NumberFormat nfPercent;
    private final NumberFormat nfCurrency;

    PaymentServiceImpl()
    {
        //percentage formatter
        nfPercent = NumberFormat.getPercentInstance();
        nfPercent.setMinimumFractionDigits(2);
        nfPercent.setMaximumFractionDigits(4);

        //currency formatter
        nfCurrency = NumberFormat.getCurrencyInstance();
        nfCurrency.setMinimumFractionDigits(2);
        nfCurrency.setMaximumFractionDigits(2);
    } 
    
	@Override
	public String formatCurrency(double number) {
		return nfCurrency.format(number);
	}

	@Override
	public String formatPercent(double number) {
		return nfPercent.format(number);
	}

	@Override
	public double stringToPercent(String s) throws ParseException {
		return nfPercent.parse(s).doubleValue();
	}

	@Override
	public double getMonthlyInterestRate(double interestRate) {
		return interestRate / 100 / 12;
	}

	@Override
	public double pmt(double r, int nper, double pv, double fv, int type) {
		if (r == 0) {
            return -(pv + fv) / nper;
        }

        double pmt = r / (Math.pow(1 + r, nper) - 1) * -(pv * Math.pow(1 + r, nper) + fv);

        if (type == 1) {
            pmt /= (1 + r);
        }

        return pmt;
	}

	@Override
	public double pmt(double r, int nper, double pv, double fv) {
		return pmt(r, nper, pv, fv, 0);
	}

	@Override
	public double pmt(double r, int nper, double pv) {
		return pmt(r, nper, pv, 0, 0);
	}

	@Override
	public double fv(double r, int nper, double c, double pv, int type) {
		if (r == 0) return pv;

        
        //we are going in reverse, we multiply by 1 plus interest rate.
        if (type == 1) {
            c *= (1 + r);
        }

        double fv = -((Math.pow(1 + r, nper) - 1) / r * c + pv * Math.pow(1 + r, nper));

        
        return fv;
	}

	@Override
	public double fv(double r, int nper, double c, double pv) {
		 return fv(r, nper, c, pv, 0);
	}

	@Override
	public double ipmt(double r, int per, int nper, double pv, double fv, int type) {

        double ipmt = fv(r, per - 1, pmt(r, nper, pv, fv, type), pv, type) * r;

        if (type == 1) {
            ipmt /= (1 + r);
        }

        return ipmt;
	}

	@Override
	public double ppmt(double r, int per, int nper, double pv, double fv, int type) {
		
		// Calculated payment per period - interest portion of that period.
        return pmt(r, nper, pv, fv, type) - ipmt(r, per, nper, pv, fv, type);
	}

}
