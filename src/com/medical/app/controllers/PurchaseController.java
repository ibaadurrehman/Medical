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

import com.medical.app.model.ProductBean;
import com.medical.app.model.PurchaseBean;
import com.medical.app.service.PurchaseService;

@Controller
public class PurchaseController {
	@Autowired
	PurchaseService purchaseService;
	
	@RequestMapping(value="/purchase/prodPckgList/{prodName}/{manId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodPckgList(@PathVariable("prodName") String prodName,@PathVariable("manId") String manId){
		
		List<ProductBean> prodList = purchaseService.fetchPckSizeList(prodName, manId);
		
		return prodList;
	}
	
	@RequestMapping(value="/purchase/productList/{prodId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> productList(@PathVariable("prodId") String prodId){
		
		List<ProductBean> prodList = purchaseService.fetchProductList(prodId);
		
		return prodList;
	}
	
	@RequestMapping(value="/purchase/addPurchase", method = RequestMethod.POST)
	public ResponseEntity<Void> addPurchase(@RequestBody PurchaseBean purchaseData){
		if(purchaseService.savePurchase(purchaseData, purchaseData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/purchase/submitPurchase", method = RequestMethod.POST)
	public ResponseEntity<Void> submitPurchase(@RequestBody PurchaseBean purchaseData){
		if(purchaseService.submitPurchase(purchaseData, purchaseData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/purchase/fetchPurList", method = RequestMethod.GET)
	@ResponseBody
	public List<PurchaseBean> fetchPurList(){
		List<PurchaseBean> purList = purchaseService.fetchAllBill();
		
		return purList;
	}
	
	@RequestMapping(value="/purchase/fetchPurchase/{purId}", method = RequestMethod.GET)
	@ResponseBody
	public PurchaseBean fetchPurchase(@PathVariable("purId") Long purId){
		PurchaseBean purchase = purchaseService.fetchPurchase(purId);
		
		return purchase;
	}
	
	@RequestMapping(value="/purchase/deletePurchase/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePurchase(@PathVariable("id") String id){
		
		if(purchaseService.deletePurchase(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
