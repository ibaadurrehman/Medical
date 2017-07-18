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

import com.medical.app.model.BankBean;
import com.medical.app.service.BankService;

@Controller
public class BankController {
	@Autowired
	BankService bankService;
	
	@RequestMapping(value="/bank/listBank", method = RequestMethod.GET)
	@ResponseBody
	public List<BankBean> bankList(){
		
		List<BankBean> bankList = bankService.fetchBankList();
		
		return bankList;
	}
	
	@RequestMapping(value="/bank/selfListBank", method = RequestMethod.GET)
	@ResponseBody
	public List<BankBean> selfBankList(){
		
		List<BankBean> bankList = bankService.fetchSelfBankList();
		
		return bankList;
	}
	
	@RequestMapping(value="/bank/statement", method = RequestMethod.POST)
	@ResponseBody
	public List<BankBean> getStatement(@RequestBody BankBean bankData){
		List<BankBean> bankList = bankService.getStatement(bankData);
		
		return bankList;
	}
	
	@RequestMapping(value="/bank/partyListBank", method = RequestMethod.GET)
	@ResponseBody
	public List<BankBean> selfPartyist(){
		
		List<BankBean> bankList = bankService.fetchPartyBankList();
		
		return bankList;
	}
	
	@RequestMapping(value="/bank/addBank", method = RequestMethod.POST)
	public ResponseEntity<Void> addBank(@RequestBody BankBean bankData){

		if(bankService.insertBank(bankData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/bank/updateBank", method = RequestMethod.POST)
	public ResponseEntity<Void> updateBank(@RequestBody BankBean bankData){
		
		if(bankService.updateBank(bankData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/bank/deleteBank/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBank(@PathVariable("id") String id){
		
		if(bankService.deleteBank(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/bank/getBankId", method = RequestMethod.GET)
	@ResponseBody
	public String getBankId(){

		int bankId = bankService.getBankId();
		String bankCode = "B00" + bankId;
		System.out.println("BankCode="+bankCode);
		
		return bankCode;
	}
}
