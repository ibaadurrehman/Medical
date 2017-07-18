package com.medical.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medical.app.model.TransactionBean;
import com.medical.app.service.SalesPayService;

@Controller
public class SalesPayController {
	@Autowired
	SalesPayService salesPayService;
	
	@RequestMapping(value="/salesTransaction/fetchSalBillDetails/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionBean> fetchBillDetails(@PathVariable("custId") String custId){
		List<TransactionBean> billData = salesPayService.fetchBillDetails(custId);
		
		return billData;
	}
	
	@RequestMapping(value="/salesTransaction/addSalTransaction", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> addSalTransaction(@RequestBody TransactionBean transData){
		if(salesPayService.insertSalTransaction(transData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/salesTransaction/addCashDeposit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> addCashDeposit(@RequestBody TransactionBean transData){
		if(salesPayService.addCashDeposit(transData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
