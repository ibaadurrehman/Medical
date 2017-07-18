<div class="bankStatementForm" ng-controller="bankController as ctrl">
	<div class="row lineHeight">
		 <div class="form-group">
		 	 <div class="col-sm-2 text-right"><label class="control-label" for="bank">Bank Name:</label></div>
  			 <div class="col-sm-3 text-left">
  				<ui-select id="bank" ng-model="ctrl.bankData" theme="selectize" on-select="">
				    <ui-select-match placeholder="Select Bank">{{$select.selected.bankName}}</ui-select-match>
				    <ui-select-choices repeat="bank in ctrl.selfBankList | filter: $select.search">
				    	<span ng-bind-html="bank.bankName | highlight: $select.search"></span>
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
			      <input ng-model="ctrl.bankStateData.fromDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
			    </datepicker>
			 </div>
			 <div class="col-sm-2 text-right"><label class="control-label" for="toDate">To Date:</label></div>
			 <div class="col-sm-3 text-left">
			 	<datepicker
			      date-format="dd/MM/yyyy"
			      button-prev='<i class="fa fa-arrow-circle-left"></i>'
			      button-next='<i class="fa fa-arrow-circle-right"></i>'>
			      <input ng-model="ctrl.bankStateData.toDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
			    </datepicker>
			 </div>
		 </div> 
	</div>
	<div class="row lineHeight">
		 <div class="form-group">
  			<div class="col-sm-4 text-right"><button name="fetch" id="fetch" ng-click="ctrl.fetchStatement()" class="btn btn-default">Fetch Statement</button></div>
  			<div class="col-sm-4 text-right"><button name="download" id="download" ng-click="ctrl.downloadReport()" class="btn btn-default">Download</button></div>
		 </div>
  	 </div>
  	 <br>
  	 <div id="grid" ui-grid="gridOptions" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-cellnav class="gridBig">
       	 <div class="watermark" ng-show="!gridOptions.data.length">No data available</div>
     </div>
</div>