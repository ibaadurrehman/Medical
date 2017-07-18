'use strict';

homeApp.controller('salesController', ['$q','$scope','customerService','manufactService','taxService', 'productService','salesService',  
    function ($q, $scope, customerService, manufactService, taxService, productService, salesService) {
	var self = this;
    self.user={billNo:'',billDate:'',custId:'',custName:'',totDisc:0,grossAmtSch:0,address:'',custCode:'',vatNo:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    self.salesList={billNo:'',billDate:'',custId:'',custName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    self.users=[];
    self.custList = [];
    self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
    self.manufactData={manId:'',manName:'',address:'',phoneNo:'',custCare:''};;
    self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
    self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',rate:''
    				,scheme:'',grossAmt:0,schemeVal:'',totDisc:0,disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:'',qtyMinus:0};
    self.taxData={taxId:'',taxName:'',taxPerc:''};
    self.maufactList=[];
    self.taxList=[];
    self.prodNameList=[];
    self.prodList=[];
    self.prodPckgList=[];
    self.oldProdList= null;
    
    self.updateRow = null;
    var upTotAmt = 0;
	var upTotTax = 0;
	var upScheme = 0;
	var tableData = null;
	var upSchemeVal = 0;
	var today = new Date();
    var date = today.getDate()+'/'+(today.getMonth()+1)+'/'+today.getFullYear();
    self.user.billDate = date;
    
    var dataGrid = [];
    var data = {"data": []};
    
    $scope.gridOptions = { enableSorting: false,
    					   enableFiltering:false,	
    					   data: dataGrid,
    					   multiSelect: false,
    					   enableColumnMenus: false,
    					   onRegisterApi: function(gridApi) {
    						      console.log('Loaded Grid API 1');
    						      $scope.gridApi1 = gridApi;
    						}
    					};
    
    $scope.gridOptions2 = { enableSorting: false,
    						enableFiltering:false,
			   				data: dataGrid,
			   				multiSelect: false,
	    					enableColumnMenus: false,
			   				onRegisterApi: function(gridApi) {
			   			      console.log('Loaded Grid API 2');
			   			      $scope.gridApi2 = gridApi;
			   			    }
						};
    
    $scope.saveState = function() {
        $scope.state1 = $scope.gridApi1.saveState.save();
        $scope.state2 = $scope.gridApi2.saveState.save();
    };
    
    $scope.restoreState = function() {
        $scope.gridApi1.saveState.restore(null, $scope1.state);
        $scope.gridApi2.saveState.restore(null, $scope2.state);
    };
    
    $scope.gridOptions.columnDefs = [
		 { name: 'prodDtlId', enableCellEdit: false, visible: false },
		 { name: 'prodId', enableCellEdit: false, visible: false},
		 { name: 'prodCode', displayName:'Product Code', enableCellEdit: false, width: '20%' },
		 { name: 'prodName', displayName: 'Product Name', editDropdownValueLabel: 'prodName',
			 editableCellTemplate: 'ui-grid/dropdownEditor', width: '25%', editDropdownOptionsFunction:''},
		 { name: 'pckSize', displayName: 'Packaging Size', editDropdownValueLabel: 'pckSize',
				 editableCellTemplate: 'ui-grid/dropdownEditor', width: '20%', editDropdownOptionsFunction:''},
		 { name: 'qty', displayName:'Quantity', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'stock', displayName:'Stock', eznableCellEdit: false, width: '20%' },
		 { name: 'oldMrp', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'oldRate', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'mrp', displayName:'MRP', enableCellEditOnFocus: true, width: '12%' },
		 { name: 'rate', displayName:'Rate', enableCellEditOnFocus: true, width: '12%' },
		 { name: 'scheme', displayName:'Scheme', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'schemeVal', displayName:'Scheme Value', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'disc1', displayName:'Disc1%', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'disc1Val', displayName:'Disc1 Value', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'disc2', displayName:'Disc2%', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'disc2Val', displayName:'Disc2 Val', enableCellEditOnFocus: true, width: '20%' },
		 { name: 'totDisc', displayName:'Tot Disc', width: '20%', visible: false},
		 { name: 'manId', displayName:'Manufacturer Id', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'manName', displayName:'Manufacturer Name', enableCellEdit: false, width: '20%' },
		 { name: 'taxId', displayName:'Tax Id', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'grossAmt', displayName:'Gross Amt', width: '20%', visible: false },
		 { name: 'newRate', displayName:'new rate', width: '20%', visible: false },
		 { name: 'taxPerc', displayName:'Tax%', enableCellEdit: false, width: '20%' },
		 { name: 'taxVal', displayName:'Tax Value', enableCellEdit: false, width: '20%' },
		 { name: 'totAmt', displayName:'Gross Amount', enableCellEdit: false, width: '20%' }
	];
    
    $scope.gridOptions2.columnDefs = [
		 { name: 'salesId', enableCellEdit: false, visible: false },
		 { name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '20%' },
		 { name: 'billDate', displayName: 'Bill Date', enableCellEdit: false, width: '20%'},
		 { name: 'custId', enableCellEdit: false, visible: false },
		 { name: 'custName', displayName:'Customer', enableCellEdit: false, width: '20%' },
		 { name: 'grossAmt', displayName:'Gross Amount', enableCellEdit: false, width: '20%' },
		 { name: 'totTax', displayName:'Total Vat', enableCellEdit: false, width: '20%' },
		 { name: 'roundOffAmt', displayName:'Round Off Amount', enableCellEdit: false, width: '20%' },
		 { name: 'netAmt', displayName:'Net Amount', enableCellEdit: false, width: '20%' }
	];
    
    self.updateSalesProd = function(){
    	var rowCol = $scope.gridApi1.cellNav.getFocusedCell();
    	tableData = rowCol.row.entity;
    	$q.all([
					self.fetchProdNameList(rowCol.row.entity.manId),
					self.fetchProdPckgList(rowCol.row.entity.prodName, rowCol.row.entity.manId)
    	        ]).then(function(data){
				    	self.productData = rowCol.row.entity;
				    	self.manufactData.manId = rowCol.row.entity.manId;
	    	        	self.manufactData.manName = rowCol.row.entity.manName;
	    	        	self.pckSizeData.prodId = rowCol.row.entity.prodId;
	    	        	self.pckSizeData.prodCode = rowCol.row.entity.prodCode;
	    	        	self.pckSizeData.pckSize = rowCol.row.entity.pckSize;
				    	self.prodData = rowCol.row.entity;
				    	self.updateRow = rowCol.row.entity;
				    	self.taxData = rowCol.row.entity;
				    	upTotAmt = rowCol.row.entity.totAmt;
				   	 	upTotTax = rowCol.row.entity.taxVal;
				   	 	upScheme = rowCol.row.entity.scheme;
				   	 	upSchemeVal = rowCol.row.entity.schemeVal;
    	        });
    };
    
    self.deleteSalesProd = function(){
    	var rowCol = null;
    	rowCol = $scope.gridApi1.cellNav.getFocusedCell();
    	var rowData = $scope.gridApi1.selection.getSelectedRows();
    	
    	if(rowData != null && rowData.length > 0){
    		var index = $scope.gridOptions.data.indexOf(rowCol.row.entity);
	    	self.user.grossAmt = (parseFloat(self.user.grossAmt) - parseFloat(rowCol.row.entity.totAmt)).toFixed(2);
		   	self.user.totTax = (parseFloat(self.user.totTax) - parseFloat(rowCol.row.entity.taxVal)).toFixed(2);
		   	if(rowCol.row.entity.scheme > 0 && rowCol.row.entity.scheme == 0.5){
		   		self.user.totScheme = (parseFloat(self.user.totScheme) - parseFloat(rowCol.row.entity.schemeVal)).toFixed(2);
		   	}
		   	self.user.netAmt = 0;
		   	self.user.netAmt = (parseFloat(self.user.netAmt) + parseFloat(self.user.grossAmt) + parseFloat(self.user.totTax)).toFixed(2);
		   	self.user.tempNetAmt = self.user.netAmt;
		   	self.user.roundOffAmt = 0;
		   	self.user.grossAmtSch = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totScheme)).toFixed(2);
		   	
		   	var tempNetAmt = parseFloat(self.user.netAmt) - parseInt(self.user.netAmt);
	     	
	     	if(tempNetAmt > 0.50){
	     		self.user.netAmt = parseInt(self.user.netAmt) + 1;
	     	}else{
	     		self.user.netAmt = parseInt(self.user.netAmt);
	     	}
	     	
	     	self.user.roundOffAmt = (tempNetAmt).toFixed(2);
	     	 
	    	$scope.gridOptions.data.splice(index, 1);
	    	self.resetProduct();
    	}else{
    		alert("please select product to delete");
    	}
    };
    
    self.getNextBillNo = function(){
    	salesService.getNextBillNo()
     .then(
          function(d) {
        	   console.log("next bill no="+d);
               self.user.billNo = d;
          },
          function(errResponse){
               console.error('Error while fetching next bill no');
          }
       );
    };
    
    self.getNextBillNo();
    
    self.fetchCustomerList = function(){
	    	customerService.fetchCustomerList()
         .then(
              function(d) {
                   self.custList = d;
              },
              function(errResponse){
                   console.error('Error while fetching customers');
              }
         );
     };
     
     self.fetchCustomerList();
     
     self.fetchProdNameList = function(manId){
	    productService.fetchStockProdList(manId)
	    	.then(
              function(d) {
                   self.prodNameList = d;
              },
              function(errResponse){
                   console.error('Error while fetching product');
              }
         );
     };
     
     self.fetchManufactList = function(){
     	manufactService.fetchManufactList()
         .then(
              function(d) {
                   self.maufactList = d;
                   console.info("fetched manufacturer");
              },
              function(errResponse){
                   console.error('Error while fetching Manufacturers');
              }
         );
     };
     
     self.fetchManufactList();
     
     self.fetchProdPckgList = function(prodName,manId){
    	 salesService.fetchProdPckgList(prodName,manId)
      .then(
           function(d) {
                self.prodPckgList = d;
           },
           function(errResponse){
                console.error('Error while fetching product');
           }
      	);
     };
	  
     self.fetchProductList = function(prodId){
    	 self.prodData.prodId = prodId;
    	 self.productData.prodId = prodId;
    	 salesService.fetchProductList(prodId)
      .then(
           function(d) {
                self.prodList = d;
           },
           function(errResponse){
                console.error('Error while fetching product');
           }
      	);
     };
     
     self.validateStock = function(){
    	if(parseInt(self.productData.qty) > parseInt(self.productData.stock)){
    		alert('Quantity cannot be more then available stock');
    		document.getElementById('dlgQty').focus();
    	}
     };
     
     self.addValToProdGrid = function(prodData){
    	 if(!self.addCheck()){
	    	 self.productData.manId = self.manufactData.manId;
	    	 self.productData.manName = self.manufactData.manName;
	    	 self.productData.prodId = self.prodData.prodId;
	    	 self.productData.prodName = self.prodData.prodName;
	    	 self.productData.pckSize = self.pckSizeData.pckSize;
	    	 self.productData.totDisc = (parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val)).toFixed(2); 
	    	 var scheme = parseFloat(self.productData.scheme);
	    	 console.log('oldRate'+self.productData.oldRate+'rate'+self.productData.rate);
	    	 if(self.productData.rate != self.productData.oldRate){
	    		 self.productData.newRate = self.productData.rate; 
	    	 }	    	 
	    	 $scope.gridOptions.data.push(prodData);
	    	 
	    	 self.user.grossAmt = (parseFloat(self.user.grossAmt) + parseFloat(self.productData.totAmt)).toFixed(2);
	    	 self.user.totTax = (parseFloat(self.user.totTax) + parseFloat(self.productData.taxVal)).toFixed(2);
	    	 if(scheme > 0 && scheme == 0.5){
	    		 self.user.totScheme = (parseFloat(self.user.totScheme) + parseFloat(self.productData.schemeVal)).toFixed(2);
	    	 }
	    	 self.user.netAmt = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totTax)).toFixed(2);
	    	 self.user.grossAmtSch = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totScheme)).toFixed(2);
	    	 self.user.tempNetAmt = self.user.netAmt;
	    	 
	    	 var tempNetAmt = parseFloat(self.user.netAmt) - parseInt(self.user.netAmt);
	     	
	     	 if(tempNetAmt > 0.50){
	     		self.user.netAmt = parseInt(self.user.netAmt) + 1;
	     	 }else{
	     		self.user.netAmt = parseInt(self.user.netAmt);
	     	 }
	     	
	     	 self.user.roundOffAmt = (tempNetAmt).toFixed(2);
	     	 
	    	 self.resetProduct();
    	 }
     };
     
     self.updateGridProd = function(prodData){
    	 var rowCol = null;
     	rowCol = $scope.gridApi1.cellNav.getFocusedCell();
     	var rowData = $scope.gridApi1.selection.getSelectedRows();
     	
     	if(rowData != null && rowData.length > 0){
     		var index = $scope.gridOptions.data.indexOf(rowCol.row.entity);
 	    	self.user.grossAmt = (parseFloat(self.user.grossAmt) - parseFloat(tableData.totAmt)).toFixed(2);
 		   	self.user.totTax = (parseFloat(self.user.totTax) - parseFloat(tableData.taxVal)).toFixed(2);
 		   	if(tableData.scheme > 0 && tableData.scheme == 0.5){
 		   		self.user.totScheme = (parseFloat(self.user.totScheme) - parseFloat(tableData.schemeVal)).toFixed(2);
 		   	}
 		   	self.user.netAmt = 0;
 		   	self.user.netAmt = (parseFloat(self.user.netAmt) + parseFloat(self.user.grossAmt) + parseFloat(self.user.totTax)).toFixed(2);
 		   	self.user.tempNetAmt = self.user.netAmt;
 		   	self.user.roundOffAmt = 0;
 		   	self.user.grossAmtSch = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totScheme)).toFixed(2);
 		   	
 		   	var tempNetAmt = parseFloat(self.user.netAmt) - parseInt(self.user.netAmt);
 	     	
 	     	if(tempNetAmt > 0.50){
 	     		self.user.netAmt = parseInt(self.user.netAmt) + 1;
 	     	}else{
 	     		self.user.netAmt = parseInt(self.user.netAmt);
 	     	}
 	     	
 	     	self.user.roundOffAmt = (tempNetAmt).toFixed(2);
 	     	 
 	    	$scope.gridOptions.data.splice(index, 1);
     	}
     	self.addValToProdGrid(prodData);
    	 /*var updateRow = self.updateRow;
    	 var grossAmt = self.user.grossAmt;
    	 var netAmt = self.user.netAmt;
    	 var totTax = self.user.totTax;
    	 var totScheme = self.user.totScheme;
    	 self.productData.totDisc = (parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val)).toFixed(2);
    	 var scheme = parseFloat(self.productData.scheme);
    	 if(self.productData.rate != self.productData.oldRate){
    		 self.productData.newRate = self.productData.rate; 
    	 }
    	 
    	 grossAmt = parseFloat(grossAmt) - parseFloat(upTotAmt);
    	 totTax = parseFloat(totTax) - parseFloat(upTotTax);
    	 if(parseFloat(upScheme) > 0 && parseFloat(upScheme) == 0.5){
    		 totScheme = parseFloat(totScheme) - parseFloat(upSchemeVal);
    	 }
    	 
    	 netAmt = parseFloat(grossAmt) + parseFloat(totTax);

    	 self.updateRow = self.productData;
    	 prodData = self.productData;
    	 
    	 grossAmt = (parseFloat(grossAmt) + parseFloat(self.updateRow.totAmt)).toFixed(2);
    	 totTax = (parseFloat(totTax) + parseFloat(self.updateRow.taxVal)).toFixed(2);
    	 if(scheme > 0 && scheme == 0.5){
    		 totScheme = (parseFloat(totScheme) + parseFloat(self.updateRow.schemeVal)).toFixed(2);
    	 }
    	 
    	 netAmt = (parseFloat(grossAmt) + parseFloat(totTax)).toFixed(2);
    	 
    	 self.user.tempNetAmt = netAmt;
    	 self.user.grossAmt = grossAmt;
    	 self.user.netAmt = netAmt;
    	 self.user.totScheme = totScheme;
    	 self.user.totTax = totTax;
    	 self.user.roundOffAmt = 0;
    	 self.user.grossAmtSch = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totScheme)).toFixed(2);
    	 
    	 var tempNetAmt = parseFloat(self.user.netAmt) - parseInt(self.user.netAmt);
	     	
     	 if(tempNetAmt > 0.50){
     		self.user.netAmt = parseInt(self.user.netAmt) + 1;
     	 }else{
     		self.user.netAmt = parseInt(self.user.netAmt);
     	 }
     	
     	 self.user.roundOffAmt = (tempNetAmt).toFixed(2);
    	 
    	 self.resetProduct();*/
     };
     
     self.calculateRoundOffAmt = function(){s
    	 self.user.roundOffAmt = (parseFloat(self.user.tempNetAmt) - parseFloat(self.user.netAmt)).toFixed(2) ;
     };
     
     self.resetProduct = function(){
    	 self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',rate:''
				,scheme:'',schemeVal:'',disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:''};
    	 self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    	    self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
    	    self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',rate:''
    	    				,scheme:'',grossAmt:0,schemeVal:'',totDisc:0,disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:'',qtyMinus:0};
     };
     
     self.resetStock = function(){
    	 self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',rate:''
				,scheme:'',schemeVal:'',disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:'', qtyMinus:0};
    	 self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    	    self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
    	    self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',rate:''
    	    				,scheme:'',grossAmt:0,schemeVal:'',totDisc:0,disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:'',qtyMinus:0};
     };
     
     self.resetSales = function(){
    	 self.user={billNo:'',billDate:'',custId:'',custName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    	 self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
    	 $scope.gridOptions.data = data.data;
    	 self.oldProdList = null;
    	 self.getNextBillNo();
    	 self.user.billDate = date;
     };
     
     self.calculateQtyRate = function(){
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 var scheme = parseFloat(self.productData.scheme);
    	 var disc1 = parseFloat(self.productData.disc1);
    	 var disc2 = parseFloat(self.productData.disc2);
    	 var tax = parseFloat(self.productData.taxPerc);
    	 self.productData.grossAmt = (amt).toFixed(2);
    	 
    	 if(scheme > 0 && scheme == 0.5 ){
    		 self.productData.schemeVal = (rate * scheme).toFixed(2);
    	 }else{
    		 self.productData.schemeVal = scheme * 1;
    	 }
    	 
    	 if(disc1 > 0 && scheme == 0.5){
    		 self.productData.disc1Val = ((amt - parseFloat(self.productData.schemeVal))*(disc1/100)).toFixed(2) ;
    	 }else{
    		 self.productData.disc1Val = (amt*(disc1/100)).toFixed(2) ;
    	 }
    	 
    	 if(disc2 > 0 && scheme == 0.5){
    		 self.productData.disc2Val = ((amt - parseFloat(self.productData.schemeVal) - parseFloat(self.productData.disc1Val) )*(disc2/100)).toFixed(2) ;
    	 }else{
    		 self.productData.disc2Val = ((amt - parseFloat(self.productData.disc1Val))*(disc2/100)).toFixed(2);
    	 }
    	 
    	 if(tax > 0 && scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.taxVal = ((amt - discTot )*(tax/100)).toFixed(2) ;
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.taxVal = ((amt - discTot)*(tax/100)).toFixed(2);
    	 }
    	 
    	 if(scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }
    	 
    	 
     };
     
     self.calculateScheme = function(){
    	 var scheme = parseFloat(self.productData.scheme);
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 
    	 if(scheme > 0 && scheme == 0.5 ){
    		 self.productData.schemeVal = (rate * scheme).toFixed(2);
    	 }else{
    		 self.productData.schemeVal = scheme * 1;
    	 }
    	 
    	 if(scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }
     };
     
     self.calculateDisc1 = function(){
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 var scheme = parseFloat(self.productData.scheme);
    	 var disc1 = parseFloat(self.productData.disc1);
    	 
    	 if(disc1 > 0 && scheme == 0.5){
    		 self.productData.disc1Val = ((amt - parseFloat(self.productData.schemeVal))*(disc1/100)).toFixed(2) ;
    	 }else{
    		 self.productData.disc1Val = (amt*(disc1/100)).toFixed(2) ;
    	 }
    	 
    	 if(scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }
     };
     
     self.calculateTax = function(){
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 var scheme = parseFloat(self.productData.scheme);
    	 var tax = parseFloat(self.productData.taxPerc);
    	 
    	 
    	 if(tax > 0 && scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.taxVal = ((amt - discTot )*(tax/100)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.taxVal = ((amt - discTot)*(tax/100)).toFixed(2);
    	 }
    	 
    	 if(scheme == 0.5){
    		 var discTot = parseFloat(self.productData.schemeVal) + parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)).toFixed(2);
    	 }
     };
     
     self.calculateDisc2 = function(){
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 var scheme = parseFloat(self.productData.scheme);
    	 var disc2 = parseFloat(self.productData.disc2);
    	 
    	 
    	 if(disc2 > 0 && scheme == 0.5){
    		 self.productData.disc2Val = ((amt - parseFloat(self.productData.schemeVal) - parseFloat(self.productData.disc1Val) )*(disc2/100)).toFixed(2) ;
    	 }else{
    		 self.productData.disc2Val = ((amt - parseFloat(self.productData.disc1Val))*(disc2/100)).toFixed(2);
    	 }
    	 self.calculateTax();
     };
     
     self.save = function(){
    	self.user.custId = self.custData.custId;
    	self.user.custName = self.custData.custName;
    	self.user.custCode = self.custData.custCode;
    	self.user.vatNo = self.custData.vatNo;
    	self.user.address = self.custData.address;
    	self.user.prodList = $scope.gridOptions.data;
    	self.user.oldProdList = self.oldProdList;
    	
    	salesService.addSales(self.user)
        .then(
        		function(d){
        			alert("Sales recorded successfully...");
        			self.fetcSalesList();
        			self.user={billNo:'',billDate:'',custId:'',custName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
        			self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
        	    	$scope.gridOptions.data = data.data;
        	    	self.oldProdList = null;
        	    	self.getNextBillNo();
        		}
             ,
             function(errResponse){
            	  alert("Error in saving this sales...");
                  console.error('Error in saving sales');
             }
        );

     };
     
     self.submit = function(){
     	self.user.custId = self.custData.custId;
     	self.user.custName = self.custData.custName;
     	self.user.custCode = self.custData.custCode;
    	self.user.vatNo = self.custData.vatNo;
    	self.user.address = self.custData.address;
     	self.user.prodList = $scope.gridOptions.data;
     	self.user.oldProdList = self.oldProdList;

     	salesService.submitSales(self.user)
         .then(
         		function(d){
         			alert("Sales recorded successfully...");
         			self.fetcSalesList();
         			self.user={billNo:'',billDate:'',custId:'',custName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
         			self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
         			$scope.gridOptions.data = data.data;
         	    	self.oldProdList = null;
         	    	self.getNextBillNo();
         		}
              ,
              function(errResponse){
             	  	alert("Error in submiting this sales...");
             	  	console.error('Error in submiting sales');
              }
         );

      };
      
      self.fetcSalesList = function(){
    	  salesService.fetchSalesList()
          .then(
               function(d) {
            	   $scope.gridOptions2.data = d;
               },
               function(errResponse){
                    console.error('Error while fetching sales');
               }
          	);
      };
      
      self.printBill = function(){
    	  self.user.custId = self.custData.custId;
      	  self.user.custName = self.custData.custName;
      	  self.user.custCode = self.custData.custCode;
    	  self.user.vatNo = self.custData.vatNo;
    	  self.user.address = self.custData.address;
      	  self.user.prodList = $scope.gridOptions.data;
      	  
      	  alert('print called');
    	  salesService.printBill(self.user)
          .then(
               function(d) {
            	   alert('successfully called');
               },
               function(errResponse){
                    console.error('Error in printing bill');
               }
          	);
      };
      
      self.stockMinus = function(){
    	  self.productData.manId = self.manufactData.manId;
    	  
    	  salesService.stockMinus(self.productData)
          .then(
               function(d) {
            	   alert("Stock deducted successfully");
            	   self.resetStock();
               },
               function(errResponse){
                    console.error('Error in deducting stock');
               }
          	);
      };
      
      self.fetcSalesList();
      
      self.fetchSales = function(){
    	  self.user={billNo:'',billDate:'',custId:'',custName:'',vatNo:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    	  self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
    	  $scope.gridOptions.data = data.data;
    	  self.oldProdList = null;
    	  
    	  var rowCol = null;
      	  rowCol = $scope.gridApi2.cellNav.getFocusedCell();
      	  var rowData = $scope.gridApi2.selection.getSelectedRows();
      	  
      	  if(rowData != null && rowData.length > 0){
	    	  salesService.fetchSales(rowCol.row.entity.salesId)
	          .then(
	               function(d) {
	            	   self.user = d;
	            	   self.custData = d;
	            	   $scope.gridOptions.data = self.user.oldProdList;
	            	   self.oldProdList = JSON.parse(JSON.stringify(self.user.oldProdList));
	               },
	               function(errResponse){
	                    console.error('Error while fetching sales');
	               }
	          );
      	  }else{
      		  alert("Please select sales record for editing");
      	  }
      };
      
      self.deleteSales = function(){
    	var rowCol = null;
      	rowCol = $scope.gridApi2.cellNav.getFocusedCell();
      	var rowData = $scope.gridApi2.selection.getSelectedRows();
      	
      	if(rowData != null && rowData.length > 0){
	      	salesService.deleteSales(rowCol.row.entity.salesId)
	          .then(
	      		 function(d){
	      			self.fetcSalesList();
	     			self.user={billNo:'',billDate:'',custId:'',custName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
	     			self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
	     			$scope.gridOptions.data = data.data;
	     	    	self.oldProdList = null;
	     	    	self.getNextBillNo();
		    		alert("Sales record deleted successfully...");
	      		 },
	      		 function(errResponse){
	                console.error('Error while deleting sales');
	      		 }
	        );
      	}else{
      		alert("Please select sales record to delete");
      	}
      };
   
      self.addCheck = function(){
    	  if($scope.gridOptions.data.length > 14){
    		  $('#prodModal').modal('hide');
    		  alert("Maximum limit of 15 products is reached.");
    		  return true;
    	  }else{
    		  return false;
    	  }
      };
}
]);