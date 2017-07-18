'use strict';

homeApp.controller('purPayController', ['$scope', 'purPayService', 'distributorService', 'bankService','uiGridValidateService',
    function ($scope, purPayService, distributorService, bankService, uiGridValidateService) {
		var self = this;
	    self.user={transPayType:1, distId:'', bankId:'', remarks:'', cheqNo:'', cheqDate:'', 
	    			balAmt:0, transAmt:0, amtAdj:0, transDate:'',billList:null, amtPaid:0};
	    self.users=[];
	    self.billNoList=[];
	    self.billNoData={billNo:'',purId:'',distId:'',transPayTypeId:0,transTypeId:0,amtPaid:0,balAmt:0,amtAdj:0,transAmt:0};
	    self.distList=[];
	    self.bankList=[];
	    self.distData={distId:'',distName:'',address:'',phoneNo:'',vatNo:'',openingBal:''};
	    self.bankData = {bankId:'0',bankName:'',address:'',phoneNo:'',bankCode:''};
	    self.pendingDistBill=[];
	    var today = new Date();
	    var date = today.getDate()+'/'+(today.getMonth()+1)+'/'+today.getFullYear();
	    self.user.actType = true;
	    self.user.transPayType = 1;
	    self.user.transDate = date;
	    
	    
	    var dataGrid = [];
	    var data = {"data": []};
	    
	    $scope.gridOptions = { enableSorting: false,
	    					   enableFiltering:false,	
	    					   data: dataGrid,
	    					   enableColumnMenus: false,
	    					   onRegisterApi: function(gridApi) {
	    						      console.log('Loaded Grid API 1');
	    						      $scope.gridApi = gridApi;
	    						      gridApi.selection.on.rowSelectionChanged($scope, function(row){ 
	    						    	  self.addAmount();
	    						      });
	    						}
	    					};
	    
	    $scope.gridOptions.columnDefs = [
    		 { name: 'purId', enableCellEdit: false, visible: false},
    		 { name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '20%' },
    		 { name: 'billDate', displayName:'Bill Date', enableCellEdit: false, width: '20%' },
    		 { name: 'billAmt', displayName: 'Bill Amount', enableCellEdit: false, width: '20%'},
    		 { name: 'amtPaid', displayName:'Amount Paid', enableCellEdit: false, width: '20%' },
    		 { name: 'balAmt', displayName: 'Balance Amt', enableCellEdit: false, width: '20%'}
    	];
	    
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
	    
     	self.fetchBankList = function(){
	    	bankService.fetchSelfBankList()
            .then(
                 function(d) {
                      self.bankList = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching bank');
                 }
            );
        };
        
        self.fetchBankList();
        
        self.fetchBillDetails = function(distId){
        	purPayService.fetchBillDetails(distId)
            .then(
            		function(d){
            			$scope.gridOptions.data = d;
            		}
                 ,
                 function(errResponse){
                	  alert("Error in fetching bill details for distributor...");
                      console.error('error in fetching bill details');
                 }
            );
        };
        
        self.addTransaction = function(){
        	self.user.billList = $scope.gridApi.selection.getSelectedRows();
        	self.user.distId = self.distData.distId;
        	if(self.user.transPayType == '1'){
        		self.user.bankId = 0;
        	}else{
        		self.user.bankId = self.bankData.bankId;
        	}
        	
        	purPayService.addTransaction(self.user)
            .then(
            		function(d){
            			self.resetTransaction();
            			alert("Transaction recorded successfully...");
            		}
                 ,
                 function(errResponse){
                	  alert("Error in inserting transaction...");
                      console.error('Duplicate bank');
                 }
            );
        };
        
        self.resetTransaction = function(){
        	self.user.transPayType = 1;
        	self.user.distId = "";
        	self.user.bankId = "";
        	self.user.remarks = "";
        	self.user.cheqNo = "";
        	self.user.cheqDate = "";
        	self.user.balAmt = 0;
        	self.user.transAmt = 0;
        	self.user.amtAdj = 0;
        	self.user.amtPaid = 0;
        	self.user.transDate = date;
        	self.user.billList = null;
        	self.distData = undefined;
        	self.bankData = undefined;
        	$scope.gridOptions.data = data.data;
        	self.activate('cash');
        };
        
        self.addAmount = function(){
        	var rowData = $scope.gridApi.selection.getSelectedRows();
        	var count = 0;
        	var amt = 0;
        	
        	while(rowData.length > count){
        		if(parseFloat(rowData[count].balAmt) > 0){
        			amt = amt + parseFloat(rowData[count].balAmt);
        		}else{
        			amt = amt + parseFloat(rowData[count].billAmt);
        		}
        		
        		count++;
        	}
        	console.log('amt='+amt);
        	self.user.amtPaid = (amt).toFixed(2);
        };
        
        self.calculateAdjAmt = function(){
        	self.user.amtAdj = (parseFloat(self.user.amtPaid) - parseFloat(self.user.transAmt)).toFixed(2);
        };
        
        self.validateAmtAdj = function(){
        	var rowData = $scope.gridApi.selection.getSelectedRows();
        	var amtToAdj = (parseFloat(self.user.amtPaid) - parseFloat(self.user.transAmt)).toFixed(2);
        	var adjAmt = self.user.amtAdj;
        	
        	if(rowData.length > 1 && (parseFloat(adjAmt) > amtToAdj || parseFloat(adjAmt) < amtToAdj)){
        		alert('The difference amount cannot be more than (total amount - transaction amount). Please select single bill for part payment.');
        		self.user.amtAdj = amtToAdj;
        	}else if(rowData.length == 1 && parseFloat(adjAmt) == 0){
        		self.user.balAmt = amtToAdj;
        		console.log('self.user.balAmt'+self.user.balAmt);
        	}else if(rowData.length == 1 && parseFloat(adjAmt) > 0 && parseFloat(adjAmt) < amtToAdj){
        		alert('The amount can either be adjusted or would be recorded as part payment, but cannot be both.');
        		self.user.amtAdj = amtToAdj;
        	}
        };
        
        self.activate = function(payType){
        	if(payType == 'cash'){
        		$('#cheqNo').attr('disabled','disabled');
        		$('#cheqDate').attr('disabled','disabled');
        		self.user.actType = true;
        	}else{
        		$('#cheqNo').removeAttr('disabled');
        		$('#cheqDate').removeAttr('disabled');
        		self.user.actType = false;
        	}
        };
        
        self.activate('cash');
    }
]);