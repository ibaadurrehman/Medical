<div class="bankFormBody" ng-controller="salPayController as ctrl">
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
			 <div class="col-sm-2 text-right"><label class="control-label" for="transDate">Cash Deposit Date:</label></div>
			 <div class="col-sm-3 text-left">
			 	<datepicker
			      date-format="dd/MM/yyyy"
			      button-prev='<i class="fa fa-arrow-circle-left"></i>'
			      button-next='<i class="fa fa-arrow-circle-right"></i>'>
			      <input ng-model="ctrl.depositData.transDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
			    </datepicker>
			 </div>
		 </div> 
	</div>
	<div class="row lineHeight">
		 <div class="form-group">
			 <div class="col-sm-2 text-right"><label class="control-label" for="transAmt">Deposit Amount:</label></div>
			 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.depositData.transAmt" id="transAmt" /></div>
		 </div>
	</div>
	<div class="row lineHeight">
		<div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addDeposit()" class="btn btn-default">Add</button></div>
	</div>
</div>