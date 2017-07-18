<div class="purchaseFormBody" ng-controller="purchaseController as ctrl">
	<form role="form">
		<div ng-show="error" class="alert alert-danger">{{error}}</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="billNo">Bill No:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.billNo" id="billNo" required/></div>
   				 <span ng-show="form.billNo.$dirty && form.billNo.$error.required" class="help-block">Bill No is required</span>
			 </div>
   	  	</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="billDate">Bill Date:</label></div>
   				 <div class="col-sm-3 text-left">
   				 	<datepicker
				      date-format="dd/MM/yyyy"
				      button-prev='<i class="fa fa-arrow-circle-left"></i>'
				      button-next='<i class="fa fa-arrow-circle-right"></i>'>
				      <input ng-model="ctrl.user.billDate" type="text" class="font-fontawesome font-light radius3" placeholder="Choose a date"/>
				    </datepicker>
   				 </div>
			 </div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="distributor">Distributor:</label></div>
   				 <div class="col-sm-3 text-left">
	   				 <ui-select id="distributor" ng-model="ctrl.distData" theme="selectize">
					    <ui-select-match placeholder="Select Distributor">{{$select.selected.distName}}</ui-select-match>
					    <ui-select-choices repeat="dist in ctrl.distList | filter: $select.search">
					    	<span ng-bind-html="dist.distName | highlight: $select.search"></span>
						</ui-select-choices>
				 	 </ui-select>
			 	 </div>
			 </div>
			 <div class="col-sm-2 text-right"><button id="addProduct" type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#prodModal">Add Product</button></div>
           	 <div class="col-sm-2 text-center"><button id="updateproduct" ng-click="ctrl.updatePurProd()" class="btn btn-default btn-sm" data-toggle="modal" data-target="#prodUpdateModal">Modify Product</button></div>
   			 <div class="col-sm-2"><button id="deleteProduct" ng-click="ctrl.deletePurProd()" class="btn btn-default btn-sm">Delete Product</button></div>
		</div>
		<div id="prodModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
			    <div class="modal-content">
			    	<div class="modal-header">
			        	<button type="button" class="close" data-dismiss="modal" ng-click="ctrl.resetProduct()">&times;</button>
			        	<h4 class="modal-title">Add Product</h4>
			        </div>
			        <div class="modal-body">
			        	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="manId">Manufacturer Name:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="manufacturer" ng-model="ctrl.manufactData" theme="bootstrap" on-select="ctrl.fetchProdNameList(ctrl.manufactData.manId)">
									    <ui-select-match placeholder="Select Manufacturer">{{$select.selected.manName}}</ui-select-match>
									    <ui-select-choices repeat="man in ctrl.maufactList | filter: $select.search">
									    	<span ng-bind-html="man.manName | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="product">Product Name:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="product" ng-model="ctrl.prodData" theme="bootstrap" on-select="ctrl.fetchProdPckgList(ctrl.prodData.prodName,ctrl.manufactData.manId)">
									    <ui-select-match placeholder="Select Product">{{$select.selected.prodName}}</ui-select-match>
									    <ui-select-choices repeat="prod in ctrl.prodNameList | filter: $select.search">
									    	<span ng-bind-html="prod.prodName | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="pckSize">Packaging Size:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="pckSize" ng-model="ctrl.pckSizeData" theme="bootstrap" on-select="ctrl.fetchProductList(ctrl.pckSizeData.prodId)">
									    <ui-select-match placeholder="Select Packaging Size">{{$select.selected.pckSize}}</ui-select-match>
									    <ui-select-choices repeat="pckSize in ctrl.prodPckgList | filter: $select.search">
									    	<span ng-bind-html="pckSize.pckSize | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
							 	 <input type="hidden" id="dlgProdId" ng-model="ctrl.productData.prodId"/>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="prodCode">Product Code:</label></div>
				   				 <div class="col-sm-5 text-left"><input class="form-control" ng-model="ctrl.pckSizeData.prodCode" id="prodCode" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgMrp">MRP:</label></div>
				   				 <div class="col-sm-3 text-left">
				   				 	<ui-select id="dlgMRP" ng-model="ctrl.productData" theme="bootstrap" on-select="ctrl.setTaxDetails()">
									    <ui-select-match placeholder="Select MRP">{{$select.selected.mrpStr}}</ui-select-match>
									    <ui-select-choices repeat="mrp in ctrl.prodList | filter: $select.search" refresh="ctrl.setMrpDetails($select)"
             				refresh-delay="0">
									    	<span ng-bind-html="mrp.mrpStr | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
								 <input type="hidden" id="dlgProdDtlId" ng-model="ctrl.productData.prodDtlId"/>
								 <div class="col-sm-3 text-right"><label class="control-label" for="dlgRate">Rate:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.rate" id="dlgRate" /></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgQty">Quantity:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.qty" id="dlgQty" ng-blur="ctrl.calculateQtyRate()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgStock">Stock:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.stock" id="dlgStock" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgScheme">Scheme:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.scheme" id="dlgScheme" ng-blur="ctrl.calculateScheme()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgStock">Scheme Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.schemeVal" id="dlgSchemeVal" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc1">Disc1%:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc1" id="dlgDisc1" ng-blur="ctrl.calculateDisc1()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc1Val">Disc1 Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc1Val" id="dlgDisc1Val" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc2">Disc2%:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc2" id="dlgDisc2" ng-blur="ctrl.calculateDisc2()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc2">Disc2 Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc2Val" id="dlgDisc2Val" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgVat">VAT Percent:</label></div>
				   				 <div class="col-sm-3 text-left">
				   				 	<ui-select id="pckSize" ng-model="ctrl.taxData" theme="selectize" on-select="ctrl.calculateTax()">
									    <ui-select-match placeholder="Select Tax">{{$select.selected.taxPerc}}</ui-select-match>
									    <ui-select-choices repeat="tax in ctrl.taxList | filter: $select.search">
									    	<span ng-bind-html="tax.taxPerc | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 	<!-- <input class="form-control" ng-model="ctrl.productData.taxPerc" id="dlgVat" disabled="disabled"/> -->
								 </div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgTax">Tax:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.taxVal" id="dlgTax" disabled="disabled"/></div>
							 </div>
				   	  	</div>
			        </div>
			        <div class="modal-footer" style="text-align:center !important;">
			        	<button type="button" class="btn btn-default btn-md" id="addProd" ng-click="ctrl.addValToProdGrid(ctrl.productData)">Add</button>
			        	<button type="button" class="btn btn-default btn-md" data-dismiss="modal" ng-click="ctrl.resetProduct()">Close</button>
			        </div>
			    </div>
			</div>
		</div>
		<div id="prodUpdateModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
			    <div class="modal-content">
			    	<div class="modal-header">
			        	<button type="button" class="close" data-dismiss="modal" ng-click="ctrl.resetProduct()">&times;</button>
			        	<h4 class="modal-title">Update Product</h4>
			        </div>
			        <div class="modal-body">
			        	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="manId">Manufacturer Name:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="manufacturer" ng-model="ctrl.manufactData" theme="bootstrap" on-select="ctrl.fetchProdNameList(ctrl.manufactData.manId)">
									    <ui-select-match placeholder="Select Manufacturer">{{$select.selected.manName}}</ui-select-match>
									    <ui-select-choices repeat="man in ctrl.maufactList | filter: $select.search">
									    	<span ng-bind-html="man.manName | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="prodId">Product Name:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="product" ng-model="ctrl.prodData" theme="bootstrap" on-select="ctrl.fetchProdPckgList(ctrl.prodData.prodName,ctrl.manufactData.manId)">
									    <ui-select-match placeholder="Select Product">{{$select.selected.prodName}}</ui-select-match>
									    <ui-select-choices repeat="prod in ctrl.prodNameList | filter: $select.search">
									    	<span ng-bind-html="prod.prodName | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="pckSize">Packaging Size:</label></div>
				   				 <div class="col-sm-5 text-left">
				   				 	<ui-select id="pckSize" ng-model="ctrl.pckSizeData" theme="bootstrap" on-select="ctrl.fetchProductList(ctrl.pckSizeData.prodId)">
									    <ui-select-match placeholder="Select Packaging Size">{{$select.selected.pckSize}}</ui-select-match>
									    <ui-select-choices repeat="pckSize in ctrl.prodPckgList | filter: $select.search">
									    	<span ng-bind-html="pckSize.pckSize | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="prodCode">Product Code:</label></div>
				   				 <div class="col-sm-5 text-left"><input class="form-control" ng-model="ctrl.pckSizeData.prodCode" id="prodCode" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgMrp">MRP:</label></div>
				   				 <div class="col-sm-3 text-left">
				   				 	<ui-select id="dlgMRP" ng-model="ctrl.productData" theme="bootstrap" on-select="ctrl.setTaxDetails()">
									    <ui-select-match placeholder="Select MRP">{{$select.selected.mrp}}</ui-select-match>
									    <ui-select-choices repeat="mrp in ctrl.prodList | filter: $select.search" refresh="ctrl.setMrpDetails($select)"
             								refresh-delay="0">
									    	<span ng-bind-html="mrp.mrp | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
								 </div>
								 <input type="hidden" id="dlgProdDtlId" ng-model="ctrl.productData.prodDtlId"/>
								 <div class="col-sm-3 text-right"><label class="control-label" for="dlgRate">Rate:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.rate" id="dlgRate"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgQty">Quantity:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.qty" id="dlgQty" ng-blur="ctrl.calculateQtyRate()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgStock">Stock:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.stock" id="dlgStock" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgScheme">Scheme:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.scheme" id="dlgScheme" ng-blur="ctrl.calculateScheme()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgStock">Scheme Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.schemeVal" id="dlgSchemeVal" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc1">Disc1%:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc1" id="dlgDisc1" ng-blur="ctrl.calculateDisc1()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc1Val">Disc1 Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc1Val" id="dlgDisc1Val" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc2">Disc2%:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc2" id="dlgDisc2" ng-blur="ctrl.calculateDisc2()"/></div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgDisc2">Disc2 Value:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.disc2Val" id="dlgDisc2Val" disabled="disabled"/></div>
							 </div>
				   	  	</div>
				   	  	<div class="row lineHeight">
							 <div class="form-group">
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgVat">VAT Percent:</label></div>
				   				 <div class="col-sm-3 text-left">
				   				 	<ui-select id="pckSize" ng-model="ctrl.taxData" theme="selectize" on-select="ctrl.calculateTax()">
									    <ui-select-match placeholder="Select Tax">{{$select.selected.taxPerc}}</ui-select-match>
									    <ui-select-choices repeat="tax in ctrl.taxList | filter: $select.search">
									    	<span ng-bind-html="tax.taxPerc | highlight: $select.search"></span>
										</ui-select-choices>
								 	</ui-select>
				   				 	<!-- <input class="form-control" ng-model="ctrl.productData.taxPerc" id="dlgVat" disabled="disabled"/> -->
				   				 </div>
				   				 <div class="col-sm-3 text-right"><label class="control-label" for="dlgTax">Tax:</label></div>
				   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.taxVal" id="dlgTax" disabled="disabled"/></div>
							 </div>
				   	  	</div>
			        </div>
			        <div class="modal-footer" style="text-align:center !important;">
			        	<button type="button" class="btn btn-default btn-md" data-dismiss="modal" id="updateProd" ng-click="ctrl.updateGridProd(ctrl.productData)">Update</button>
			        </div>
			    </div>
			</div>
		</div>
		<div id="grid1" ui-grid="gridOptions" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-validate ui-grid-cellnav class="grid">
			<div class="watermark" ng-show="!gridOptions.data.length">No data available</div>
		</div>
		<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="totScheme">Total Scheme:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.totScheme" id="totScheme" disabled="disabled"/></div>
   				 <div class="col-sm-2 text-right"><label class="control-label" for="grossAmt">Gross Amount:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.grossAmt" id="grossAmt" disabled="disabled"/></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="totTax">Total Vat:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.totTax" id="totTax" disabled="disabled"/></div>
   				 <div class="col-sm-2 text-right"><label class="control-label" for="roundOffAmt">Round Off Amount:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.roundOffAmt" id="roundOffAmt" disabled="disabled"/></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
			 <div class="form-group">
   				 <div class="col-sm-2 text-right"><label class="control-label" for="netAmt">Net Amount:</label></div>
   				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.user.netAmt" id="netAmt" ng-blur="ctrl.calculateRoundOffAmt()"/></div>
			 </div>
   	  	</div>
   	  	<div class="row lineHeight">
           	 <div class="col-sm-2 text-right"><button name="save" id="save" ng-disabled="form.$invalid" ng-click="ctrl.save()" class="btn btn-default">Save</button></div>
   			 <div class="col-sm-2 text-center"><button name="submit" id="submit" ng-disabled="form.$invalid" ng-click="ctrl.submit()" class="btn btn-default">Submit</button></div>
   			 <div class="col-sm-2"><button name="modifyPur" id="modifyPur" ng-click="ctrl.fetchPurchase()" class="btn btn-default">Modify</button></div>
   			 <div class="col-sm-2"><button name="deletePur" id="deletePur" ng-click="ctrl.deletePurchase()" class="btn btn-default">Delete</button></div>
   			 <div class="col-sm-2"><button name="resetPur" id="resetPur" ng-click="ctrl.resetPurchase()" class="btn btn-default">Reset</button></div>
        </div>
        <div id="grid2" ui-grid="gridOptions2" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-cellnav class="grid">
        	<div class="watermark" ng-show="!gridOptions2.data.length">No data available</div>
        </div>
  	</form>
</div>