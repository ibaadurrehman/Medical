<div class="ledgerForm" ng-controller="reportController as ctrl">
	<div class="row lineHeight">
		 <div class="form-group">
			 <div class="col-sm-3 text-right"><input type="radio" name="repType" ng-model="ctrl.user.repType" value="dist" checked ng-click="ctrl.activateLegderOpt('dist')">Distributor</div>
			 <div class="col-sm-3 text-left"><input type="radio" name="repType" ng-model="ctrl.user.repType" value="cust" ng-click="ctrl.activateLegderOpt('cust')">Party</div>
		 </div>
   	</div>
   	<div class="row lineHeight">
   		<div class="form-group">
			<div class="col-sm-2 text-right"><label class="control-label" for="distributor">Distributor:</label></div>
	 		<div class="col-sm-3 text-left">
	  			 <ui-select id="distributor" ng-disabled="ctrl.user.actDistType" ng-model="ctrl.distData" theme="selectize">
				    <ui-select-match placeholder="Select Distributor">{{$select.selected.distName}}</ui-select-match>
				    <ui-select-choices repeat="dist in ctrl.distList | filter: $select.search">
				    	<span ng-bind-html="dist.distName | highlight: $select.search"></span>
					</ui-select-choices>
			 	 </ui-select>
		 	</div>
		 	<div class="col-sm-2 text-right"><label class="control-label" for="customer">Customer:</label></div>
  			<div class="col-sm-3 text-left">
   				 <ui-select id="customer" ng-disabled="ctrl.user.actCustType" ng-model="ctrl.custData" theme="selectize">
				    <ui-select-match placeholder="Select Customer">{{$select.selected.custName}}</ui-select-match>
				    <ui-select-choices repeat="cust in ctrl.custList | filter: $select.search">
				    	<span ng-bind-html="cust.custName | highlight: $select.search"></span>
					</ui-select-choices>
			 	 </ui-select>
		 	</div>
		 </div>
   	</div>
   	<div class="row lineHeight">
		 <div class="form-group">
			 <div class="col-sm-2 text-right"><label class="control-label" for="fromDate">From Date:</label></div>
			 <div class="col-sm-3 text-left">
			 	<datepicker
				      date-format="dd/MM/yyyy"
				      button-prev='<i class="fa fa-arrow-circle-left"></i>'
				      button-next='<i class="fa fa-arrow-circle-right"></i>'>
				      <input ng-model="ctrl.purSalData.fromDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
		    	</datepicker>
			 </div>
			 <div class="col-sm-2 text-right"><label class="control-label" for="toDate">To Date:</label></div>
			 <div class="col-sm-3 text-left">
			 	<datepicker
				      date-format="dd/MM/yyyy"
				      button-prev='<i class="fa fa-arrow-circle-left"></i>'
				      button-next='<i class="fa fa-arrow-circle-right"></i>'>
				      <input ng-model="ctrl.purSalData.toDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
		    	</datepicker>
			 </div>
		 </div>
	</div>
   	<div class="row lineHeight">
		 <div class="form-group">
  			<div class="col-sm-4 text-right"><button name="fetch" id="fetch" ng-click="ctrl.fetchLedger()" class="btn btn-default">Fetch Report</button></div>
  			<div class="col-sm-4 text-right"><button name="download" id="download" ng-click="ctrl.downLedger()" class="btn btn-default">Download</button></div>
		 </div>
  	</div>
  	<br>
  	<div id="grid3" ui-grid="gridOptions3" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-cellnav class="gridBig">
       	 <div class="watermark" ng-show="!gridOptions3.data.length">No data available</div>
    </div> 
</div>