'use strict';

homeApp.controller('purchaseController', ['$q','$scope','distributorService','manufactService','taxService', 'productService','purchaseService','uiGridValidateService',  
    function ($q, $scope, distributorService, manufactService, taxService, productService, purchaseService,uiGridValidateService) {
	var self = this;
    self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    self.purList={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    self.users=[];
    self.distList = [];
    self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
    self.manufactData={manId:'',manName:'',address:'',phoneNo:'',custCare:''};;
    self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
    self.tempPckSizeData={prodId:'',prodCode:'',pckSize:''};
    self.productData={prodDtlId:'',prodId:'',prodCode:'',mrpStr:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',mrpStr:'',rate:''
    				,scheme:'',schemeVal:'',disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:0,taxPerc:0,taxVal:0,totAmt:''};
    self.taxData={taxId:'',taxName:'',taxPerc:''};
    self.mrpData = {taxId:'',taxPerc:'', prodId:'', mrp:0, rate:0, prodCode:''};
    self.maufactList=[];
    self.tempProdList=[];
    self.prodNameList=[];
    self.prodList=[];
    self.prodPckgList=[];
    self.oldProdList= null;
    self.taxList = [];
    self.mrpList = [];
    
    self.updateRow = null;
    var upTotAmt = 0;
	var upTotTax = 0;
	var upScheme = 0;
	var upSchemeVal = 0;
	var today = new Date();
	var tableData = null;
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
    						      gridApi.validate.on.validationFailed($scope,function(rowEntity, colDef, newValue, oldValue){
    						            $window.alert('rowEntity: '+ rowEntity + '\n' +
    						                          'colDef: ' + colDef + '\n' +
    						                          'newValue: ' + newValue + '\n' +
    						                          'oldValue: ' + oldValue);
    						          });
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
		 { name: 'prodName', displayName: 'Product Name', width: '25%', validators: {required: true}, cellTemplate: 'ui-grid/cellTitleValidator' },
		 { name: 'pckSize', displayName: 'Packaging Size', width: '20%', validators: {required: true}, cellTemplate: 'ui-grid/cellTitleValidator' },
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
		 { name: 'manId', displayName:'Manufacturer Id', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'manName', displayName:'Manufacturer Name', enableCellEdit: false, width: '20%' },
		 { name: 'taxId', displayName:'Tax Id', enableCellEdit: false, width: '20%', visible: false },
		 { name: 'taxPerc', displayName:'Tax%', enableCellEdit: false, width: '20%'},
		 { name: 'taxVal', displayName:'Tax Value', enableCellEdit: false, width: '20%' },
		 { name: 'totAmt', displayName:'Gross Amount', enableCellEdit: false, width: '20%' }
	];
    
    $scope.gridOptions2.columnDefs = [
		 { name: 'purId', enableCellEdit: false, visible: false },
		 { name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '20%' },
		 { name: 'billDate', displayName: 'Bill Date', enableCellEdit: false, width: '20%'},
		 { name: 'distId', enableCellEdit: false, visible: false },
		 { name: 'distName', displayName:'Distributor', enableCellEdit: false, width: '20%' },
		 { name: 'grossAmt', displayName:'Gross Amount', enableCellEdit: false, width: '20%' },
		 { name: 'totTax', displayName:'Total Vat', enableCellEdit: false, width: '20%' },
		 { name: 'roundOffAmt', displayName:'Round Off Amount', enableCellEdit: false, width: '20%' },
		 { name: 'netAmt', displayName:'Net Amount', enableCellEdit: false, width: '20%' }
	];
    
    uiGridValidateService.setValidator('startWith',
	    function(argument) {
	      return function(oldValue, newValue, rowEntity, colDef) {
	        if (!newValue) {
	          return true; // We should not test for existence here
	        } else {
	          return newValue.startsWith(argument);
	        }
	      };
	    },
	    function(argument) {
	      return 'You can only insert names starting with: "' + argument + '"';
	    }
	  );

    self.updatePurProd = function(){
    	var rowCol = $scope.gridApi1.cellNav.getFocusedCell();
    	tableData = rowCol.row.entity;
    	$q.all([
					self.fetchProdNameList(rowCol.row.entity.manId),
					self.fetchProdPckgList(rowCol.row.entity.prodName, rowCol.row.entity.manId),
					self.fetchProductList(rowCol.row.entity.prodId)
    	        ]).then(function(data){
    	        	self.productData = rowCol.row.entity;
    	        	self.manufactData.manId = rowCol.row.entity.manId;
    	        	self.manufactData.manName = rowCol.row.entity.manName;
    	        	self.pckSizeData.prodId = rowCol.row.entity.prodId;
    	        	self.pckSizeData.prodCode = rowCol.row.entity.prodCode;
    	        	self.pckSizeData.pckSize = rowCol.row.entity.pckSize;
    	        	self.taxData = rowCol.row.entity;
    	        	self.prodData = rowCol.row.entity;
    	        	self.updateRow = rowCol.row.entity;
    	        	upTotAmt = rowCol.row.entity.totAmt;
    	       	 	upTotTax = rowCol.row.entity.taxVal;
    	       	 	upScheme = rowCol.row.entity.scheme;
    	       	 	upSchemeVal = rowCol.row.entity.schemeVal;	
    	});
    };
    
    self.deletePurProd = function(){
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
	    	$scope.gridOptions.data.splice(index, 1);
	    	self.resetProduct();
    	}else{
    		alert("please select product to delete");
    	}
    };

    self.setMrpDetails = function($select){
    	var search = $select.search,
    	   list = angular.copy($select.items), 
    	   FLAG = -1;
    	
    	console.log('search'+search);
    	console.log('list'+list);
    	//remove last user input
    	list = list.filter(function(item) { 
    	   return item.mrp !== FLAG; 
    	});
    	 
    	if (!search) {
    	   //use the predefined list
    	   $select.items = list;
    	}
    	else {
    	   //manually add user input and set selection
    	   try{
	    	   var userInputItem = {
	    			   mrp: FLAG,
	    			   mrpStr:search
	    	   };
	    	   $select.items = [userInputItem].concat(list);
	    	   $select.selected = userInputItem;
    	   }catch(err){
    		   self.setTaxDetails();
    	   }
    	}
    };
    
    self.setTaxDetails = function(){
    	if(self.tempProdList.length > 0){
    		console.log('self.productData.mrpStr '+self.productData.mrpStr );
    		console.log('self.productData.mrp '+self.productData.mrp );
       		self.productData.mrp = parseFloat(self.productData.mrpStr);
       		console.log('self.productData.mrp '+self.productData.mrp );
       		self.productData.taxId = self.tempProdList[0].taxId;
       		self.productData.taxPerc = self.tempProdList[0].taxPerc;
       		self.taxData.taxId = self.tempProdList[0].taxId;
       		self.taxData.taxPerc = self.tempProdList[0].taxPerc;
       		self.productData.prodCode = self.tempProdList[0].prodCode;
       		self.productData.prodId = self.tempPckSizeData.prodId;
       		self.prodData.prodId = self.tempPckSizeData.prodId;
       		self.productData.qty = 0;
       		self.productData.scheme = 0;
       		self.productData.disc1 = 0;
       		self.productData.disc2 = 0;
   	   }else{
	   		console.log('self.productData.mrpStr '+self.productData.mrpStr );
			console.log('self.productData.mrp '+self.productData.mrp );
			self.productData.mrp = parseFloat(self.productData.mrpStr);
			console.log('self.productData.mrp '+self.productData.mrp );
	   		self.productData.taxId = self.taxList[0].taxId;
	   		self.productData.taxPerc = self.taxList[0].taxPerc;
	   		self.taxData.taxId = self.taxList[0].taxId;
	   		self.taxData.taxPerc = self.taxList[0].taxPerc;
	   		self.productData.rate = 0;
	   		self.productData.qty = 0;
	   		self.productData.scheme = 0;
	   		self.productData.disc1 = 0;
	   		self.productData.disc2 = 0;
   	   }
       console.log(self.productData);
    };
    
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
     
     self.fetchProdNameList = function(manId){
	    	productService.fetchProdNameList(manId)
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
     
     self.fetchTaxList = function(){
	    	taxService.fetchTaxList()
         .then(
              function(d) {
                   self.taxList = d;
              },
              function(errResponse){
                   console.error('Error while fetching tax');
              }
         );
     };
     
     self.fetchTaxList();
     
     self.fetchProdPckgList = function(prodName,manId){
    	 purchaseService.fetchProdPckgList(prodName,manId)
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
    	 self.tempPckSizeData.prodId = prodId;
    	 self.prodData.prodId = prodId;
    	 self.productData.prodId = prodId;
    	 purchaseService.fetchProductList(prodId)
      .then(
           function(d) {
                self.prodList = d;
                self.tempProdList = d;
           },
           function(errResponse){
                console.error('Error while fetching product');
           }
      	);
     };
     
     self.addValToProdGrid = function(prodData){
    	 self.productData.manId = self.manufactData.manId;
    	 self.productData.manName = self.manufactData.manName;
    	 self.productData.prodId = self.prodData.prodId;
    	 self.productData.prodName = self.prodData.prodName;
    	 self.productData.prodCode = self.pckSizeData.prodCode;
    	 self.productData.pckSize = self.pckSizeData.pckSize;
    	 self.productData.taxId = self.taxData.taxId;
    	 self.productData.taxPerc = self.taxData.taxPerc;
    	 
    	 console.log('self.prodData.prodCode'+self.prodData.prodCode);
    	 console.log('self.pckSizeData.prodCode'+self.pckSizeData.prodCode);
    	 
    	 var scheme = parseFloat(self.productData.scheme);
    	 
    	 $scope.gridOptions.data.push(prodData);
    	 
    	 self.user.grossAmt = (parseFloat(self.user.grossAmt) + parseFloat(self.productData.totAmt)).toFixed(2);
    	 self.user.totTax = (parseFloat(self.user.totTax) + parseFloat(self.productData.taxVal)).toFixed(2);
    	 if(scheme > 0 && scheme == 0.5){
    		 self.user.totScheme = (parseFloat(self.user.totScheme) + parseFloat(self.productData.schemeVal)).toFixed(2);
    	 }
    	 self.user.netAmt = (parseFloat(self.user.grossAmt) + parseFloat(self.user.totTax)).toFixed(2);
    	 self.user.tempNetAmt = self.user.netAmt;
    	 self.resetProduct();
     };
     
     self.updateGridProd = function(prodData){
    	var rowCol = null;
     	rowCol = $scope.gridApi1.cellNav.getFocusedCell();
     	var rowData = $scope.gridApi1.selection.getSelectedRows();
     	
     	if(rowData != null && rowData.length > 0){
     		console.log('tableData'+JSON.stringify(tableData));
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
 	    	$scope.gridOptions.data.splice(index, 1);
     	}
    	 self.addValToProdGrid(prodData);
    	 /*var updateRow = self.updateRow;
    	 var grossAmt = self.user.grossAmt;
    	 var netAmt = self.user.netAmt;
    	 var totTax = self.user.totTax;
    	 var totScheme = self.user.totScheme;
    	 var scheme = parseFloat(self.productData.scheme);
    	 self.productData.taxId = self.taxData.taxId;
    	 self.productData.taxPerc = self.taxData.taxPerc;
    	 
    	 grossAmt = parseFloat(grossAmt) - parseFloat(upTotAmt);
    	 totTax = parseFloat(totTax) - parseFloat(upTotTax);
    	 if(parseFloat(upScheme) > 0 && parseFloat(upScheme) == 0.5){
    		 totScheme = parseFloat(totScheme) - parseFloat(upSchemeVal);
    	 }
    	 
    	 netAmt = parseFloat(grossAmt) + parseFloat(totTax);

    	 self.updateRow = self.productData;
    	 prodData =  self.productData;
    	 
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
    	 
    	 self.resetProduct();*/
     };
     
     self.calculateRoundOffAmt = function(){
    	 self.user.roundOffAmt = (parseFloat(self.user.tempNetAmt) - parseFloat(self.user.netAmt)).toFixed(2) ;
     };
     
     self.resetProduct = function(){
    	 self.productData = undefined;
    	 self.productData={prodDtlId:'',prodId:'',prodCode:'',prodName:'',pckSize:'',qty:'',stock:'',oldMrp:'',oldRate:'',mrp:'',mrpStr:'',rate:''
				,scheme:'',schemeVal:'',disc1:'',disc1Val:'',disc2:'',disc2Val:'',manId:'',manName:'',taxId:'',taxPerc:'',taxVal:'',totAmt:''};
    	 self.manufactData={manId:'',manName:'',address:'',phoneNo:'',custCare:''};;
    	 self.prodData={prodId:'',prodName:'',manId:'',manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    	 self.pckSizeData={prodId:'',prodCode:'',pckSize:''};
    	 self.taxData = {taxId:'',taxName:'',taxPerc:''};
     };
     
     self.resetPurchase = function(){
    	 var date = today.getDate()+'/'+(today.getMonth()+1)+'/'+today.getFullYear();
    	 self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    	 self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
    	 $scope.gridOptions.data = data.data;
    	 self.oldProdList = null;
   	     self.user.billDate = date;
     };
     
     self.calculateQtyRate = function(){
    	 var rate = parseFloat(self.productData.rate);
    	 var qty = parseInt(self.productData.qty);
    	 var amt = qty * rate;
    	 var scheme = parseFloat(self.productData.scheme);
    	 var disc1 = parseFloat(self.productData.disc1);
    	 var disc2 = parseFloat(self.productData.disc2);
    	 var tax = parseFloat(self.taxData.taxPerc);
    	 
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
    		 self.productData.totAmt = ((amt-discTot)+parseFloat(self.productData.taxVal)).toFixed(2);
    	 }else{
    		 var discTot = parseFloat(self.productData.disc1Val) + parseFloat(self.productData.disc2Val);
    		 self.productData.totAmt = ((amt-discTot)+parseFloat(self.productData.taxVal)).toFixed(2);
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
    	 var tax = parseFloat(self.taxData.taxPerc);
    	 self.productData.taxId = self.taxData.taxId;
    	 self.productData.taxPerc = self.taxData.taxPerc;
    	 
    	 
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
    	self.user.distId = self.distData.distId;
    	self.user.distName = self.distData.distName;
    	self.user.prodList = $scope.gridOptions.data;
    	self.user.oldProdList = self.oldProdList;
    	
    	purchaseService.addPurchase(self.user)
        .then(
        		function(d){
        			alert("Purchase recorded successfully...");
        			self.fetcPurList();
        			self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
        			self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
        	    	$scope.gridOptions.data = data.data;
        	    	self.oldProdList = null;
        		}
             ,
             function(errResponse){
            	  alert("Error in saving this purchase...");
                  console.error('Error in saving purchase');
             }
        );

     };
     
     self.submit = function(){
     	self.user.distId = self.distData.distId;
     	self.user.distName = self.distData.distName;
     	self.user.prodList = $scope.gridOptions.data;
     	self.user.oldProdList = self.oldProdList;
     	
     	console.log('old='+self.user.oldProdList);
     	console.log('new='+self.user.prodList);

     	purchaseService.submitPurchase(self.user)
         .then(
         		function(d){
         			alert("Purchase recorded successfully...");
         			self.fetcPurList();
         			self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
         			self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
         			$scope.gridOptions.data = data.data;
         	    	self.oldProdList = null;
         		}
              ,
              function(errResponse){
             	  alert("Error in saving this purchase...");
                   console.error('Error in saving purchase');
              }
         );

      };
      
      self.fetcPurList = function(){
    	  purchaseService.fetchPurchaseList()
          .then(
               function(d) {
            	   $scope.gridOptions2.data = d;
               },
               function(errResponse){
                    console.error('Error while fetching product');
               }
          	);
      };
      
      self.fetcPurList();
      
      self.fetchPurchase = function(){
    	  self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
    	  self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
    	  $scope.gridOptions.data = data.data;
    	  self.oldProdList = null;
    	  
    	  var rowCol = null;
      	  rowCol = $scope.gridApi2.cellNav.getFocusedCell();
      	  var rowData = $scope.gridApi2.selection.getSelectedRows();
      	  
      	  if(rowData != null && rowData.length > 0){
	    	  purchaseService.fetchPurchase(rowCol.row.entity.purId)
	          .then(
	               function(d) {
	            	   self.user = d;
	            	   self.distData = d;
	            	   console.log(self.user.oldProdList);
	            	   console.log(self.user.prodList);
	            	   $scope.gridOptions.data = self.user.oldProdList;
	            	   self.oldProdList = JSON.parse(JSON.stringify(self.user.oldProdList));
	               },
	               function(errResponse){
	                    console.error('Error while fetching purchase');
	               }
	          );
      	  }else{
      		  alert("Please select purchase for editing");
      	  }
      };
      
      self.deletePurchase = function(){
    	var rowCol = null;
      	rowCol = $scope.gridApi2.cellNav.getFocusedCell();
      	var rowData = $scope.gridApi2.selection.getSelectedRows();
      	
      	if(rowData != null && rowData.length > 0){
	      	purchaseService.deletePurchase(rowCol.row.entity.purId)
	          .then(
	      		 function(d){
	      			self.fetcPurList();
	     			self.user={billNo:'',billDate:'',distId:'',distName:'',totScheme:0,grossAmt:0,totTax:0,roundOffAmt:0,netAmt:0,tempNetAmt:0,prodList:null,oldProdList:null};
	     			self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
	     			$scope.gridOptions.data = data.data;
	     	    	self.oldProdList = null;
		    		alert("Purchase record deleted successfully...");
	      		 },
	      		 function(errResponse){
	                console.error('Error while deleting purchase');
	      		 }
	        );
      	}else{
      		alert("Please select purchase record to delete");
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