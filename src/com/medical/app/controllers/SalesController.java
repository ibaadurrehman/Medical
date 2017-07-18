package com.medical.app.controllers;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterResolution;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.medical.app.configurations.NumberToWords;
import com.medical.app.model.ProductBean;
import com.medical.app.model.SalesBean;
import com.medical.app.service.SalesService;

@Controller
 class SalesController {

	@Autowired
	SalesService salesService;
	
	@RequestMapping(value="/sales/prodPckgList/{prodName}/{manId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> prodPckgList(@PathVariable("prodName") String prodName,@PathVariable("manId") String manId){
		
		List<ProductBean> prodList = salesService.fetchPckSizeList(prodName, manId);
		
		return prodList;
	}
	
	@RequestMapping(value="/sales/getNextBillNo", method = RequestMethod.GET)
	@ResponseBody
	public String nextBillNo(){
		String billNo = salesService.getNextBillNo();
		
		return billNo;
	}
	
	@RequestMapping(value="/sales/productList/{prodId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductBean> productList(@PathVariable("prodId") String prodId){
		
		List<ProductBean> prodList = salesService.fetchProductList(prodId);
		
		return prodList;
	}
	
	@RequestMapping(value="/sales/addSales", method = RequestMethod.POST)
	public ResponseEntity<Void> addSales(@RequestBody SalesBean salesData){
		if(salesService.saveSales(salesData, salesData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/sales/stockMinus", method = RequestMethod.POST)
	public ResponseEntity<Void> stockMinus(@RequestBody ProductBean prodData){
		if(salesService.stockMinus(prodData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/sales/submitSales", method = RequestMethod.POST)
	public ResponseEntity<Void> submitSales(@RequestBody SalesBean salesData){
		if(salesService.submitSales(salesData, salesData) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/sales/printBill", method = RequestMethod.POST)
	public ResponseEntity<Void> printBill(@RequestBody SalesBean salesData){
		System.out.println("print method called");
		DecimalFormat df = new DecimalFormat("#.00");
		NumberToWords numToWords = new NumberToWords();
		StringBuilder strNum = new StringBuilder();
		StringBuilder strTax = new StringBuilder();
		strNum.append("Total  :Rs. ");
		strNum.append(numToWords.convert(Double.valueOf(salesData.getNetAmt()).intValue()));
		strNum.append(" only");
		
		List<ProductBean> prodList = salesData.getProdList();
		Iterator<ProductBean> itrProd = prodList.iterator();
		Map<Double,ArrayList<Double>> taxPerc = new HashMap<Double, ArrayList<Double>>();
	    int count = 1;
	    while(itrProd.hasNext()){
	    	ProductBean prodForm = itrProd.next();
	    	prodList.get(count-1).setCount(count);
	    	prodList.get(count-1).setTotDisc(new Double(df.format(prodList.get(count-1).getDisc1Val() + prodList.get(count-1).getDisc2Val())));
	    	prodList.get(count-1).setGrossAmt(new Double(df.format(prodList.get(count-1).getQty() * prodList.get(count-1).getRate())));
	    	
	    	if(prodList.get(count-1).getProdName() != null && !prodList.get(count-1).getProdName().equals("")){
	    		if(prodList.get(count-1).getProdName().length() > 21){
	    			prodList.get(count-1).setProdName(prodList.get(count-1).getProdName().substring(0, 22));
	    		}
	    	}
	    	
	    	if(taxPerc.containsKey(prodForm.getTaxPerc())){
	    		ArrayList<Double> tempList = taxPerc.get(prodForm.getTaxPerc());
	    		double totTax = tempList.get(0) + prodForm.getTaxVal();
	    		double totAmt = tempList.get(1) + prodForm.getTotAmt();
	    		taxPerc.remove(prodForm.getTaxPerc());
	    		tempList.add(0,totTax);
	    		tempList.add(1,totAmt);
	    		taxPerc.put(prodForm.getTaxPerc(), tempList);
	    		System.out.println("count="+count+"taxPerc="+prodForm.getTaxPerc()+"totAmt="+totAmt);
	    	}else{
	    		ArrayList<Double> tempList = new ArrayList<Double>();
	    		tempList.add(0,prodForm.getTaxVal());
	    		tempList.add(1,prodForm.getTotAmt());
	    		taxPerc.put(prodForm.getTaxPerc(),tempList);
	    		System.out.println("count new Added="+count+"taxPerc="+prodForm.getTaxPerc()+"taxVal="+prodForm.getTaxVal());
	    	}
	    	count++;
	    }
	    
	    try{
		    Iterator taxEntries = taxPerc.entrySet().iterator();
		    int taxCount=0;
		    while(taxEntries.hasNext()){
			    Entry thisEntry = (Entry) taxEntries.next();
			    ArrayList<Double> taxList = (ArrayList<Double>)thisEntry.getValue();
			    if(taxCount > 0){
			    	strTax.append(" AND ");
			    }
			    strTax.append(df.format(thisEntry.getKey()));
		    	strTax.append("% Vat on Rs.");
		    	strTax.append(df.format(taxList.get(1)));
		    	strTax.append("=Rs.");
		    	strTax.append(df.format(taxList.get(0)));
		    	taxCount++;
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    
	    for(int i = prodList.size()+1; i <= 15; i++ ){
	    	prodList.add(new ProductBean());
	    }
		
	    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(prodList);

	      Map parameters = new HashMap();
	      
	      double grossAmtScheme = salesData.getGrossAmt() - salesData.getTotScheme();
	      
	      parameters.put("address", salesData.getAddress());
	      parameters.put("billNo", salesData.getBillNo());
	      parameters.put("billDate", salesData.getBillDate());
	      parameters.put("custCode", salesData.getCustCode());
	      parameters.put("custName", salesData.getCustName());
	      parameters.put("custVatNo", salesData.getVatNo());
	      parameters.put("grossAmt", df.format(salesData.getGrossAmt()));
	      parameters.put("netAmt", df.format(salesData.getNetAmt()));
	      parameters.put("roundOffAmt", df.format(salesData.getRoundOffAmt()));
	      parameters.put("totScheme", df.format(salesData.getTotScheme()));
	      parameters.put("totTax", df.format(salesData.getTotTax()));
	      parameters.put("taxString", strTax.toString());
	      parameters.put("amtInWords", strNum.toString().toUpperCase());
	      parameters.put("grossAmtSch", df.format(grossAmtScheme));
	      
	      try {
	    	  //String printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, beanColDataSource);
	    	  /*File file = new File("");
	    	  System.out.println(file.getAbsolutePath()+ "\\webapps\\Medical\\collection\\reports\\salesBill.jasper");*/
	    	  //if(new File(file.getAbsolutePath() + "\\webapps\\Medical\\collection\\reports\\salesBill.jasper").exists()){
	    		  /*System.out.println("file exist" + new File("D:\\Reports\\reports\\salesBill.jasper").exists());
	    		  FileInputStream fileInput = new FileInputStream("D:\\Reports\\reports\\salesBill.jasper");*/
	    		//String fileName = "D://Reports/final Reports/salesBill.jasper";
	    	  String fileName = "C://Program Files/Apache Software Foundation/Tomcat 7.0_Apache_Tomcat_7/webapps/Medical/collection/reports/salesBill.jasper";
	    		  
	    		  System.out.println("exist"+new File(fileName).exists());
		    	  //JasperReport report=(JasperReport)JRLoader.loadObject(fileInput);
		    	  //System.out.println(""+report.getName());
	    		  System.out.println("before");
		    	  JasperPrint printFileName = JasperFillManager.fillReport(fileName,parameters,beanColDataSource);
		    	  System.out.println("after print"+printFileName);
		    	  JasperExportManager.exportReportToPdfFile(printFileName,"D://SalesBill.pdf");
		    	  //JasperExportManager.exportReportToXmlFile(printFileName,false);
		    	  System.out.println("after export");
		    	  //JasperViewer.viewReport(printFileName, true);
		    	  //JasperPrintManager.printReport(printFileName, true);
		    	  JRPrintServiceExporter jrPrint = new JRPrintServiceExporter();
		    	  PrintServiceAttributeSet printServ = new HashPrintServiceAttributeSet();
		    	  //printServ.add(new PrinterResolution(240, 144, ResolutionSyntax.DPI));
		    	  
		    	  PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
		    	  attrs.add(new PrinterResolution(240, 144, ResolutionSyntax.DPI));
		    	  
		    	  printServ.add(new PrinterName("EPSON LX-300+ /II", null));
		    	  jrPrint.setParameter(JRExporterParameter.JASPER_PRINT, printFileName);
		    	  jrPrint.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServ);
		    	  jrPrint.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
		    	  jrPrint.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, attrs);
		    	  
		    	  jrPrint.exportReport();
	    	  //}
	         
	        /* if(printFileName != null){
	        	 JasperExportManager.exportReportToPdfFile(printFileName,"C://SalesBill.pdf");
	        	 JasperPrintManager.printReport(printFileName, true);
	             //JasperExportManager.exportReportToHtmlFile(printFileName, "C://SalesBill.htm");
	         }*/
	      } catch (Exception e) {
	    	  System.out.println("error");
	         e.printStackTrace();
	      }

		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/sales/fetchSalesList", method = RequestMethod.GET)
	@ResponseBody
	public List<SalesBean> fetchSalesList(){
		List<SalesBean> salesList = salesService.fetchAllBill();
		
		return salesList;
	}
	
	@RequestMapping(value="/sales/fetchSales/{salesId}", method = RequestMethod.GET)
	@ResponseBody
	public SalesBean fetchSales(@PathVariable("salesId") Long salesId){
		SalesBean sales = salesService.fetchSales(salesId);
		
		return sales;
	}
	
	@RequestMapping(value="/sales/deleteSales/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteSales(@PathVariable("id") String id){
		
		if(salesService.deleteSales(id) > 0){
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
	}
}
