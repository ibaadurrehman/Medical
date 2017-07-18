'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('bankService', ['$http', '$q', function($http, $q){
	
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
		fetchStatement: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/bank/statement',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching statement');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchPartyBankList: function(){
			return $http.get('http://localhost:8080/Medical/bank/partyListBank')
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
		fetchSelfBankList: function(){
			return $http.get('http://localhost:8080/Medical/bank/selfListBank')
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
		addBank: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/bank/addBank',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding bank');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateBank: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/bank/updateBank',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating bank');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteBank: function(id){
			return $http.delete('http://localhost:8080/Medical/bank/deleteBank/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating bank');
                        return $q.reject(errResponse);
                    }
            );
		},
		getBankId: function(){
			return $http.get('http://localhost:8080/Medical/bank/getBankId')
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