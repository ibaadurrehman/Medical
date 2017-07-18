'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('taxController', ['$scope', 'taxService',
    function ($scope, taxService) {
		var self = this;
	    self.user={taxId:0,taxName:'',taxPerc:''};
	    self.users=[];
	    
	    self.fetchTaxList = function(){
	    	taxService.fetchTaxList()
            .then(
                 function(d) {
                      self.users = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching tax');
                 }
            );
        };
        
        self.fetchTaxList();
        
        self.addTax = function(user){
        	taxService.addTax(user)
            .then(
            		function(d){
            			self.fetchTaxList();
            			self.resetTax();
            			alert("Tax added successfully...");
            		}
                 ,
                 function(errResponse){
                	 alert("Tax Percentage already exist...");
                      console.error('Duplicate tax');
                 }
            );
        };
        
        self.updateTax = function(user){
        	taxService.updateTax(user)
            .then(
            		function(d){
                      self.fetchTaxList();
                      self.resetTax();
                      alert("Tax updated successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Error while updating tax');
                 }
            );
        };
        
        self.deleteTax = function(taxId){
        	taxService.deleteTax(taxId)
            .then(
        		 function(d){
	    			self.fetchTaxList();
	    			self.resetTax();
	    			alert("Tax deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                      console.error('Error while deleting tax');
                 }
            );
        };
        
        self.resetTax = function(){
        	self.user.taxId=0;
        	self.user.taxName='';
        	self.user.taxPerc='';
        };
        
        self.updateTaxModel = function(user){
        	self.user = user;
        };
    }
]);