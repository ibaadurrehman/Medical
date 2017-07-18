'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('taxService', ['$http', '$q', function($http, $q){
	
	return {
		fetchTaxList: function(){
			return $http.get('http://localhost:8080/Medical/tax/listTax')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching tax');
                        return $q.reject(errResponse);
                    }
            );
		},
		addTax: function(user){
			var data=JSON.parse(JSON.stringify(user));
			console.log("tax Data"+JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/tax/addTax',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding tax');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateTax: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/tax/updateTax',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating tax');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteTax: function(id){
			return $http.delete('http://localhost:8080/Medical/tax/deleteTax/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating tax');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);