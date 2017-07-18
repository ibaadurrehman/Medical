'use strict';

homeApp.controller('salPayController', ['$scope', 'salPayService', 'customerService', 'bankService',
    function ($scope, salPayService, customerService, bankService) {
		var self = this;
		self.user={transPayType:1, custId:'', bankId:'',selfBankId:'', remarks:'', cheqNo:'', cheqDate:'', 
    			balAmt:0, transAmt:0, amtAdj:0, transDate:'',billList:null, amtPaid:0};
		self.users=[];
		self.billNoList=[];
	    self.billNoData={billNo:'',salessId:'',custId:'',transPayTypeId:0,transTypeId:0,amtPaid:0,balAmt:0,amtAdj:0,transAmt:0};
	    self.custList=[];
	    self.bankList=[];
	    self.selfBankList=[];
	    self.custData={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
	    self.bankData = {bankId:'0',bankName:'',address:'',phoneNo:'',bankCode:''};
	    self.selfBankData = {bankId:'0',bankName:'',address:'',phoneNo:'',bankCode:''};
	    self.depositData = {bankId:'0', bankName:'',transAmt:0,transDate:''};
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
			{ name: 'salesId', enableCellEdit: false, visible: false},
			{ name: 'billNo', displayName:'Bill No', enableCellEdit: false, width: '20%' },
			{ name: 'billDate', displayName:'Bill Date', enableCellEdit: false, width: '20%' },
			{ name: 'billAmt', displayName: 'Bill Amount', enableCellEdit: false, width: '20%'},
			{ name: 'amtPaid', displayName:'Amount Paid', enableCellEdit: false, width: '20%' },
			{ name: 'balAmt', displayName: 'Balance Amt', enableCellEdit: false, width: '20%'}
		];
	    
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
	    
     	self.fetchBankList = function(){
	    	bankService.fetchPartyBankList()
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
        
        self.fetchSelfBankList = function(){
	    	bankService.fetchSelfBankList()
            .then(
                 function(d) {
                      self.selfBankList = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching bank');
                 }
            );
        };
        
        self.fetchSelfBankList();

        self.fetchBillDetails = function(custId){
        	salPayService.fetchBillDetails(custId)
            .then(
            		function(d){
            			$scope.gridOptions.data = d;
            		}
                 ,
                 function(errResponse){
                	  alert("Error in fetching bill details for customer...");
                      console.error('error in fetching bill details');
                 }
            );
        };
        
        self.addTransaction = function(){
        	self.user.billList = $scope.gridApi.selection.getSelectedRows();
        	self.user.custId = self.custData.custId;
        	if(self.user.transPayType == '1'){
        		self.user.bankId = 0;
            	self.user.selfBankId = 0;
        	}else{
        		self.user.bankId = self.bankData.bankId;
            	self.user.selfBankId = self.selfBankData.bankId;
        	}

        	salPayService.addTransaction(self.user)
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
        
        self.addDeposit = function(){
        	self.depositData.bankId = self.bankData.bankId;
        	self.depositData.bankName = self.bankData.bankName;
        	
        	salPayService.addDeposit(self.depositData)
            .then(
            		function(d){
            			self.resetDeposit();
            			alert("Transaction recorded successfully...");
            		}
                 ,
                 function(errResponse){
                	  alert("Error in inserting transaction...");
                      console.error('Duplicate bank');
                 }
            );
        };
        
        self.resetDeposit = function(){
        	self.depositData = {bankId:'0', bankName:'',transAmt:0,transDate:''};
        };
        
        self.resetTransaction = function(){
        	self.user.transPayType = 1;
        	self.user.distId = "";
        	self.user.bankId = "";
        	self.user.selfBankId = "";
        	self.user.remarks = "";
        	self.user.cheqNo = "";
        	self.user.cheqDate = "";
        	self.user.balAmt = 0;
        	self.user.transAmt = 0;
        	self.user.amtAdj = 0;
        	self.user.amtPaid = 0;
        	self.user.transDate = date;
        	self.user.billList = null;
        	self.custData = undefined;
        	self.bankData = undefined;
        	self.selfBankData = undefined;
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