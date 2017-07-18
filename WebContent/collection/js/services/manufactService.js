'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('manufactService', ['$http', '$q', function($http, $q){
	
	return {
		fetchManufactList: function(){
			return $http.get('http://localhost:8080/Medical/manufact/listManufact')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching manufacturers');
                        return $q.reject(errResponse);
                    }
            );
		},
		initiateManufact: function(){
			return $http.get('http://localhost:8080/Medical/manufact')
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while intializing manufacturers');
                        return $q.reject(errResponse);
                    }
            );
		},
		addManufact: function(user){
			console.info(JSON.stringify(user));
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/manufact/addManufact',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding manufacturers');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateManufact: function(user){
			console.info(JSON.stringify(user));
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/manufact/updateManufact',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating manufacturers');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteManufact: function(id){
			return $http.delete('http://localhost:8080/Medical/manufact/deleteManufact/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating manufacturers');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);