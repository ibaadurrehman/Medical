<div class="bankFormBody" ng-controller="bankController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <input type="hidden" id="bankId" ng-model="ctrl.user.bankId"/>
   				 <div class="col-sm-2 text-right"><label class="control-label" for="bankCode">Bank Code:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.bankCode" id="bankCode" disabled="disabled"/></div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="bankName">Bank Name:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.bankName" id="bankName" /></div>
			 </div>
   	  	</div>
		<div class="row lineHeightArea">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="address">Address:</label></div>
   				 <div class="col-sm-3 text-left"><textarea class="form-control" ng-model="ctrl.user.address" id="address" rows="3"></textarea></div>
			 </div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="phoneNo">Phone No:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.phoneNo" id="phoneNo" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="openingBal">Opening Balance:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.openingBal" id="openingBal"/></div>
			 </div>
		</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-3 text-right"><input type="radio" name="bankTypeId" ng-model="ctrl.user.bankTypeId" value="1" checked >Party</div>
   				 <div class="col-sm-3 text-left"><input type="radio" name="bankTypeId" ng-model="ctrl.user.bankTypeId" value="2" >Self</div>
			 </div>
   	  	</div>
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addBank(ctrl.user)" class="btn btn-default">Add</button></div>
           	 <div class="col-sm-2 text-center"><button name="update" id="update" ng-click="ctrl.updateBank(ctrl.user)" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="delete" id="delete" ng-click="ctrl.deleteBank(ctrl.user.bankId)" class="btn btn-default">Delete</button></div>
        </div>
  
		<div class="row">
			<div class="col-sm-10">
	       		<div class="table-responsive">
	       			 <table id="bankTable" class="table table-bordered table-striped table-hover" >
	       			 		<thead>
	       						   <tr>
	       						   	   <th>#</th>
	       						   	   <th style="display:none;">Bank Id</th>
	       							   <th>Bank Code</th>
	   								   <th>Bank Name</th>
	   								   <th>Address</th>
	   								   <th>Phone No</th>
	   								   <th>Opening Balalnce</th>
	   								   <th>Account Type</th>
	       						   </tr>
	       					</thead>
	   						<tbody>
	   							   <tr ng-repeat="u in ctrl.users track by $index" ng-click="ctrl.updateBankModel(u)">
	   							   		<td><span>{{$index+1}}</span></td>
	   							   		<td style="display:none;"><span ng-bind="u.bankId"></span></td>
		                              	<td><span ng-bind="u.bankCode"></span></td>
		                              	<td><span ng-bind="u.bankName"></span></td>
		                              	<td><span ng-bind="u.address"></span></td>
		                              	<td><span ng-bind="u.phoneNo"></span></td>
		                              	<td><span ng-bind="u.openingBal"></span></td>
		                              	<td><span ng-bind="u.bankType"></span></td>
		                          </tr>
	   						</tbody>
	       			 </table>
	       		</div>
	       	</div>
		</div>
  	</form>
</div>