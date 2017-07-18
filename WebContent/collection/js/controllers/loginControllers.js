'use strict';

angular.module('Authentication')
 
.controller('LoginController',
    ['$scope', '$rootScope', '$window', '$log', '$location', 'AuthenticationService',
    function ($scope, $rootScope, $window, $log, $location, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();
 
        $scope.login = function () {
            $scope.dataLoading = true;

            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    var url = "http://" + $window.location.host + "/Medical/collection/jsp/Home.jsp";
                    $log.log(url);
                    $window.location.href = url;
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }]);