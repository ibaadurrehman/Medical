'use strict';

//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])
 
homeApp.controller('productController', ['$scope', 'productService','manufactService','taxService',
    function ($scope, productService, manufactService, taxService) {
		var self = this;
	    self.user={prodId:'',prodName:'',manId:0,manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
	    self.users=[];
	    self.manufactData={manId:0,manName:'',address:'',phoneNo:'',custCare:''};;
	    self.prodData={id:0,prodId:'',prodName:'',manId:0,manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
	    self.taxData={taxId:0,taxName:'',taxPerc:0};
	    self.maufactList=[];
	    self.taxList=[];
	    self.prodList=[];
	    
	    self.fetchProdList = function(manId){
	    	productService.fetchProdList(manId)
            .then(
                 function(d) {
                	  self.fetchProdNameList(manId);
                      self.users = d;
                      self.user.manId = self.manufactData.manId;
                      self.user.manName = self.manufactData.manName;
                      self.prodData.prodName = '';
                 },
                 function(errResponse){
                      console.error('Error while fetching product');
                 }
            );
        };
        
        self.fetchProdNameList = function(manId){
	    	productService.fetchProdNameList(manId)
            .then(
                 function(d) {
                      self.prodList = d;
                 },
                 function(errResponse){
                      console.error('Error while fetching product');
                 }
            );
        };
        
        self.fetchProdDtlList = function(prodName){
	    	productService.fetchProdDtlList(prodName)
            .then(
                 function(d) {
                      self.users = d;
                      self.user.prodName = prodName;
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
                      console.info("fetched tax");
                 },
                 function(errResponse){
                      console.error('Error while fetching tax');
                 }
            );
        };
        
        self.fetchTaxList();
        
        self.setProdDetails = function($select){
        	var search = $select.search,
        	   list = angular.copy($select.items), 
        	   FLAG = -1;
        	//remove last user input
        	list = list.filter(function(item) { 
        	   return item.id !== FLAG; 
        	});
        	 
        	if (!search) {
        	   //use the predefined list
        	   $select.items = list;
        	}
        	else {
        	   //manually add user input and set selection
        	   var userInputItem = {
        			   id:FLAG,
        			   prodId:'',
        			   prodName:search,
        			   pckSize:'',
        			   mrp:0,
        			   rate:0,
        			   prodDtlId:0,
        			   taxId:0,
        			   taxPerc:0
        	   };
        	   $select.items = [userInputItem].concat(list);
        	   $select.selected = userInputItem;
        	}
        };
        
        self.addTax = function(taxId){
			self.user.taxId = taxId;
		};
		
        self.addProduct = function(user){
        	productService.addProduct(user)
            .then(
            		function(d){
            			self.fetchProdList(user.manId);
            			self.resetProduct();
            			alert("Product added successfully...");
            		}
                 ,
                 function(errResponse){
                	 alert("Product already exist...");
                      console.error('Duplicate product');
                 }
            );
        };
        
        self.updateProduct = function(user){
        	productService.updateProduct(user)
            .then(
            		function(d){
                      self.fetchProdList(user.manId);
            		  self.resetProduct();
                      alert("Product updated successfully...");
            		}
                 ,
                 function(errResponse){
                	  alert("Product with same package size, MRP and Rate already exist....");
                      console.error('Error while updating product');
                 }
            );
        };
        
        self.deleteProduct = function(user){
        	productService.deleteProduct(user.prodId,user.prodDtlId)
            .then(
        		 function(d){
	    			self.fetchProdList(self.user.manId);
	    			self.resetProduct();
	    			alert("Product deleted successfully...");
        		 }
                 ,
                 function(errResponse){
                	 alert('Product is associated with either Purchase or Sales Bill, cannot be deleted');
                     console.error('Error while deleting product');
                 }
            );
        };
        
        self.resetProduct = function(){
        	self.user.prodId='';
        	self.user.prodName='';
        	self.user.manId=0;
        	self.user.manName='';
        	self.user.prodDtlId=0;
        	self.user.taxId=0;
        	self.user.taxPerc=0;
        	self.user.mrp=0;
        	self.user.rate=0;
        	self.user.pckSize='';
        	self.manufactData={manId:0,manName:'',address:'',phoneNo:'',custCare:''};;
    	    self.prodData={prodId:'',prodName:'',manId:0,manName:'',pckSize:'',mrp:0,rate:0,prodDtlId:0,taxId:0,taxPerc:0};
    	    self.taxData={taxId:0,taxName:'',taxPerc:0};
        };
        
        self.updateProductModel = function(user){
        	self.user = user;
        	self.manufactData.manName = self.user.manName;
        	self.prodData.prodName = self.user.prodName;
        	self.taxData.taxPerc = self.user.taxPerc;
        };
    }
]);