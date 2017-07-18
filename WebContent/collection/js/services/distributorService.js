'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('distributorService', ['$http', '$q', function($http, $q){
	
	return {
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
		addDistributor: function(user){
			console.info(JSON.stringify(user));
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/distributor/addDistributor',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding distributors');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateDistributor: function(user){
			console.info(JSON.stringify(user));
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/distributor/updateDistributor',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating distributors');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteDistributor: function(id){
			return $http.delete('http://localhost:8080/Medical/distributor/deleteDistributor/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating distributors');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);