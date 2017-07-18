package com.medical.app.model;

import java.util.List;

public class SalesBean {
	private long salesId;
	private String billNo;
	private String billDate;
	private String custId;
	private String custName;
	private List<ProductBean> prodList;
	private List<ProductBean> oldProdList;
	private double totScheme;
	private double grossAmt;
	private double totTax;
	private double roundOffAmt;
	private double totDisc;
	private double netAmt;
	private String vatNo;
	private String custCode;
	private String address;
	private double billGrossAmt;
	private double billNetAmt;
	private String taxString;
	private String amtInWords;
	
	public long getSalesId() {
		return salesId;
	}
	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public List<ProductBean> getProdList() {
		return prodList;
	}
	public void setProdList(List<ProductBean> prodList) {
		this.prodList = prodList;
	}
	public List<ProductBean> getOldProdList() {
		return oldProdList;
	}
	public void setOldProdList(List<ProductBean> oldProdList) {
		this.oldProdList = oldProdList;
	}
	public double getTotScheme() {
		return totScheme;
	}
	public void setTotScheme(double totScheme) {
		this.totScheme = totScheme;
	}
	public double getGrossAmt() {
		return grossAmt;
	}
	public void setGrossAmt(double grossAmt) {
		this.grossAmt = grossAmt;
	}
	public double getTotTax() {
		return totTax;
	}
	public void setTotTax(double totTax) {
		this.totTax = totTax;
	}
	public double getRoundOffAmt() {
		return roundOffAmt;
	}
	public void setRoundOffAmt(double roundOffAmt) {
		this.roundOffAmt = roundOffAmt;
	}
	public double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	public String getVatNo() {
		return vatNo;
	}
	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getBillGrossAmt() {
		return billGrossAmt;
	}
	public void setBillGrossAmt(double billGrossAmt) {
		this.billGrossAmt = billGrossAmt;
	}
	public double getBillNetAmt() {
		return billNetAmt;
	}
	public void setBillNetAmt(double billNetAmt) {
		this.billNetAmt = billNetAmt;
	}
	public String getTaxString() {
		return taxString;
	}
	public void setTaxString(String taxString) {
		this.taxString = taxString;
	}
	public String getAmtInWords() {
		return amtInWords;
	}
	public void setAmtInWords(String amtInWords) {
		this.amtInWords = amtInWords;
	}
	public double getTotDisc() {
		return totDisc;
	}
	public void setTotDisc(double totDisc) {
		this.totDisc = totDisc;
	}
}
