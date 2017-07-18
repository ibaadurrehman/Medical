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

import com.medical.app.model.CustomerBean;
import com.medical.app.service.CustomerService;

@Controller
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/customer/listCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerBean> customerList(){
		
		List<CustomerBean> customerList = customerService.fetchCustomerList();
		
		return customerList;
	}
	
	@RequestMapping(value="/customer/addCustomer", method = RequestMethod.POST)
	public ResponseEntity<Void> addCustomer(@RequestBody CustomerBean customerData){

		if(customerService.insertCustomer(customerData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/customer/updateCustomer", method = RequestMethod.POST)
	public ResponseEntity<Void> updateCustomer(@RequestBody CustomerBean customerData){
		
		if(customerService.updateCustomer(customerData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/customer/deleteCustomer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String id){
		
		if(customerService.deleteCustomer(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/customer/getCustomerId", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomerId(){

		int custId = customerService.getCustId();
		String custCode = "C00" + custId;
		System.out.println("CustCode="+custCode);
		
		return custCode;
	}
}
