'use strict';

homeApp.controller('reportController', ['$scope','manufactService', 'productService', 'reportService', 'salesService', 'customerService','distributorService',  
    function ($scope, manufactService, productService, reportService, salesService, customerService, distributorService) {
		var self = this;
		self.user={manId:'',prodName:'',pckSize:''};
		self.manufactData={manId:'',manName:'',address:'',phoneNo:'',custCare:''};;
	    self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
	    self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
	    self.custList = [];
	    self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
	    self.distList = [];
	    self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
		self.maufactList=[];
	    self.prodNameList=[];
	    self.prodPckgList=[];
	    self.purSalData = {distId:'',distName:'',custId:'',custName:'',fromDate:'',toDate:'',finYear:''};
	    self.finYearData = {finYear:''};
	    self.finYearList = [];
	    
	    var dataGrid = [];
	    var data = {"data": []};
	    
	    $scope.gridOptions = { enableSorting: false,
	    					   enableFiltering:false,	
	    					   data: dataGrid,
	    					   multiSelect: false,
	    					   enableColumnMenus: false,
	    					   enableRowSelection: false,
	    					   onRegisterApi: function(gridApi) {
	    						      console.log('Loaded Grid API ');
	    						      $scope.gridApi = gridApi;
	    						}
	    					};
		
	    $scope.gridOptions.columnDefs = [
	                             		 { name: 'prodCode', displayName:'Product Code', enableCellEdit: false, width: '15%' },
	                             		 { name: 'prodName', displayName: 'Product Name', enableCellEdit: false, width: '20%'},
	                             		 { name: 'manName', displayName:'Manufacturer', enableCellEdit: false, width: '20%' },
	                             		 { name: 'pckSize', displayName:'Packing', enableCellEdit: false, width: '10%' },
	                             		 { name: 'mrp', displayName:'MRP', enableCellEdit: false, width: '10%' },
	                             		 { name: 'rate', displayName:'Rate', enableCellEdit: false, width: '10%' },
	                             		 { name: 'stock', displayName:'Stock', enableCellEdit: false, width: '10%' }
	                             	];
	    
	    $scope.gridOptions1 = { enableSorting: false,
				   enableFiltering:false,	
				   data: dataGrid,
				   multiSelect: false,
				   enableColumnMenus: false,
				   enableRowSelection: false,
				   onRegisterApi: function(gridApi) {
					      console.log('Loaded Grid API 1');
					      $scope.gridApi1 = gridApi;
					}
				};

	    $scope.gridOptions1.columnDefs = [
	                     { name: 'count', displayName:'Sr No', enableCellEdit: false, width: '5%' },                 
                  		 { name: 'name', displayName:'Name', enableCellEdit: false, width: '18%' },
                  		 { name: 'openingBal', displayName: 'Opening Balance', enableCellEdit: false, width: '16%'},
                  		 { name: 'billing', displayName:'Billing', enableCellEdit: false, width: '16%' },
                  		 { name: 'payment', displayName:'Payment', enableCellEdit: false, width: '16%' },
                  		 { name: 'amtAdj', displayName:'Adjusted Amt', enableCellEdit: false, width: '16%' },
                  		 { name: 'balance', displayName:'Balance', enableCellEdit: false, width: '16%' }
                  	];
	    
	    $scope.gridOptions2 = { enableSorting: false,
				   enableFiltering:false,	
				   data: dataGrid,
				   multiSelect: false,
				   enableColumnMenus: false,
				   enableRowSelection: false,
				   onRegisterApi: function(gridApi) {
					      console.log('Loaded Grid API 2');
					      $scope.gridApi2 = gridApi;
					}
				};

	    $scope.gridOptions2.columnDefs = [
	                 { name: 'count', displayName:'Sr No', enableCellEdit: false, width: '5%' },
	                 { name: 'name', displayName:'Name', enableCellEdit: false, width: '15%' },
               		 { name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '15%' },
               		 { name: 'billDt', displayName: 'Bill Date', enableCellEdit: false, width: '15%'},
               		 { name: 'billAmt', displayName:'Bill Amount', enableCellEdit: false, width: '15%' },
               		 { name: 'payment', displayName:'Payment', enableCellEdit: false, width: '15%' },
               		 { name: 'adjAmt', displayName:'Adjusted Amt', enableCellEdit: false, width: '15%' },
               		 { name: 'balance', displayName:'Balance', enableCellEdit: false, width: '15%' }
               	];
	    
	    $scope.gridOptions3 = { enableSorting: false,
				   enableFiltering:false,	
				   data: dataGrid,
				   multiSelect: false,
				   enableColumnMenus: false,
				   enableRowSelection: false,
				   onRegisterApi: function(gridApi) {
					      console.log('Loaded Grid API 3');
					      $scope.gridApi3 = gridApi;
					}
				};

	    $scope.gridOptions3.columnDefs = [
	                 { name: 'count', displayName:'#', enableCellEdit: false, width: '5%' },
	                 { name: 'name', displayName:'Name', enableCellEdit: false, width: '15%' },
            		 { name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '10%' },
            		 { name: 'billDt', displayName: 'Transaction Date', enableCellEdit: false, width: '13%'},
            		 { name: 'cheqNo', displayName:'Cheque No', enableCellEdit: false, width: '12%' },
            		 { name: 'cheqDt', displayName:'Cheque Date', enableCellEdit: false, width: '13%' },
            		 { name: 'bankName', displayName:'Bank Name', enableCellEdit: false, width: '15%' },
            		 { name: 'deposit', displayName:'Deposit', enableCellEdit: false, width: '13%' },
            		 { name: 'payment', displayName:'Payment', enableCellEdit: false, width: '13%' },
            		 { name: 'balance', displayName:'Balance', enableCellEdit: false, width: '13%' }
            	];
	    
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
	     
	     self.fetchDistributorList = function(){
		    	distributorService.fetchDistributorList()
	         .then(
	              function(d) {
	                   self.distList = d;
	                   console.info(self.distList);
	              },
	              function(errResponse){
	                   console.error('Error while fetching distributors');
	              }
	         );
	     };
	     
	     self.fetchDistributorList();
	     
	     self.activate = function(fetchType){
        	if(fetchType == 'All'){
        		self.user.actType = true;
        	}else if(fetchType == 'Single'){
        		self.user.actType = false;
        	}
        	self.activateRepType(self.user.repType);
         };
         
         self.activateRepType = function(fetchType){
         	if(fetchType == 'dist' && self.user.actType == true){
         		self.user.actDistType = true;
         		self.user.actCustType = true;
         	}else if(fetchType == 'dist' && self.user.actType == false){
         		self.user.actDistType = false;
         		self.user.actCustType = true;
         	}
         	
         	if(fetchType == 'cust' && self.user.actType == true){
         		self.user.actCustType = true;
         		self.user.actDistType = true;
         	}else if(fetchType == 'cust' && self.user.actType == false){
         		self.user.actCustType = false;
         		self.user.actDistType = true;
         	}
          };
          
          self.activateLegderOpt = function(fetchType){
        	if(fetchType == 'dist'){
        		self.user.actCustType = true;
         		self.user.actDistType = false;
        	}else if(fetchType == 'cust'){
        		self.user.actCustType = false;
         		self.user.actDistType = true;
        	}
         };
          
         
         self.user.fetchType = 'All';
         self.user.repType = 'dist';
         self.activate('All');
         self.activateRepType('dist');
	     
	     self.fetchStock = function(){
	    	 self.user.manId = self.manufactData.manId;
	    	 self.user.prodName = self.prodData.prodName;
	    	 self.user.pckSize = self.pckSizeData.pckSize;
	    	 
	    	 if(self.user.fetchType == 'All'){
		    	 reportService.fetchAllStock()
			      .then(
			           function(d) {
			        	   $scope.gridOptions.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching stock');
			               console.error('Error while fetching stock');
			           }
			      );
	    	 }else{
	    		 reportService.fetchStock(self.user)
			      .then(
			           function(d) {
			        	   $scope.gridOptions.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching stock');
			               console.error('Error while fetching stock');
			           }
			     );
	    	 }
	     };
	     
	     self.fetchBalReport = function(){
	    	 self.purSalData.finYear = self.finYearData.finYear;
	    	 
	    	 if(self.user.fetchType == 'All' && self.user.repType == 'dist'){
		    	 reportService.fetchAllDistBal(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions1.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching balance report');
			               console.error('Error while fetching balance report');
			           }
			      );
	    	 }else if(self.user.fetchType == 'Single' && self.user.repType == 'dist'){
	    		 self.purSalData.distId = self.distData.distId;
	    		 reportService.fetchDistBal(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions1.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching balance report');
			               console.error('Error in fetching balance report');
			           }
			     );
	    	 }else if(self.user.fetchType == 'All' && self.user.repType == 'cust'){
	    		 self.purSalData.custId = self.custData.custId;
		    	 reportService.fetchAllCustBal(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions1.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching balance report');
			               console.error('Error while fetching balance report');
			           }
			      );
	    	 }else if(self.user.fetchType == 'Single' && self.user.repType == 'cust'){
	    		 self.purSalData.custId = self.custData.custId;
	    		 reportService.fetchCustBal(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions1.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching balance report');
			               console.error('Error in fetching balance report');
			           }
			     );
	    	 }
	     };
	     
	     self.fetchPurSalesReport = function(){
 	    	 
	    	 if(self.user.fetchType == 'All' && self.user.repType == 'dist'){
		    	 reportService.fetchAllPurReport(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions2.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching purchase report');
			               console.error('Error in fetching purchase report');
			           }
			      );
	    	 }else if(self.user.fetchType == 'Single' && self.user.repType == 'dist'){
	    		 self.purSalData.distId = self.distData.distId;
	    		 
	    		 reportService.fetchDistPurReport(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions2.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching purchase report');
			               console.error('Error in fetching purchase report');
			           }
			     );
	    	 }else if(self.user.fetchType == 'All' && self.user.repType == 'cust'){
		    	 reportService.fetchAllSalesReport(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions2.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching sales report');
			               console.error('Error in fetching sales report');
			           }
			      );
	    	 }else if(self.user.fetchType == 'Single' && self.user.repType == 'cust'){
	    		 self.purSalData.custId = self.custData.custId;
	    		 
	    		 reportService.fetchCustSalesReport(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions2.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching sales report');
			               console.error('Error in fetching sales report');
			           }
			     );
	    	 }
	     };
	     
	     self.calculateFinYear = function(){
	    	 reportService.fetchFinYearList()
		      .then(
		           function(d) {
		        	   console.log("yearList="+d.finYearList);
		        	   self.finYearList = d;
		           },
		           function(errResponse){
		        	   alert('Error in fetching Financial year');
		               console.error('Error in fetching financial Year');
		           }
		     );
	     };
	     
	     self.calculateFinYear();
	     
	     self.fetchLedger = function(){
 	    	 
	    	 if(self.user.repType == 'dist'){
	    		 self.purSalData.distId = self.distData.distId;
		    	 reportService.fetchDistLedger(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions3.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching ledegr');
			               console.error('Error in fetching ledger');
			           }
			      );
	    	 }else if(self.user.repType == 'cust'){
	    		 self.purSalData.custId = self.custData.custId;
		    	 reportService.fetchCustLedger(self.purSalData)
			      .then(
			           function(d) {
			        	   $scope.gridOptions3.data = d;
			           },
			           function(errResponse){
			        	   alert('Error in fetching ledger');
			               console.error('Error in fetching ledger');
			           }
			      );
	    	 }
	    	 
	     };
	}
]);