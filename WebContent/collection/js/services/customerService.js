'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('customerService', ['$http', '$q', function($http, $q){
	
	return {
		fetchCustomerList: function(){
			return $http.get('http://localhost:8080/Medical/customer/listCustomer')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching customers');
                        return $q.reject(errResponse);
                    }
            );
		},
		addCustomer: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/customer/addCustomer',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding customer');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateCustomer: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/customer/updateCustomer',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating customer');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteCustomer: function(id){
			return $http.delete('http://localhost:8080/Medical/customer/deleteCustomer/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating customer');
                        return $q.reject(errResponse);
                    }
            );
		},
		getCustomerId: function(){
			return $http.get('http://localhost:8080/Medical/customer/getCustomerId')
	        .then(
                    function(response){
                    	return response.data;
                    }, 
                    function(errResponse){
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);