<div class="manFormBody" ng-controller="manController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="manId">Manufacturer Id:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.manId" id="manId"/></div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="manName">Manufacturer Name:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.manName" id="manName" /></div>
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
   				 <div class="col-sm-2 text-right"><label class="control-label" for="custCare">Customer Care:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.custCare" id="custCare"/></div>
			 </div>
		</div>
  					
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addManufact(ctrl.user)" class="btn btn-default">Add</button></div>
           	 <div class="col-sm-2 text-center"><button name="update" id="update" ng-click="ctrl.updateManufact(ctrl.user)" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="delete" id="delete" ng-click="ctrl.deleteManufact(ctrl.user.manId)" class="btn btn-default">Delete</button></div>
        </div>
  
		<div class="row">
			<div class="col-sm-10">
				<div class="table-responsive">
   			 	<table id="manTable" class="table table-bordered table-striped table-hover" >
   			 		<thead>
					   <tr>
					   	   <th>#</th>
						   <th>Manufacturer Id</th>
						   <th>Manufacturer Name</th>
						   <th>Address</th>
						   <th>Phone No</th>
						   <th>Customer Care</th>
					   </tr>
   					</thead>
					<tbody>
					   <tr ng-repeat="u in ctrl.users track by $index" ng-click="ctrl.updateModel(u)">
					   		<td><span>{{$index+1}}</span></td>
                           	<td><span ng-bind="u.manId"></span></td>
                           	<td><span ng-bind="u.manName"></span></td>
                           	<td><span ng-bind="u.address"></span></td>
                           	<td><span ng-bind="u.phoneNo"></span></td>
                           	<td><span ng-bind="u.custCare"></span></td>
  		                </tr>
					</tbody>
   				</table>
       		</div>
       	</div>
  	</form>
</div>