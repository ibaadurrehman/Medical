'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('bankController', ['$scope', 'bankService',
    function ($scope, bankService) {
		var self = this;
	    self.user={bankId:'0',bankName:'',address:'',phoneNo:'',openingBal:0,bankCode:'',bankType:1};
	    self.bankStateData = {bankId:'',fromDate:'', toDate:''};
	    self.users=[];
	    self.user.bankCodeArr = {bankCode:''};
	    
	    self.selfBankList = [];
	    
	    var dataGrid = [];
	    var data = {"data": []};
	    
	    $scope.gridOptions = { enableSorting: false,
	    					   enableFiltering:false,	
	    					   data: dataGrid,
	    					   onRegisterApi: function(gridApi) {
	    						      console.log('Loaded Grid API 1');
	    						      $scope.gridApi = gridApi;
	    						}
	    					};
		
	    $scope.gridOptions.columnDefs = [
	                             		 { name: 'transDate', displayName:'Date', enableCellEdit: false, width: '15%' },
	                             		 { name: 'mode', displayName:'Mode', enableCellEdit: false, width: '15%' },
	                             		 { name: 'particulars', displayName: 'Particulars', enableCellEdit: false, width: '20%'},
	                             		 { name: 'chequeNo', displayName: 'Cheque no', enableCellEdit: false, width: '10%'},
	                             		 { name: 'deposit', displayName:'Deposit', enableCellEdit: false, width: '12%' },
	                             		 { name: 'withdrawl', displayName:'Withdrawl', enableCellEdit: false, width: '12%' },
	                             		 { name: 'balance', displayName:'Balance', enableCellEdit: false, width: '12%' }
	                             	];
	    
	    self.fetchBankList = function(){
	    	bankService.fetchBankList()
            .then(
                 function(d) {
                      self.users = d;
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
        
        self.fetchStatement = function(){
        	self.bankStateData.bankId = self.bankData.bankId;
        	
        	bankService.fetchStatement(self.bankStateData)
            .then(
                 function(d) {
                	 $scope.gridOptions.data = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching statement');
                 }
            );
        };
        
        self.addBank = function(user){
        	bankService.addBank(user)
            .then(
            		function(d){
            			self.fetchBankList();
            			self.resetBank();
            			self.getBankId();
            			alert("Bank added successfully...");
            		}
                 ,
                 function(errResponse){
                	  alert("Bank already exist...");
                      console.error('Duplicate bank');
                 }
            );
        };
        
        self.updateBank = function(user){
    		if (confirm(user.bankName+" balance would get updated to "+user.openingBal) == true){
    			bankService.updateBank(user)
                .then(
                		function(d){
                          self.fetchBankList();
                          self.resetBank();
                          self.getBankId();
                          alert("Bank updated successfully...");
                		}
                     ,
                     function(errResponse){
                          console.error('Error while updating bank');
                     }
                );
    		} 
        };
        
        self.deleteBank = function(bankId){
        	bankService.deleteBank(bankId)
            .then(
        		 function(d){
	    			self.fetchBankList();
	    			self.resetBank();
	    			self.getBankId();
	    			alert("Bank deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                      console.error('Error while deleting bank');
                 }
            );
        };
        
        self.resetBank = function(){
        	self.user.bankId='0';
        	self.user.bankName='';
        	self.user.address='';
        	self.user.phoneNo='';
        	self.user.bankCode='';
        	self.user.bankType=1;
        	self.user.openingBal=0;
        };
        
        self.updateBankModel = function(user){
        	self.user = user;
        };
        
        self.getBankId = function(){
        	bankService.getBankId()
        	.then(
           		 function(d){
           			self.user.bankCode = d;
   	    			console.info ("self.user.bankCode="+d);
           		 }
                 ,
                 function(errResponse){
                     console.error('Error while fetching bank code.');
                 }
            );
        };
        self.getBankId();
    }
]);