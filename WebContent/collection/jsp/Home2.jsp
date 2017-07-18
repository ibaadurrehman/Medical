<!DOCTYPE html>
<html ng-app="MedicalApp">
  	<head>
    	<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport">
    	<title>Asiya Unani, Ayurvedic & Gen. Store</title>
		<link rel="shortcut icon" type="image/x-icon" href="/Medical/collection/themes/images/favicon.ico">
    	<link rel="stylesheet" href="/Medical/collection/css/bootstrap.min.css" />
    	<link rel="stylesheet" type="text/css" href="/Medical/collection/assets/css/propeller.min.css">
    	<link rel="stylesheet" type="text/css" href="/Medical/collection/components/datetimepicker/css/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" type="text/css" href="/Medical/collection/components/datetimepicker/css/pmd-datetimepicker.css" />
		<link rel="stylesheet" type="text/css" href="/Medical/collection/themes/css/propeller-theme.css" />
		<link rel="stylesheet" type="text/css" href="/Medical/collection/themes/css/propeller-admin.css">
    	<link rel="stylesheet" href="/Medical/collection/css/style.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/select.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/select2.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/selectize.default.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/ui-grid.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/angular-datepicker.css" />
    	<script src="/Medical/collection/js/jquery-2.2.2.min.js"></script>
    	<script src="/Medical/collection/js/bootstrap.min.js"></script>
	    <script src="/Medical/collection/js/angular.js"></script>
	    <script src="/Medical/collection/js/angular-route.js"></script>
	    <script src="/Medical/collection/js/angular-cookies.js"></script>
	    <script src="/Medical/collection/js/angular-sanitize.js"></script>
	    <script src="/Medical/collection/js/angular-touch.min.js"></script>
	    <script src="/Medical/collection/js/angular-animate.min.js"></script>
	    <script src="/Medical/collection/js/angular-datepicker.js"></script>
	    <script src="/Medical/collection/js/ui-grid.js"></script>
	    <script src="/Medical/collection/js/select.js"></script>
	    <script src="/Medical/collection/js/medical.js"></script>
	    <script src="/Medical/collection/js/controllers/homeControllers.js"></script>
	    <script src="/Medical/collection/js/controllers/loginServices.js"></script>
	    <script src="/Medical/collection/js/controllers/loginControllers.js"></script>
	    <script src="/Medical/collection/js/controllers/manufController.js"></script>
	    <script src="/Medical/collection/js/services/manufactService.js"></script>
	    <script src="/Medical/collection/js/controllers/distributorController.js"></script>
	    <script src="/Medical/collection/js/services/distributorService.js"></script>
	    <script src="/Medical/collection/js/controllers/customerController.js"></script>
	    <script src="/Medical/collection/js/services/customerService.js"></script>
	    <script src="/Medical/collection/js/controllers/taxController.js"></script>
	    <script src="/Medical/collection/js/services/taxService.js"></script>
	    <script src="/Medical/collection/js/controllers/bankController.js"></script>
	    <script src="/Medical/collection/js/services/bankService.js"></script>
	    <script src="/Medical/collection/js/controllers/productController.js"></script>
	    <script src="/Medical/collection/js/services/productService.js"></script>
	    <script src="/Medical/collection/js/controllers/purchaseController.js"></script>
	    <script src="/Medical/collection/js/services/purchaseService.js"></script>
	    <script src="/Medical/collection/js/controllers/salesController.js"></script>
	    <script src="/Medical/collection/js/services/salesService.js"></script>
	    <script src="/Medical/collection/js/controllers/purPayController.js"></script>
	    <script src="/Medical/collection/js/services/purPayService.js"></script>
	    <script src="/Medical/collection/js/controllers/salPayController.js"></script>
	    <script src="/Medical/collection/js/services/salPayService.js"></script>
	    <script src="/Medical/collection/js/controllers/reportController.js"></script>
	    <script src="/Medical/collection/js/services/reportService.js"></script>
	    
	    <!-- <script type="text/javascript">
	        $(document).ready(function() {
	    		var w = $(window).innerHeight()-63;
	    		$("#sideDivLeft").height(w);
	    		$("#sideDivRight").height(w);
	    		console.info("height="+$(window).innerHeight());
	    	});
	    </script> -->
  	</head>
  	<body>
		<div class="container-fluid headerDiv">
			 <div class="row">
			 	  <div class="col-sm-5 text-right">
				  	   Asiya Unani, Ayurvedic & Gen. Store
			 	  </div>
				  <div class="col-sm-offset-5 col-sm-1 logout" ><a style="color:white" href="#">Logout</a></div>
				  <div class="col-sm-1" style="float:right;text-align:right;">
  				  	<a href="#" class="">
                      <img src="../images/user.jpg" title="Profile" class="img-circle img-responsive" width="30" height="30"> 
                    </a>
				  </div>
			 </div>
		</div>
		<div class="container-fluid" ng-controller="homeController">
			 <div class="row">
			 	  <div class="col-sm-2 sidenav sideDivLeft" id="sideDivLeft">
				  	   <div class="row">
					   		<div class="well" >
								 <div style="float:none;padding-left:15%;padding-bottom:20px;">
								 	  <img src="../images/user.jpg" class="img-circle img-responsive" width="130" height="130">
								 </div>
								 <div class="userBody">
								 	  <div class="infoLinks">Md Ibaadur Rehman Shaikh</div>
									  <div class="infoLinks">Admin</div>
								 </div>
							</div>
							<div class="well dashBody" >
								 <div class="heading">
								 	  Dashboard
								 </div>
								 <div>
								 	  <div class="info">Total Purchase: 50000</div>
									  <div class="info">Total Sales: 20000</div>
									  <div class="info">Last Purchase by: Dabur Pvt ltd.</div>
									  <div class="info">Last Sales to: Bukhari Medical</div>
								 </div>
							</div>
  					   </div>
			 	  </div>
				  <div class="col-sm-10 sideDivRight" id="sideDivRight">
					  <div class="row">
					  	   <nav class="navbar navbar-default navbar-inverse navbarRow">
						   		<div class="container-fluid">
							   		<div class="navbar-header">
		            					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
		            				        <span class="icon-bar"></span>
		            				        <span class="icon-bar"></span>
		            				        <span class="icon-bar"></span>                        
		            			      	</button>
		              				</div>
		            				<div class="collapse navbar-collapse" id="myNavbar">
		    					   		<ul class="nav navbar-nav">
		    								<li><a href="" ng-click="activate('Master');">MASTER</a></li>
		    								<li><a href="" ng-click="activate('Purchase');">PURCHASE</a></li>
		    								<li><a href="" ng-click="activate('Sales');">SALES</a></li>
		    								<li><a href="" ng-click="activate('Accounts');">ACCOUNTS</a></li>
		    								<li><a href="" ng-click="activate('Reports');">REPORTS</a></li>
		    								<li><a href="" ng-click="activate('Admin');">ADMIN</a></li>
		    							</ul>
									</div>
								</div>
						   </nav>
						   <nav class="navbar navbarTrans" ng-show="MasterNav">
						   		<div class="dash">
							   		<ul class="nav nav-tabs">
		  								<li><a href="#manufacturer" ng-click="activate('');">Manufacturer</a></li>
		  								<li><a href="#distributor" ng-click="activate('');">Distributor</a></li>
		  								<li><a href="#customer" ng-click="activate('');">Customer</a></li>
										<li><a href="#medicine" ng-click="activate('');">Medicine</a></li>
										<li><a href="#tax" ng-click="activate('');">Tax</a></li>
										<li><a href="#bank" ng-click="activate('');">Bank</a></li>
		  							</ul>
								</div>
						   </nav>
						   <nav class="navbar navbarTrans" ng-show="PurchaseNav">
						   		<div class="dash">
								    <ul class="nav nav-tabs">
										<li><a href="#purchaseInvoice" ng-click="activate('');">Invoice</a></li>
										<li><a href="#purchasePayment" ng-click="activate('');">Payment</a></li>
										<li><a href="#" ng-click="activate('');">Freezed Invoice</a></li>
									</ul>
								</div>
						   </nav>
						   <nav class="navbar navbarTrans" ng-show="SalesNav">
						   		<div class="dash">
							   		<ul class="nav nav-tabs">
		  								<li><a href="#salesInvoice" ng-click="activate('');">Invoice</a></li>
		  								<li><a href="#salesPayment" ng-click="activate('');">Payment</a></li>
		  								<li><a href="#stockMinus" ng-click="activate('');">Stock Minus</a></li>
		  								<li><a href="#" ng-click="activate('');">Freezed Invoice</a></li>
		  							</ul>
								</div>
						   </nav>
						   <nav class="navbar navbarTrans" ng-show="AccountsNav">
						   		<div class="dash">
							   		<ul class="nav nav-tabs">
		  								<li><a href="#cashDeposit" ng-click="activate('');">Cash Deposit</a></li>
		  								<li><a href="#bankStatement" ng-click="activate('');">Bank Statement</a></li>
		  								<li><a href="#ledger" ng-click="activate('');">Ledger</a></li>
		  								<li><a href="#balanceReport" ng-click="activate('');">Balance Report</a></li>
		  							</ul>
								</div>
						   </nav>
						   <nav class="navbar navbarTrans" ng-show="ReportsNav">
						   		<div class="dash">
							   		<ul class="nav nav-tabs">
		  								<li><a href="#stockReport" ng-click="activate('');">Stock</a></li>
					            		<li><a href="#purSalesReport" ng-click="activate('');">Purchase/Sales Report</a></li>
					            		<li><a href="#prodWiseReport" ng-click="activate('');">Product Wise Details</a></li>
					            		<li><a href="#demandReport" ng-click="activate('');">Demand Report</a></li>
					            		<li><a href="#manufWiseReport" ng-click="activate('');">Manufacturer Wise Details</a></li>
		  							</ul>
								</div>
						   </nav>
					</div>
					<div class="row" style="margin-top:20px;">
						<div class="col-sm-12" ng-view></div>
					</div>
				  </div>
			  </div>
		</div>	
	</body>
</html>
