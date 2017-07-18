'use strict';
 
homeApp.factory('salesService', ['$http', '$q', '$cookieStore', function($http, $q, $cookieStore){
	
	return {
		fetchProdPckgList: function(prodName,manId){
			console.log('sales url='+$cookieStore.get('globalUrl'));
			return $http.get('http://localhost:8080/Medical/sales/prodPckgList/'+prodName+'/'+manId)
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
		getNextBillNo: function(){
			return $http.get('http://localhost:8080/Medical/sales/getNextBillNo')
	        .then(
                    function(response){
                    	console.info("Data received"+response.data);
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching next bill no');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchProductList: function(prodId){
			return $http.get('http://localhost:8080/Medical/sales/productList/'+prodId)
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
		fetchSalesList: function(){
			return $http.get('http://localhost:8080/Medical/sales/fetchSalesList')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching sales list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchSales: function(salesId){
			return $http.get('http://localhost:8080/Medical/sales/fetchSales/'+salesId)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching sales record');
                        return $q.reject(errResponse);
                    }
            );
		},
		addSales: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/sales/addSales',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding sales');
                        return $q.reject(errResponse);
                    }
            );
		},
		submitSales: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/sales/submitSales',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding sales');
                        return $q.reject(errResponse);
                    }
            );
		},
		printBill: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/sales/printBill',data)
				.then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while adding sales');
                        return $q.reject(errResponse);
                    }
            );
		},
		stockMinus: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/sales/stockMinus',data)
			.then(
                function(response){
                    return response.data;
                }, 
                function(errResponse){
                    console.error('Error while deducting stock');
                    return $q.reject(errResponse);
                }
			);
		},
		deleteSales: function(id){
			return $http.delete('http://localhost:8080/Medical/sales/deleteSales/'+id)
	        .then(
                    function(response){
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while deleting sales');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);