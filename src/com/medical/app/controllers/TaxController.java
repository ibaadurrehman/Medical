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

import com.medical.app.model.TaxBean;
import com.medical.app.service.TaxService;

@Controller
public class TaxController {
	@Autowired
	TaxService taxService;
	
	@RequestMapping(value="/tax/listTax", method = RequestMethod.GET)
	@ResponseBody
	public List<TaxBean> taxList(){
		
		List<TaxBean> taxList = taxService.fetchTaxList();
		
		return taxList;
	}
	
	@RequestMapping(value="/tax/addTax", method = RequestMethod.POST)
	public ResponseEntity<Void> addTax(@RequestBody TaxBean taxData){

		if(taxService.insertTax(taxData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/tax/updateTax", method = RequestMethod.POST)
	public ResponseEntity<Void> updateTax(@RequestBody TaxBean taxData){
		
		if(taxService.updateTax(taxData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/tax/deleteTax/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTax(@PathVariable("id") String id){
		
		if(taxService.deleteTax(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
