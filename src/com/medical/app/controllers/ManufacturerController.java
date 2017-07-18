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

import com.medical.app.model.ManufacturerBean;
import com.medical.app.service.ManufactService;

@Controller
public class ManufacturerController{
	
	@Autowired
	ManufactService manufactService;
	
	@RequestMapping(value="/manufact", method = RequestMethod.GET)
	public void initiateCtrlMethod(){
		System.out.println("Controller Called....");
	}
	
	@RequestMapping(value="/manufact/listManufact", method = RequestMethod.GET)
	@ResponseBody
	public List<ManufacturerBean> manufacturerList(){
		List<ManufacturerBean> manufactList = manufactService.fetchManufactList();
		
		return manufactList;
	}
	
	@RequestMapping(value="/manufact/addManufact", method = RequestMethod.POST)
	public ResponseEntity<Void> addManufacturer(@RequestBody ManufacturerBean manufactData){
		if(manufactService.insertManufacturer(manufactData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/manufact/updateManufact", method = RequestMethod.POST)
	public ResponseEntity<Void> updateManufacturer(@RequestBody ManufacturerBean manufactData){
		if(manufactService.updateManufacturer(manufactData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/manufact/deleteManufact/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteManufacturer(@PathVariable("id") String id){
		
		if(manufactService.deleteManufacturer(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}