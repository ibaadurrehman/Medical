<div class="custFormBody" ng-controller="customerController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <input type="hidden" id="custId" ng-model="ctrl.user.custId"/>
   				 <div class="col-sm-2 text-right"><label class="control-label" for="custCode">Customer Code:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.custCode" id="custCode" disabled="disabled"/></div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="custName">Customer Name:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.custName" id="custName" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="ownerName">Owner Name:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.ownerName" id="ownerName" /></div>
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
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.phoneNo" id="phoneNo"/></div>
			 </div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="emailId">Email Id:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.emailId" id="emailId"/></div>
			 </div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="vatNo">VAT No:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.vatNo" id="vatNo"/></div>
			 </div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="openingBal">Opening Balance:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.openingBal" id="openingBal"/></div>
			 </div>
		</div>
  					
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addCustomer(ctrl.user)" class="btn btn-default">Add</button></div>
           	 <div class="col-sm-2 text-center"><button name="update" id="update" ng-click="ctrl.updateCustomer(ctrl.user)" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="delete" id="delete" ng-click="ctrl.deleteCustomer(ctrl.user.custId)" class="btn btn-default">Delete</button></div>
        </div>
  
		<div class="row">
			<div class="col-sm-10">
	       		<div class="table-responsive">
	       			 <table id="custTable" class="table table-bordered table-striped table-hover" >
	       			 		<thead>
	       						   <tr>
	       						   	   <th>#</th>
	       							   <th style="display:none;">Customer Id</th>
	       							   <th>Customer Code</th>
	   								   <th>Customer Name</th>
	   								   <th>Owner Name</th>
	   								   <th>Address</th>
	   								   <th>Phone No</th>
	   								   <th>Email Id</th>
	   								   <th>VAT No</th>
	   								   <th>Opening Balance</th>
	       						   </tr>
	       					</thead>
	   						<tbody>
	   							   <tr ng-repeat="u in ctrl.users track by $index" ng-click="ctrl.updateCustModel(u)">
	   							   		<td><span>{{$index+1}}</span></td>
		                              	<td style="display:none;"><span ng-bind="u.custId"></span></td>
		                              	<td><span ng-bind="u.custCode"></span></td>
		                              	<td><span ng-bind="u.custName"></span></td>
		                              	<td><span ng-bind="u.ownerName"></span></td>
		                              	<td><span ng-bind="u.address"></span></td>
		                              	<td><span ng-bind="u.phoneNo"></span></td>
		                              	<td><span ng-bind="u.emailId"></span></td>
		                              	<td><span ng-bind="u.vatNo"></span></td>
		                              	<td><span ng-bind="u.openingBal"></span></td>
		                          </tr>
	   						</tbody>
	       			 </table>
	       		</div>
	       	</div>
		</div>
  	</form>
</div>