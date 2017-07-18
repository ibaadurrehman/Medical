// declare modules
angular.module('Authentication', []);
angular.module('Home', ['ngTouch','720kb.datepicker', 'ui.grid', 'ui.grid.edit', 'ui.grid.cellNav','ui.grid.validate']);

angular.module('MedicalApp', ['Authentication',
    'ngRoute',
    'ngCookies',
    'ngSanitize',
    'ngTouch',
    'ngAnimate',
    'ui.select',
    '720kb.datepicker',
    'ui.grid',
    'ui.grid.edit',
    'ui.grid.cellNav',
    'ui.grid.saveState', 
    'ui.grid.selection',
    'ui.grid.validate',
    'Home'
])

.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/home', {
            controller: 'homeController',
            templateUrl: '/Medical/collection/jsp/Home.jsp'
        })
        .when('/manufacturer', {
            controller: 'manController',
            templateUrl: '/Medical/collection/jsp/Manufacturer.jsp'
        })
        .when('/distributor', {
            controller: 'distributorController',
            templateUrl: '/Medical/collection/jsp/Distributor.jsp'
        })
        .when('/customer', {
            controller: 'customerController',
            templateUrl: '/Medical/collection/jsp/Customer.jsp'
        })
        .when('/tax', {
            controller: 'taxController',
            templateUrl: '/Medical/collection/jsp/Tax.jsp'
        })
        .when('/medicine', {
            controller: 'productController',
            templateUrl: '/Medical/collection/jsp/Product.jsp'
        })
        .when('/bank', {
            controller: 'bankController',
            templateUrl: '/Medical/collection/jsp/Bank.jsp'
        })
        .when('/purchaseInvoice', {
            controller: 'purchaseController',
            templateUrl: '/Medical/collection/jsp/PurchaseInvoice.jsp'
        })
        .when('/salesInvoice', {
            controller: 'salesController',
            templateUrl: '/Medical/collection/jsp/SalesInvoice.jsp'
        })
        .when('/purchasePayment', {
            controller: 'purPayController',
            templateUrl: '/Medical/collection/jsp/PurchasePayment.jsp'
        })
        .when('/salesPayment', {
            controller: 'salPayController',
            templateUrl: '/Medical/collection/jsp/SalesPayment.jsp'
        })
        .when('/stockMinus', {
            controller: 'salesController',
            templateUrl: '/Medical/collection/jsp/StockMinus.jsp'
        })
        .when('/cashDeposit', {
            controller: 'salPayController',
            templateUrl: '/Medical/collection/jsp/BankDeposit.jsp'
        })
        .when('/stockReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/StockReport.jsp'
        })
        .when('/bankStatement', {
            controller: 'bankController',
            templateUrl: '/Medical/collection/jsp/BankStatement.jsp'
        })
        .when('/balanceReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/BalanceReport.jsp'
        })
        .when('/purSalesReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/PurSalesReport.jsp'
        })
        .when('/prodWiseReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/ProdWiseReport.jsp'
        })
        .when('/demandReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/DemandReport.jsp'
        })
        .when('/manufWiseReport', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/ManufWiseReport.jsp'
        })
        .when('/ledger', {
            controller: 'reportController',
            templateUrl: '/Medical/collection/jsp/Ledger.jsp'
        });
}])

.directive('nextOnEnter', function () {
    return {
        restrict: 'A',
        link: function ($scope, selem, attrs) {
            selem.bind('keydown', function (e) {
                var code = e.keyCode || e.which;
                if (code === 13) {
                    e.preventDefault();
                    var pageElems = document.querySelectorAll('input, select, textarea'),
                        elem = e.srcElement,
                        focusNext = false,
                        len = pageElems.length;
                    for (var i = 0; i < len; i++) {
                        var pe = pageElems[i];
                        if (focusNext) {
                            if (pe.style.display !== 'none') {
                                pe.focus();
                                break;
                            }
                        } else if (pe === e.srcElement) {
                            focusNext = true;
                        }
                    }
                }
            });
        }
    };
})
 
.run(['$rootScope', '$location', '$cookieStore', '$http', '$window',
    function ($rootScope, $location, $cookieStore, $http, $window) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        console.log("URL="+$location.host()+":"+$location.port());
        console.log($cookieStore.put('globalUrl', "http://"+$location.host()+":"+$location.port()));
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if (!$rootScope.globals.currentUser) {
            	var url = "http://" + $window.location.host + "/Medical/WEB-INF/views/IndexPage.jsp";
                $window.location.href = url;
            }
        });
    }]);