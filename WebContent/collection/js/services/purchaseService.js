'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('purchaseService', ['$http', '$q', function($http, $q){
	
	return {
		fetchProdPckgList: function(prodName,manId){
			return $http.get('http://localhost:8080/Medical/purchase/prodPckgList/'+prodName+'/'+manId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product package list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchProductList: function(prodId){
			return $http.get('http://localhost:8080/Medical/purchase/productList/'+prodId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product package list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchPurchaseList: function(){
			return $http.get('http://localhost:8080/Medical/purchase/fetchPurList')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching purchase list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchPurchase: function(purId){
			return $http.get('http://localhost:8080/Medical/purchase/fetchPurchase/'+purId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching purchase record');
                        return $q.reject(errResponse);
                    }
            );
		},
		addPurchase: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/purchase/addPurchase',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding purchase');
                        return $q.reject(errResponse);
                    }
            );
		},
		submitPurchase: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/purchase/submitPurchase',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding purchase');
                        return $q.reject(errResponse);
                    }
            );
		},
		deletePurchase: function(id){
			return $http.delete('http://localhost:8080/Medical/purchase/deletePurchase/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while deleting purchase');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);