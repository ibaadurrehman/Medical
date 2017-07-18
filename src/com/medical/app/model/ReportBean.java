package com.medical.app.model;

import java.util.List;

public class ReportBean {
	private String distId;
	private String distName;
	private String custId;
	private String custName;
	private String fromDate;
	private String toDate;
	private int count;
	private String billNo;
	private String billDt;
	private double billAmt;
	private double adjAmt;
	private double deposit;
	private double payment;
	private double balance;
	private String cheqNo;
	private String cheqDt;
	private String bankName;
	private String name;
	private String finYear;
	
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillDt() {
		return billDt;
	}
	public void setBillDt(String billDt) {
		this.billDt = billDt;
	}
	public double getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(double billAmt) {
		this.billAmt = billAmt;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getCheqNo() {
		return cheqNo;
	}
	public void setCheqNo(String cheqNo) {
		this.cheqNo = cheqNo;
	}
	public String getCheqDt() {
		return cheqDt;
	}
	public void setCheqDt(String cheqDt) {
		this.cheqDt = cheqDt;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getAdjAmt() {
		return adjAmt;
	}
	public void setAdjAmt(double adjAmt) {
		this.adjAmt = adjAmt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
}
