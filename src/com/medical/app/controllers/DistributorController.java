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

import com.medical.app.model.DistributorBean;
import com.medical.app.service.DistributorService;

@Controller
public class DistributorController {
	@Autowired
	DistributorService distributorService;
	
	@RequestMapping(value="/distributor/listDistributor", method = RequestMethod.GET)
	@ResponseBody
	public List<DistributorBean> distributorList(){
		
		List<DistributorBean> distributorList = distributorService.fetchDistributorList();
		
		return distributorList;
	}
	
	@RequestMapping(value="/distributor/addDistributor", method = RequestMethod.POST)
	public ResponseEntity<Void> addDistributor(@RequestBody DistributorBean distributorData){

		if(distributorService.insertDistributor(distributorData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/distributor/updateDistributor", method = RequestMethod.POST)
	public ResponseEntity<Void> updateDistributor(@RequestBody DistributorBean distributorData){
		
		if(distributorService.updateDistributor(distributorData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/distributor/deleteDistributor/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDistributor(@PathVariable("id") String id){
		
		if(distributorService.deleteDistributor(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
