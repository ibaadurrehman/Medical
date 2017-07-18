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
import com.medical.app.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	ProductService prodService;
	
	@RequestMapping(value="/product/listProduct/{manId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodList(@PathVariable("manId") String manId){
		
		List<ProductBean> prodList = prodService.fetchProdList(manId);
		
		return prodList;
	}
	
	@RequestMapping(value="/product/listProductName/{manId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodNameList(@PathVariable("manId") String manId){
		
		List<ProductBean> prodList = prodService.fetchProdNameList(manId);
		
		return prodList;
	}
	
	@RequestMapping(value="/product/listStockProduct/{manId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodStockList(@PathVariable("manId") String manId){
		
		List<ProductBean> prodList = prodService.fetchProdStockList(manId);
		
		return prodList;
	}
	
	@RequestMapping(value="/product/listProductDtl/{prodName}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodListDtl(@PathVariable("prodName") String prodName){
		
		List<ProductBean> prodList = prodService.fetchProdDtlList(prodName);
		
		return prodList;
	}
	
	@RequestMapping(value="/product/addProduct", method = RequestMethod.POST)
	public ResponseEntity<Void> addProduct(@RequestBody ProductBean prodData){

		int result = prodService.insertProd(prodData);
		if(result > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else if(result == -1){
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}else if(result == -2){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}else if(result == -3){
			return new ResponseEntity<Void>(HttpStatus.CHECKPOINT);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/product/updateProduct", method = RequestMethod.POST)
	public ResponseEntity<Void> updateProduct(@RequestBody ProductBean prodData){
		
		if(prodService.updateProd(prodData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/product/deleteProduct/{id}/{prodDtlId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id, @PathVariable("prodDtlId") String prodDtlId){
		int result = prodService.deleteProd(id,prodDtlId);
		if(result > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
