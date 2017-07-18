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
import com.medical.app.service.PurchasePayService;

@Controller
public class PurchasePayController {
	@Autowired
	PurchasePayService purchasePayService;
	
	@RequestMapping(value="/transaction/fetchBillDetails/{distId}", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionBean> fetchBillDetails(@PathVariable("distId") String distId){
		List<TransactionBean> billData = purchasePayService.fetchBillDetails(distId);
		
		return billData;
	}
	
	@RequestMapping(value="/transaction/addPurTransaction", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> addPurTransaction(@RequestBody TransactionBean transData){
		if(purchasePayService.insertPurTransaction(transData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
