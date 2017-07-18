<div class="productFormBody" ng-controller="productController as ctrl">
	<form role="form">
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <div class="col-sm-2 text-right"><label class="control-label" for="manufactList">Manufacturer Name:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<input type="hidden" ng-model="ctrl.user.manId" id="manId" />
   				 	<ui-select id="manufactList" ng-model="ctrl.manufactData" ng-disabled="disabled" theme="selectize" on-select="ctrl.fetchProdList(ctrl.manufactData.manId)">
					    <ui-select-match placeholder="Select Manufacturer">{{$select.selected.manName}}</ui-select-match>
					    <ui-select-choices repeat="manufact in ctrl.maufactList | filter: $select.search">
					    	<span ng-bind-html="manufact.manName | highlight: $select.search"></span>
						</ui-select-choices>
					</ui-select>
   				 </div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
			 	 <div class="col-sm-2 text-right"><label class="control-label" for="prodName">Product Name:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<input type="hidden" ng-model="ctrl.user.prodId" id="prodId" />
   				 	<input type="hidden" ng-model="ctrl.user.prodDtlId" id="prodDtlId" />
   				 	<ui-select ng-model="ctrl.prodData" ng-disabled="disabled" theme="selectize" on-select="ctrl.fetchProdDtlList(ctrl.prodData.prodName)">
					    <ui-select-match placeholder="Select Product">{{$select.selected.prodName}}</ui-select-match>
					    <ui-select-choices repeat="product in ctrl.prodList | filter: $select.search" refresh="ctrl.setProdDetails($select)"
             				refresh-delay="0">
					    	<span ng-bind-html="product.prodName | highlight: $select.search"></span>
						</ui-select-choices>
					</ui-select>
   				 </div>
			 </div>    	   	   
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="pckSize">Packaging Size:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.pckSize" id="pckSize" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="mrp">MRP:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.mrp" id="mrp" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="rate">Rate:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.rate" id="rate" /></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
			 	 <div class="col-sm-2 text-right"><label class="control-label" for="vat">VAT Percent:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<input type="hidden" ng-model="ctrl.user.taxId" id="taxId" />
   				 	<ui-select id="vat" ng-disabled="disabled" ng-model="ctrl.taxData" theme="selectize" on-select="ctrl.addTax(ctrl.taxData.taxId)">
					    <ui-select-match placeholder="Select Tax">{{$select.selected.taxPerc}}</ui-select-match>
					    <ui-select-choices repeat="tax in ctrl.taxList | filter: $select.search">
					    	<span ng-bind-html="tax.taxPerc | highlight: $select.search"></span>
						</ui-select-choices>
					</ui-select>
   				 </div>
			 </div>    	   	   
		</div>
        <div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="add" id="add" ng-click="ctrl.addProduct(ctrl.user)" class="btn btn-default">Add</button></div>
           	 <div class="col-sm-2 text-center"><button name="update" id="update" ng-click="ctrl.updateProduct(ctrl.user)" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="delete" id="delete" ng-click="ctrl.deleteProduct(ctrl.user)" class="btn btn-default">Delete</button></div>
        </div>
  
		<div class="row">
			<div class="col-sm-10">
	       		<div class="table-responsive">
	       			 <table id="productTable" class="table table-bordered table-striped table-hover" >
	       			 		<thead>
	       						   <tr>
	       						   	   <th>#</th>
	       						   	   <th style="display:none;">Prod Id</th>
	       							   <th>Product Code</th>
	   								   <th>Product Name</th>
	   								   <th style="display:none;">Manufacturer Id</th>
	   								   <th>Manufacturer</th>
	   								   <th>Packaging Size</th>
	   								   <th>MRP</th>
	   								   <th>Rate</th>
	   								   <th style="display:none;">Tax Id</th>
	   								   <th>VAT Percent</th>
	       						   </tr>
	       					</thead>
	   						<tbody>
	   							   <tr ng-repeat="u in ctrl.users track by $index" ng-click="ctrl.updateProductModel(u)">
	   							   		<td><span>{{$index+1}}</span></td>
	   							   		<td style="display:none;"><span ng-bind="u.prodId"></span></td>
		                              	<td><span ng-bind="u.prodCode"></span></td>
		                              	<td><span ng-bind="u.prodName"></span></td>
		                              	<td style="display:none;"><span ng-bind="u.manId"></span></td>
		                              	<td><span ng-bind="u.manName"></span></td>
		                              	<td><span ng-bind="u.pckSize"></span></td>
		                              	<td><span ng-bind="u.mrp"></span></td>
		                              	<td><span ng-bind="u.rate"></span></td>
		                              	<td style="display:none;"><span ng-bind="u.taxId"></span></td>
		                              	<td><span ng-bind="u.taxPerc"></span></td>
		                          </tr>
	   						</tbody>
	       			 </table>
	       		</div>
	       	</div>
		</div>
  	</form>
</div>