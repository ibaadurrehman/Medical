'use strict';
 
//angular.module('Home',['ngTouch','datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav'])

homeApp.factory('productService', ['$http', '$q', function($http, $q){
	
	return {
		fetchProdList: function(manId){
			return $http.get('http://localhost:8080/Medical/product/listProduct/'+manId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchProdNameList: function(manId){
			return $http.get('http://localhost:8080/Medical/product/listProductName/'+manId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchStockProdList: function(manId){
			return $http.get('http://localhost:8080/Medical/product/listStockProduct/'+manId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchProdDtlList: function(prodName){
			return $http.get('http://localhost:8080/Medical/product/listProductDtl/'+prodName)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product list');
                        return $q.reject(errResponse);
                    }
            );
		},
		addProduct: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/product/addProduct',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding product');
                        return $q.reject(errResponse);
                    }
            );
		},
		updateProduct: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/product/updateProduct',data)
			.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while updating product');
                        return $q.reject(errResponse);
                    }
            );
		},
		deleteProduct: function(id,prodDtlId){
			return $http.delete('http://localhost:8080/Medical/product/deleteProduct/'+id+'/'+prodDtlId)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while deleting product');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);