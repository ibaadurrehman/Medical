'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('purPayService', ['$http', '$q', function($http, $q){
	
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
		fetchDistributorList: function(){
			return $http.get('http://localhost:8080/Medical/distributor/listDistributor')
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
		fetchBillDetails: function(distId){
			return $http.get('http://localhost:8080/Medical/transaction/fetchBillDetails/'+distId)
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
			return $http.post('http://localhost:8080/Medical/transaction/addPurTransaction',data)
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