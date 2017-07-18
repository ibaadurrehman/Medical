'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('distributorController', ['$scope', 'distributorService',
    function ($scope, distributorService) {
		var self = this;
	    self.user={distId:'',distName:'',address:'',emailId:'',phoneNo:'',vatNo:'',openingBal:''};
	    self.users=[];
	    
	    self.fetchDistributorList = function(){
	    	distributorService.fetchDistributorList()
            .then(
                 function(d) {
                	  console.info("Data received in controller");
                      self.users = d;
                      console.info(self.users);
                 },
                 function(errResponse){
                      console.error('Error while fetching distributors');
                 }
            );
        };
        
        self.fetchDistributorList();
        
        self.addDistributor = function(user){
        	distributorService.addDistributor(user)
            .then(
            		function(d){
            			self.fetchDistributorList();
            			self.resetDistributor();
            			alert("Distributor added successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Duplicate distributor');
                 }
            );
        };
        
        self.updateDistributor = function(user){
        	distributorService.updateDistributor(user)
            .then(
            		function(d){
                      self.fetchDistributorList();
                      self.resetDistributor();
                      alert("Distributor updated successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Error while updating distributor');
                 }
            );
        };
        
        self.deleteDistributor = function(distId){
        	distributorService.deleteDistributor(distId)
            .then(
        		 function(d){
	    			self.fetchDistributorList();
	    			self.resetDistributor();
	    			alert("Distributor deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                      console.error('Error while deleting distributor');
                 }
            );
        };
        
        self.resetDistributor = function(){
        	self.user.distId='';
        	self.user.distName='';
        	self.user.emailId='';
        	self.user.address='';
        	self.user.phoneNo='';
        	self.user.vatNo='';
        	self.user.openingBal='';
        };
        
        self.updateDistModel = function(user){
        	self.user = user;
        };
    }
]);