'use strict';

var homeApp = angular.module('Home',['ngTouch','720kb.datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav']);

homeApp.controller('homeController',
    ['$scope',
    function ($scope) {
    	$scope.MasterNav = false;
		$scope.PurchaseNav = false;
		$scope.SalesNav = false;
		$scope.BankingNav = false;
		$scope.AccountsNav = false;
		$scope.ReportsNav = false;
	
		$scope.activate = function(menu){
			if(menu === 'Master'){
			    $scope.MasterNav = true;
				$scope.PurchaseNav = false;
				$scope.SalesNav = false;
				$scope.BankingNav = false;
				$scope.AccountsNav = false;
				$scope.ReportsNav = false;
			}else if(menu === 'Purchase'){
			    $scope.MasterNav = false;
				$scope.PurchaseNav = true;
				$scope.SalesNav = false;
				$scope.BankingNav = false;
				$scope.AccountsNav = false;
				$scope.ReportsNav = false;
			}else if(menu === 'Sales'){
			    $scope.MasterNav = false;
				$scope.PurchaseNav = false;
				$scope.SalesNav = true;
				$scope.BankingNav = false;
				$scope.AccountsNav = false;
				$scope.ReportsNav = false;
			}else if(menu === 'Banking'){
			    $scope.MasterNav = false;
				$scope.PurchaseNav = false;
				$scope.SalesNav = false;
				$scope.BankingNav = true;
				$scope.AccountsNav = false;
				$scope.ReportsNav = false;
			}else if(menu === 'Accounts'){
			    $scope.MasterNav = false;
				$scope.PurchaseNav = false;
				$scope.SalesNav = false;
				$scope.BankingNav = false;
				$scope.AccountsNav = true;
				$scope.ReportsNav = false;
			}else if(menu === 'Reports'){
			    $scope.MasterNav = false;
				$scope.PurchaseNav = false;
				$scope.SalesNav = false;
				$scope.BankingNav = false;
				$scope.AccountsNav = false;
				$scope.ReportsNav = true;
			}else{
			    $scope.MasterNav = false;
				$scope.PurchaseNav = false;
				$scope.SalesNav = false;
				$scope.BankingNav = false;
				$scope.AccountsNav = false;
				$scope.ReportsNav = false;
			}
		};
    }]);