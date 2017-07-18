'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('manController', ['$scope', 'manufactService',
    function ($scope, manufactService) {
		var self = this;
	    self.user={manId:'',manName:'',address:'',phoneNo:'',custCare:''};
	    self.users=[];
	    
	    self.initiateManufact = function(){
	    	manufactService.initiateManufact()
            .then(
                 function(d) {
                      console.log('Controller called.....');
                 },
                 function(errResponse){
                      console.error('Error while fetching Manufacturers');
                     }
             );
        };
        
        self.fetchManufactList = function(){
	    	manufactService.fetchManufactList()
            .then(
                 function(d) {
                	  console.info("Data received in controller");
                      self.users = d;
                      console.info(self.users);
                      //self.users = self.arrangeList(self.users);
                 },
                 function(errResponse){
                      console.error('Error while fetching Manufacturers');
                 }
            );
        };
        
        self.fetchManufactList();
        
        self.arrangeList = function(list){
        	for(var i=0; i < list.length; i++){
        		var count=i+1;
        		list[i].srNo = new String(count);
        	}
        	return list;
        };
        
        self.addManufact = function(user){
        	manufactService.addManufact(user)
            .then(
            		function(d){
            			self.fetchManufactList();
            			self.resetManufact();
            			alert("Manufacturer added successfully...");
            			console.info("Manufacturer added successfully...");
            		}
                 ,
                 function(errResponse){
                	 alert("Manufacturer already present...");
                      console.error('Duplicate Manufacturer');
                 }
            );
        };
        
        self.updateManufact = function(user){
        	console.info("RowIndex clicked"+user.manId);
        	manufactService.updateManufact(user)
            .then(
            		function(d){
                      self.fetchManufactList();
                      self.resetManufact();
                      alert("Manufacturer updated successfully...");
                      console.info("Manufacturer updated successfully...");
            		}
                 ,
                 function(errResponse){
                      console.error('Error while updating Manufacturer');
                 }
            );
        };
        
        self.deleteManufact = function(manId){
        	manufactService.deleteManufact(manId)
            .then(
        		 function(d){
	    			self.fetchManufactList();
	    			self.resetManufact();
	    			alert("Manufacturer deleted successfully...");
	    			console.info("Manufacturer deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                      console.error('Error while deleting Manufacturer');
                 }
            );
        };
        
        self.resetManufact = function(){
        	self.user.manId='';
        	self.user.manName='';
        	self.user.address='';
        	self.user.phoneNo='';
        	self.user.custCare='';
        };
        
        self.updateModel = function(user){
        	console.info("RowIndex clicked"+user.id);
        	self.user = user;
        };
    }
]);