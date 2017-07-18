package com.medical.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medical.app.model.CustomerBean;
import com.medical.app.model.DistributorBean;
import com.medical.app.model.ProductBean;
import com.medical.app.model.ReportBean;
import com.medical.app.model.TransactionBean;
import com.medical.app.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value="/report/fetchAllStock", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> fetchAllStock(){
		List<ProductBean> prodList = reportService.fetchAllStock();
		
		return prodList;
	}
	
	@RequestMapping(value="/report/fetchStock", method = RequestMethod.POST)
	@ResponseBody
	public List<ProductBean> fetchStock(@RequestBody ProductBean prodData){
		List<ProductBean> prodList = reportService.fetchStock(prodData);
		
		return prodList;
	}
	
	@RequestMapping(value="/report/fetchAllDistBal", method = RequestMethod.POST)
	@ResponseBody
	public List<TransactionBean> fetchAllDistBal(@RequestBody ReportBean reptData){
		List<TransactionBean> transList = reportService.fetchAllDistBal(reptData);
		
		return transList;
	}
	
	@RequestMapping(value="/report/fetchDistBal", method = RequestMethod.POST)
	@ResponseBody
	public List<TransactionBean> fetchDistBal(@RequestBody ReportBean reptData){
		List<TransactionBean> transList = reportService.fetchDistBal(reptData);
		
		return transList;
	}
	
	@RequestMapping(value="/report/fetchAllCustBal", method = RequestMethod.POST)
	@ResponseBody
	public List<TransactionBean> fetchAllCustBal(@RequestBody ReportBean reptData){
		List<TransactionBean> transList = reportService.fetchAllCustBal(reptData);
		
		return transList;
	}
	
	@RequestMapping(value="/report/fetchCustBal", method = RequestMethod.POST)
	@ResponseBody
	public List<TransactionBean> fetchCustBal(@RequestBody ReportBean reptData){
		List<TransactionBean> transList = reportService.fetchCustBal(reptData);
		
		return transList;
	}
	
	@RequestMapping(value="/report/fetchAllPurReport", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchAllPurReport(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchAllPurReport(reptData);
		
		return repList;
	}
	
	@RequestMapping(value="/report/fetchDistPurReport", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchDistPurReport(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchDistPurReport(reptData);
		
		return repList;
	}
	
	@RequestMapping(value="/report/fetchAllSalesReport", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchAllSalesReport(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchAllSalesReport(reptData);
		
		return repList;
	}
	
	@RequestMapping(value="/report/fetchCustSalesReport", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchCustSalesReport(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchCustSalesReport(reptData);
		
		return repList;
	}
	
	@RequestMapping(value="/report/fetchFinYear", method = RequestMethod.GET)
	@ResponseBody
	public List<ReportBean> fetchFinYearList(){
		List<ReportBean> repBeanList = reportService.fetchFinYear();
		
		return repBeanList;
	}
	
	@RequestMapping(value="/report/fetchDistLedger", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchDistLedger(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchDistLedger(reptData);
		
		return repList;
	}
	
	@RequestMapping(value="/report/fetchCustLedger", method = RequestMethod.POST)
	@ResponseBody
	public List<ReportBean> fetchCustLedger(@RequestBody ReportBean reptData){
		List<ReportBean> repList = reportService.fetchCustLedger(reptData);
		
		return repList;
	}
}
