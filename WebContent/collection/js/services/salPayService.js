'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('salPayService', ['$http', '$q', function($http, $q){
	
	return {
		fetchBankList: function(){
			return $http.get('http://localhost:8080/Medical/bank/listBank')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching bank');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchCustomerList: function(){
			return $http.get('http://localhost:8080/Medical/customer/listCustomer')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching distributors');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchBillDetails: function(custId){
			return $http.get('http://localhost:8080/Medical/salesTransaction/fetchSalBillDetails/'+custId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching bill details for selected purchase');
                        return $q.reject(errResponse);
                    }
            );
		},
		addTransaction: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/salesTransaction/addSalTransaction',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding transaction');
                        return $q.reject(errResponse);
                    }
            );
		},
		addDeposit: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/salesTransaction/addCashDeposit',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding transaction');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);