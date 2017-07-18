package com.medical.app.model;

public class TaxBean {
	private int taxId;
	private String taxName;
	private double taxPerc;
	
	public int getTaxId() {
		return taxId;
	}
	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public double getTaxPerc() {
		return taxPerc;
	}
	public void setTaxPerc(double taxPerc) {
		this.taxPerc = taxPerc;
	}
}
