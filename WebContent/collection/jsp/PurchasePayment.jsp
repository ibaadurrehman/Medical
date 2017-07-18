<div class="purPayFormBody" ng-controller="purPayController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <div class="col-sm-2 text-right"><label class="control-label" for="distList">Distributor Name:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<ui-select id="distList" ng-model="ctrl.distData" theme="selectize" on-select="ctrl.fetchBillDetails(ctrl.distData.distId)">
					    <ui-select-match placeholder="Select Distributor">{{$select.selected.distName}}</ui-select-match>
					    <ui-select-choices repeat="dist in ctrl.distList | filter: $select.search">
					    	<span ng-bind-html="dist.distName | highlight: $select.search"></span>
						</ui-select-choices>
					</ui-select>
   				 </div>
			 </div>
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="transDate">Payment Date:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<datepicker
				      date-format="dd/MM/yyyy"
				      button-prev='<i class="fa fa-arrow-circle-left"></i>'
				      button-next='<i class="fa fa-arrow-circle-right"></i>'>
				      <input ng-model="ctrl.user.transDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
				    </datepicker>
   				 </div>
			 </div>    	   	   
		</div>
		<div id="grid" ui-grid="gridOptions" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-cellnav class="grid">
			<div class="watermark" ng-show="!gridOptions.data.length">No data available</div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="amtPaid">Amount to be paid:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.amtPaid" id="amtPaid" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
   	  		<div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="transAmt">Transaction Amount:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.transAmt" id="transAmt" ng-blur="ctrl.calculateAdjAmt()"/></div>
			</div>
   	  		<div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="amtAdj">Amount Adjusted:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.amtAdj" id="amtAdj" ng-blur="ctrl.validateAmtAdj()"/></div>
			</div>
		</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-3 text-right"><input type="radio" name="transType" ng-model="ctrl.user.transPayType" value="1" checked ng-click="ctrl.activate('cash')">Cash</div>
   				 <div class="col-sm-3 text-left"><input type="radio" name="transType" ng-model="ctrl.user.transPayType" value="2" ng-click="ctrl.activate('cheque')">Cheque</div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
			 	 <div class="col-sm-2 text-right"><label class="control-label" for="bank">Bank Name:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<ui-select id="bank" ng-disabled="ctrl.user.actType" ng-model="ctrl.bankData" theme="selectize" on-select="">
					    <ui-select-match placeholder="Select Bank">{{$select.selected.bankName}}</ui-select-match>
					    <ui-select-choices repeat="bank in ctrl.bankList | filter: $select.search">
					    	<span ng-bind-html="bank.bankName | highlight: $select.search"></span>
						</ui-select-choices>
					</ui-select>
   				 </div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="cheqNo">Cheque Number:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.cheqNo" id="cheqNo" /></div>
			 </div>
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="cheqDate">Cheque Date:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<datepicker
				      date-format="dd/MM/yyyy"
				      button-prev='<i class="fa fa-arrow-circle-left"></i>'
				      button-next='<i class="fa fa-arrow-circle-right"></i>'>
				      <input ng-model="ctrl.user.cheqDate" id="cheqDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
				    </datepicker>
   				 </div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="remarks">Remarks:</label></div>
   				 <div class="col-sm-5 text-left"><input class="form-control" ng-model="ctrl.user.remarks" id="remarks" /></div>
			 </div>
   	  	</div>
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addTransaction(ctrl.user)" class="btn btn-default">Add</button></div>
        </div>
  	</form>
</div>