<!DOCTYPE html>
<html ng-app="MedicalApp">
  	<head>
    	<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport">
    	<title>Asiya Unani, Ayurvedic & Gen. Store</title>
    	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
	    <script src="/Medical/collection/assets/js/propeller.min.js"></script>
	    <script src="/Medical/collection/themes/js/circles.min.js"></script>
	    <script src="/Medical/collection/themes/js/highcharts.js"></script>
		<script src="/Medical/collection/themes/js/highcharts-more.js"></script>
		<script type="text/javascript" language="javascript" src="/Medical/collection/components/datetimepicker/js/moment-with-locales.js"></script>
		<script type="text/javascript" language="javascript" src="/Medical/collection/components/datetimepicker/js/bootstrap-datetimepicker.js"></script> 
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
	    
	    <script>
			$(document).ready(function() {
				var sPath=window.location.pathname;
				var sPage = sPath.substring(sPath.lastIndexOf('/') + 1);
				$(".pmd-sidebar-nav").each(function(){
					$(this).find("a[href='"+sPage+"']").parents(".dropdown").addClass("open");
					$(this).find("a[href='"+sPage+"']").parents(".dropdown").find('.dropdown-menu').css("display", "block");
					$(this).find("a[href='"+sPage+"']").parents(".dropdown").find('a.dropdown-toggle').addClass("active");
					$(this).find("a[href='"+sPage+"']").addClass("active");
				});
			});
			
			(function() {
			  "use strict";
			  var toggles = document.querySelectorAll(".c-hamburger");
			  for (var i = toggles.length - 1; i >= 0; i--) {
			    var toggle = toggles[i];
			    toggleHandler(toggle);
			  };
			  function toggleHandler(toggle) {
			    toggle.addEventListener( "click", function(e) {
			      e.preventDefault();
			      (this.classList.contains("is-active") === true) ? this.classList.remove("is-active") : this.classList.add("is-active");
			    });
			  }

			})();
		</script>
  	</head>
  	<body>
		<nav class="navbar navbar-inverse navbar-fixed-top pmd-navbar pmd-z-depth">
			<div class="container-fluid">
				<div class="pmd-navbar-right-icon pull-right navigation">
					<!-- Notifications -->
		            <div class="dropdown notification icons pmd-dropdown">
					
						<a href="javascript:void(0)" title="Notification" class="dropdown-toggle pmd-ripple-effect"  data-toggle="dropdown" role="button" aria-expanded="true">
							<div data-badge="3" class="material-icons md-light pmd-sm pmd-badge  pmd-badge-overlap">notifications_none</div>
						</a>
					
						<div class="dropdown-menu dropdown-menu-right pmd-card pmd-card-default pmd-z-depth" role="menu">
							<!-- Card header -->
							<div class="pmd-card-title">
								<div class="media-body media-middle">
									<a href="#" class="pull-right">3 new notifications</a>
									<h3 class="pmd-card-title-text">Notifications</h3>
								</div>
							</div>
							
							<!-- Notifications list -->
							<ul class="list-group pmd-list-avatar pmd-card-list">
								<li class="list-group-item" style="display:none">
									<p class="notification-blank">
										<span class="dic dic-notifications-none"></span> 
										<span>You don´t have any notifications</span>
									</p>
								</li>
								<li class="list-group-item unread">
									<a href="javascript:void(0)">
										<div class="media-left">
											<span class="avatar-list-img40x40">
												<img alt="40x40" data-src="holder.js/40x40" class="img-responsive" src="themes/images/profile-1.png" data-holder-rendered="true">
											</span>
										</div>
										<div class="media-body">
											<span class="list-group-item-heading"><span>Prathit</span> posted a new challanegs</span>
											<span class="list-group-item-text">5 Minutes ago</span>
										</div>
									</a>
								</li>
								<li class="list-group-item">
									<a href="javascript:void(0)">
										<div class="media-left">
											<span class="avatar-list-img40x40">
												<img alt="40x40" data-src="holder.js/40x40" class="img-responsive" src="themes/images/profile-2.png" data-holder-rendered="true">
											</span>
										</div>
										<div class="media-body">
											<span class="list-group-item-heading"><span>Keel</span> Cloned 2 challenges.</span>
											<span class="list-group-item-text">15 Minutes ago</span>
										</div>
									</a>
								</li>
								<li class="list-group-item unread">
									<a href="javascript:void(0)">
										<div class="media-left">
											<span class="avatar-list-img40x40">
												<img alt="40x40" data-src="holder.js/40x40" class="img-responsive" src="themes/images/profile-3.png" data-holder-rendered="true">
											</span>
										</div>
									
										<div class="media-body">
											<span class="list-group-item-heading"><span>John</span> posted new collection.</span>
											<span class="list-group-item-text">25 Minutes ago</span>
										</div>
									</a>
								</li>
								<li class="list-group-item unread">
									<a href="javascript:void(0)">
										<div class="media-left">
											<span class="avatar-list-img40x40">
												<img alt="40x40" data-src="holder.js/40x40" class="img-responsive" src="themes/images/profile-4.png" data-holder-rendered="true">
											</span>
										</div>
										<div class="media-body">
											<span class="list-group-item-heading"><span>Valerii</span> Shared 5 collection.</span>
											<span class="list-group-item-text">30 Minutes ago</span>
										</div>
									</a>
								</li>
							</ul><!-- End notifications list -->
		
						</div>
						
						
		            </div> <!-- End notifications -->
				</div>
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<a href="javascript:void(0);" class="btn btn-sm pmd-btn-fab pmd-btn-flat pmd-ripple-effect pull-left margin-r8 pmd-sidebar-toggle"><i class="material-icons">menu</i></a>	
				  	<a href="#" class="navbar-brand"><span style="color:#FFFFFF;font-weight: bold;">ASIYA UNANI, AYURVEDIC & GEN. STORE</span></a>
				</div>
			</div>
		
		</nav>
		<!-- Sidebar Starts -->
		<div class="pmd-sidebar-overlay"></div>
		
		<!-- Left sidebar -->
		<aside class="pmd-sidebar sidebar-default pmd-sidebar-slide-push pmd-sidebar-left pmd-sidebar-open bg-fill-darkblue sidebar-with-icons" role="navigation">
			<ul class="nav pmd-sidebar-nav">
				
				<!-- User info -->
				<li class="dropdown pmd-dropdown pmd-user-info visible-xs visible-md visible-sm visible-lg">
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" aria-expandedhref="javascript:void(0);">
						<div class="media-left">
							<img src="/Medical/collection/themes/images/user-icon.png" alt="New User">
						</div>
						<div class="media-body media-middle">Md Ibaadur Rehman Shaikh</div>
						<div class="media-right media-middle"><i class="dic-more-vert dic"></i></div>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Logout</a></li>
					</ul>
				</li><!-- End user info -->
				
				<li> 
					<a class="pmd-ripple-effect" href="index.html">	
						<i class="media-left media-middle">
							<svg version="1.1" x="0px" y="0px" width="19.83px" height="18px" viewBox="287.725 407.535 19.83 18" enable-background="new 287.725 407.535 19.83 18"
			 					xml:space="preserve">
								<g>
									<path fill="#C9C8C8" d="M307.555,407.535h-9.108v10.264h9.108V407.535z M287.725,407.535v6.232h9.109v-6.232H287.725z
				 						M296.834,415.271h-9.109v10.264h9.109V415.271z M307.555,419.303h-9.108v6.232h9.108V419.303z"/>
								</g>
							</svg>
						</i>
						<span class="media-body">Dashboard</span>
					</a> 
				</li>
				
				<li class="dropdown pmd-dropdown"> 
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" href="javascript:void(0);">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Master</span>
						<div class="media-right media-bottom"><i class="dic-more-vert dic"></i></div>
					</a> 
					<ul class="dropdown-menu">
						<li><a href="#manufacturer">Manufacturer</a></li>
						<li><a href="#distributor">Distributor</a></li>
						<li><a href="#customer"">Customer</a></li>
						<li><a href="#medicine">Medicine</a></li>
						<li><a href="#tax">Tax</a></li>
						<li><a href="#bank">Bank</a></li>
					</ul>
				</li>
				<li class="dropdown pmd-dropdown"> 
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" href="javascript:void(0);">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Purchase</span>
						<div class="media-right media-bottom"><i class="dic-more-vert dic"></i></div>
					</a> 
					<ul class="dropdown-menu">
						<li><a href="#purchaseInvoice">Invoice</a></li>
						<li><a href="#purchasePayment">Payment</a></li>
						<li><a href="#">Freezed Invoice</a></li>
					</ul>
				</li>
				
				<li class="dropdown pmd-dropdown"> 
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" href="javascript:void(0);">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Sales</span>
						<div class="media-right media-bottom"><i class="dic-more-vert dic"></i></div>
					</a> 
					<ul class="dropdown-menu">
						<li><a href="#salesInvoice">Invoice</a></li>
						<li><a href="#salesPayment">Payment</a></li>
						<li><a href="#">Freezed Invoice</a></li>
					</ul>
				</li>
				<li class="dropdown pmd-dropdown"> 
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" href="javascript:void(0);">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Accounts</span>
						<div class="media-right media-bottom"><i class="dic-more-vert dic"></i></div>
					</a> 
					<ul class="dropdown-menu">
						<li><a href="#cashDeposit">Cash Deposit</a></li>
						<li><a href="#bankStatement">Bank Statement</a></li>
						<li><a href="#ledger">Ledger</a></li>
						<li><a href="#balanceReport">Balance Report</a></li>
					</ul>
				</li>
		
				<li class="dropdown pmd-dropdown"> 
					<a aria-expanded="false" data-toggle="dropdown" class="btn-user dropdown-toggle media" data-sidebar="true" href="javascript:void(0);">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Reports</span>
						<div class="media-right media-bottom"><i class="dic-more-vert dic"></i></div>
					</a> 
					<ul class="dropdown-menu">
						<li><a href="#stockReport">Stock</a></li>
		           		<li><a href="#purSalesReport">Purchase/Sales Report</a></li>
		           		<li><a href="#prodWiseReport">Product Wise Details</a></li>
		           		<li><a href="#demandReport">Demand Report</a></li>
		           		<li><a href="#manufWiseReport">Manufacturer Wise Details</a></li>
					</ul>
				</li>
				<li> 
					<a class="pmd-ripple-effect" href="#">	
						<i class="media-left media-middle">
							<svg x="0px" y="0px" width="18px" height="18px" viewBox="288.64 337.535 18 18" enable-background="new 288.64 337.535 18 18" xml:space="preserve">
								<title>022-layout view</title>
								<desc>Created with Sketch.</desc>
								<g>
									<g>
										<path fill="#C9C8C8" d="M298.765,353.285v-2.25h3.375v-3.375h2.25v5.625H298.765z M290.89,347.66h2.25v3.375h3.375v2.25h-5.625
											V347.66z M296.515,339.785v2.25h-3.375v3.375h-2.25v-5.625H296.515z M295.39,348.785h4.5v-4.5h-4.5V348.785z M304.39,345.41h-2.25
											v-3.375h-3.375v-2.25h5.625V345.41z M288.64,355.535h18v-18h-18V355.535z"/>
									</g>
								</g>
								<text transform="matrix(1 0 0 1 -0.0154 1202.2578)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">Created by Richard Wearn</text>
								<text transform="matrix(1 0 0 1 -0.0154 1388.2891)" font-family="'HelveticaNeue-Bold'" font-size="186.0251">from the Noun Project</text>
							</svg>
						</i>
						<span class="media-body">Admin</span>
					</a> 
				</li>
			</ul>
		</aside><!-- End Left sidebar -->
		<!-- Sidebar Ends --> 
		<!--content area start-->
		<div id="content" class="pmd-content content-area dashboard">
			<div class="container-fluid" ng-view>
			</div>
		</div>
		<footer class="admin-footer">
			 <div class="container-fluid">
			 	<ul class="list-unstyled list-inline">
				 	<li>
						<span class="pmd-card-subtitle-text">Asiya &copy; <span class="auto-update-year"></span>. All Rights Reserved.</span>
						<h3 class="pmd-card-subtitle-text">Licensed under <a href="https://opensource.org/licenses/MIT" target="_blank">M Softwares</a></h3>
			        </li>
			        <!-- <li class="pull-right download-now">
						<a onClick="downloadPMDadmintemplate()" href="../../archive/pmd-admin-template-1.1.0.zip">
			           		 <div>
			                  <svg x="0px" y="0px" width="38px" height="32px" viewBox="0 0 38 32" enable-background="new 0 0 38 32" xml:space="preserve">
			<g>
				<path fill="#A5A4A4" d="M13.906,26.652l4.045,4.043c0.001,0,0.002,0.002,0.003,0.004l1.047,1.047l1.047-1.049
					c0.001,0,0.001,0,0.001,0l4.044-4.045c0.579-0.58,0.579-1.518,0-2.098c-0.579-0.578-1.519-0.578-2.096,0l-1.514,1.514V16.22
					c0-0.818-0.664-1.482-1.483-1.482c-0.818,0-1.482,0.664-1.482,1.482v9.85l-1.515-1.516c-0.29-0.289-0.669-0.434-1.048-0.434
					c-0.38,0-0.759,0.145-1.049,0.434C13.327,25.133,13.327,26.072,13.906,26.652z"/>
				<g>
					<g>
						<path fill="#A5A4A4" d="M8.453,26.363c-0.032,0-0.065,0-0.099-0.002C3.67,26.053,0,22.137,0,17.443
							c0-4.434,3.242-8.124,7.48-8.825c0.3-4.663,4.188-8.364,8.926-8.364c2.249,0,4.393,0.844,6.032,2.346
							c4.602-1.86,9.527-0.766,12.266,2.831c1.808,2.375,2.399,5.513,1.671,8.719C37.416,15.412,38,17.008,38,18.65
							c0,3.902-3.176,7.076-7.077,7.076c-1.221,0-2.428-0.32-3.492-0.926c-0.712-0.404-0.961-1.311-0.556-2.021
							c0.404-0.713,1.312-0.963,2.021-0.557c0.619,0.352,1.319,0.539,2.027,0.539c2.267,0,4.111-1.844,4.111-4.111
							c0-1.146-0.467-2.212-1.312-3.001l-0.673-0.627l0.264-0.881c0.769-2.574,0.416-5.094-0.969-6.913
							c-2.061-2.706-5.997-3.332-9.577-1.522l-1.045,0.528L20.966,5.34c-1.139-1.347-2.802-2.12-4.56-2.12
							c-3.297,0-5.979,2.683-5.979,5.979c0,0.21,0.01,0.416,0.033,0.619l0.186,1.648l-1.784-0.004
							c-3.215,0.003-5.896,2.685-5.896,5.983c0,3.135,2.453,5.752,5.584,5.957c0.817,0.055,1.436,0.76,1.382,1.576
							C9.88,25.762,9.228,26.363,8.453,26.363z"/>
					</g>
				</g>
			</g>
			</svg>
			           		 </div>
			            	 <div>
			              	 	<span class="pmd-card-subtitle-text">Version- 1.1.0</span>
			              	 	<h3 class="pmd-card-title-text">Download Now</h3>
			            	</div>
						</a>
			        </li> -->
			        <li class="pull-right for-support">
						<a href="mailto:mohammed.ibaadurrehman@gmail.com">
			          		<div>
								<svg x="0px" y="0px" width="38px" height="38px" viewBox="0 0 38 38" enable-background="new 0 0 38 38">
			<g><path fill="#A5A4A4" d="M25.621,21.085c-0.642-0.682-1.483-0.682-2.165,0c-0.521,0.521-1.003,1.002-1.524,1.523
					c-0.16,0.16-0.24,0.16-0.44,0.08c-0.321-0.2-0.683-0.32-1.003-0.521c-1.483-0.922-2.726-2.125-3.809-3.488
					c-0.521-0.681-1.002-1.402-1.363-2.205c-0.04-0.16-0.04-0.24,0.08-0.4c0.521-0.481,1.002-1.003,1.524-1.483
					c0.721-0.722,0.721-1.524,0-2.246c-0.441-0.44-0.842-0.842-1.203-1.202c-0.441-0.441-0.842-0.842-1.243-1.243
					c-0.642-0.642-1.483-0.642-2.165,0c-0.521,0.521-1.002,1.002-1.524,1.523c-0.481,0.481-0.722,1.043-0.802,1.685
					c-0.08,1.042,0.16,2.085,0.521,3.047c0.762,2.085,1.925,3.849,3.328,5.532c1.884,2.286,4.17,4.05,6.815,5.333
					c1.203,0.562,2.406,1.002,3.729,1.123c0.922,0.04,1.724-0.201,2.365-0.923c0.441-0.521,0.923-0.922,1.403-1.403
					c0.682-0.722,0.682-1.563,0-2.245C27.265,22.729,26.423,21.927,25.621,21.085z"/>
				<path fill="#A5A4A4" d="M32.437,5.568C28.869,2,24.098-0.005,19.005-0.005S9.182,2,5.573,5.568C2.005,9.177,0,13.908,0,19
					s1.965,9.823,5.573,13.432c3.568,3.568,8.34,5.573,13.432,5.573s9.823-1.965,13.431-5.573
					C39.854,25.014,39.854,12.985,32.437,5.568z M30.299,30.294c-3.003,3.045-7.021,4.695-11.293,4.695
					c-4.272,0-8.291-1.65-11.294-4.695C4.666,27.29,3.016,23.271,3.016,19c0-4.272,1.649-8.291,4.695-11.294
					c3.003-3.003,7.022-4.695,11.294-4.695c4.272,0,8.291,1.649,11.293,4.695C36.56,13.924,36.56,24.075,30.299,30.294z"/>
			</g></svg>
			            	</div>
			            	<div>
							  <span class="pmd-card-subtitle-text">For Support</span>
							  <h3 class="pmd-card-subtitle-text">Md Ibaadur Rehman Shaikh</h3>
							</div>
			            </a>
			        </li>
			    </ul>
			 </div>
		</footer> 
	</body>
</html>
