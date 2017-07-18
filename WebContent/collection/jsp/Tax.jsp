<div class="taxFormBody" ng-controller="taxController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <input type="hidden" id="taxId" ng-model="ctrl.user.taxId"/>
   				 <div class="col-sm-2 text-right"><label class="control-label" for="taxName">Tax Name:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.taxName" id="taxName" /></div>
			 </div>
   	  	</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="taxPerc">Tax Perc:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.taxPerc" id="taxPerc" /></div>
			 </div>
		</div>
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addTax(ctrl.user)" class="btn btn-default">Add</button></div>
           	 <div class="col-sm-2 text-center"><button name="update" id="update" ng-click="ctrl.updateTax(ctrl.user)" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="delete" id="delete" ng-click="ctrl.deleteTax(ctrl.user.taxId)" class="btn btn-default">Delete</button></div>
        </div>
  
		<div class="row">
			<div class="col-sm-10">
	       		<div class="table-responsive">
	       			 <table id="taxTable" class="table table-bordered table-striped table-hover" >
	       			 		<thead>
	       						   <tr>
	       						   	   <th>#</th>
	       							   <th style="display:none;">Tax Id</th>
	   								   <th>Tax Name</th>
	   								   <th>Tax Perc</th>
	       						   </tr>
	       					</thead>
	   						<tbody>
	   							   <tr ng-repeat="u in ctrl.users track by $index" ng-click="ctrl.updateTaxModel(u)">
	   							   		<td><span>{{$index+1}}</span></td>
		                              	<td style="display:none;"><span ng-bind="u.taxId"></span></td>
		                              	<td><span ng-bind="u.taxName"></span></td>
		                              	<td><span ng-bind="u.taxPerc"></span></td>
		                          </tr>
	   						</tbody>
	       			 </table>
	       		</div>
	       	</div>
		</div>
  	</form>
</div>