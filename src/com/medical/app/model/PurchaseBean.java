package com.medical.app.model;

import java.util.List;

public class PurchaseBean {
	private long purId;
	private String billNo;
	private String billDate;
	private String distId;
	private String distName;
	private List<ProductBean> prodList;
	private List<ProductBean> oldProdList;
	private double totScheme;
	private double grossAmt;
	private double totTax;
	private double roundOffAmt;
	private double netAmt;
	
	public long getPurId() {
		return purId;
	}
	public void setPurId(long purId) {
		this.purId = purId;
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
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public List<ProductBean> getProdList() {
		return prodList;
	}
	public void setProdList(List<ProductBean> prodList) {
		this.prodList = prodList;
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
	public List<ProductBean> getOldProdList() {
		return oldProdList;
	}
	public void setOldProdList(List<ProductBean> oldProdList) {
		this.oldProdList = oldProdList;
	}
}
