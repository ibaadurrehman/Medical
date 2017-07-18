'use strict';

homeApp.factory('reportService', ['$http', '$q', '$cookieStore', function($http, $q, $cookieStore){
	return {
		fetchAllStock: function(){
			return $http.get('http://localhost:8080/Medical/report/fetchAllStock')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product stock');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchStock: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchStock',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching product stock');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchAllDistBal: function(user){
			console.log(JSON.stringify(user));
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchAllDistBal',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching dist balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchDistBal: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchDistBal',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching dist balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchAllCustBal: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchAllCustBal',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching cust balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchCustBal: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchCustBal',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching cust balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchAllPurReport: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchAllPurReport',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching dist balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchDistPurReport: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchDistPurReport',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching dist balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchAllSalesReport: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchAllSalesReport',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching cust balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchCustSalesReport: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchCustSalesReport',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching cust balance report');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchFinYearList: function(){
			return $http.get('http://localhost:8080/Medical/report/fetchFinYear')
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching financial year list');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchDistLedger: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchDistLedger',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching distributor ledger');
                        return $q.reject(errResponse);
                    }
            );
		},
		fetchCustLedger: function(user){
			var data=JSON.parse(JSON.stringify(user));
			return $http.post('http://localhost:8080/Medical/report/fetchCustLedger',data)
	        .then(
                    function(response){
                    	console.info("Data received");
                        return response.data;
                    }, 
                    function(errResponse){
                        console.error('Error while fetching customer ledger');
                        return $q.reject(errResponse);
                    }
            );
		}
	};
}]);