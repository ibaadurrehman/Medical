package com.medical.app.model;

import java.util.List;

public class TransactionBean {
	private long transId;
	private int transType;
	private int transPayType;
	private double transAmt;
	private double balAmt;
	private double billAmt;
	private String bankId;
	private String selfBankId;
	private String cheqNo;
	private String cheqDate;
	private long purId;
	private long salesId;
	private String distId;
	private String custId;
	private String remarks;
	private double amtAdj;
	private String billNo;
	private String billDate;
	private String transDate;
	private double amtPaid;
	private List<TransactionBean> billList;
	private double balance;
	private double openingBal;
	private double billing;
	private double payment;
	private String name;
	private int count;
	
	public long getTransId() {
		return transId;
	}
	public void setTransId(long transId) {
		this.transId = transId;
	}
	public int getTransType() {
		return transType;
	}
	public void setTransType(int transType) {
		this.transType = transType;
	}
	public int getTransPayType() {
		return transPayType;
	}
	public void setTransPayType(int transPayType) {
		this.transPayType = transPayType;
	}
	public double getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(double transAmt) {
		this.transAmt = transAmt;
	}
	public double getBalAmt() {
		return balAmt;
	}
	public void setBalAmt(double balAmt) {
		this.balAmt = balAmt;
	}
	public double getBillAmt() {
		return billAmt;
	}
	public void setBillAmt(double billAmt) {
		this.billAmt = billAmt;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCheqNo() {
		return cheqNo;
	}
	public void setCheqNo(String cheqNo) {
		this.cheqNo = cheqNo;
	}
	public String getCheqDate() {
		return cheqDate;
	}
	public void setCheqDate(String cheqDate) {
		this.cheqDate = cheqDate;
	}
	public long getPurId() {
		return purId;
	}
	public void setPurId(long purId) {
		this.purId = purId;
	}
	public long getSalesId() {
		return salesId;
	}
	public void setSalesId(long salesId) {
		this.salesId = salesId;
	}
	public String getDistId() {
		return distId;
	}
	public void setDistId(String distId) {
		this.distId = distId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public double getAmtAdj() {
		return amtAdj;
	}
	public void setAmtAdj(double amtAdj) {
		this.amtAdj = amtAdj;
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
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public double getAmtPaid() {
		return amtPaid;
	}
	public void setAmtPaid(double amtPaid) {
		this.amtPaid = amtPaid;
	}
	public List<TransactionBean> getBillList() {
		return billList;
	}
	public void setBillList(List<TransactionBean> billList) {
		this.billList = billList;
	}
	public String getSelfBankId() {
		return selfBankId;
	}
	public void setSelfBankId(String selfBankId) {
		this.selfBankId = selfBankId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getOpeningBal() {
		return openingBal;
	}
	public void setOpeningBal(double openingBal) {
		this.openingBal = openingBal;
	}
	public double getBilling() {
		return billing;
	}
	public void setBilling(double billing) {
		this.billing = billing;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
