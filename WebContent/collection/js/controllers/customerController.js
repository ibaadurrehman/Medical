'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('customerController', ['$scope', 'customerService',
    function ($scope, customerService) {
		var self = this;
	    self.user={custId:'',custName:'',ownerName:'',address:'',phoneNo:'',emailId:'',vatNo:'',openingBal:'',custCode:''};
	    self.users=[];
	    self.user.custCodeArr = {custCode:''};
	    
	    self.fetchCustomerList = function(){
	    	customerService.fetchCustomerList()
            .then(
                 function(d) {
                      self.users = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching customers');
                 }
            );
        };
        
        self.fetchCustomerList();
        
        self.addCustomer = function(user){
        	customerService.addCustomer(user)
            .then(
            		function(d){
            			self.fetchCustomerList();
            			self.resetCustomer();
            			self.getCustomerId();
            			alert("Customer added successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Duplicate customer');
                 }
            );
        };
        
        self.updateCustomer = function(user){
        	customerService.updateCustomer(user)
            .then(
            		function(d){
                      self.fetchCustomerList();
                      self.resetCustomer();
                      self.getCustomerId();
                      alert("Customer updated successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Error while updating customer');
                 }
            );
        };
        
        self.deleteCustomer = function(custId){
        	customerService.deleteCustomer(custId)
            .then(
        		 function(d){
	    			self.fetchCustomerList();
	    			self.resetCustomer();
	    			self.getCustomerId();
	    			alert("Customer deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                      console.error('Error while deleting customer');
                 }
            );
        };
        
        self.resetCustomer = function(){
        	self.user.custId='';
        	self.user.custName='';
        	self.user.address='';
        	self.user.phoneNo='';
        	self.user.vatNo='';
        	self.user.openingBal='';
        	self.user.emailId='';
        	self.user.custCode='';
        	self.user.ownerName='';
        };
        
        self.updateCustModel = function(user){
        	self.user = user;
        };
        
        self.getCustomerId = function(){
        	customerService.getCustomerId()
        	.then(
           		 function(d){
           			self.user.custCode = d;
   	    			console.info ("self.user.custCode="+d);
           		 }
                 ,
                 function(errResponse){
                     console.error('Error while fetching customer code.');
                 }
            );
        };
        self.getCustomerId();
    }
]);