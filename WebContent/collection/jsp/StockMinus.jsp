<div class="salesStockMinusForm" ng-controller="salesController as ctrl">
	<div class="row lineHeight">
		 <div class="form-group">
  				 <div class="col-sm-2 text-right"><label class="control-label" for="manId">Manufacturer Name:</label></div>
  				 <div class="col-sm-3 text-left">
  				 	<ui-select id="manufacturer" ng-model="ctrl.manufactData" theme="selectize" on-select="ctrl.fetchProdNameList(ctrl.manufactData.manId)">
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
  				 <div class="col-sm-2 text-right"><label class="control-label" for="prodId">Product Name:</label></div>
  				 <div class="col-sm-3 text-left">
  				 	<ui-select id="product" ng-model="ctrl.prodData" theme="selectize" on-select="ctrl.fetchProdPckgList(ctrl.prodData.prodName,ctrl.manufactData.manId)">
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
  				 <div class="col-sm-2 text-right"><label class="control-label" for="pckSize">Packaging Size:</label></div>
  				 <div class="col-sm-3 text-left">
  				 	<ui-select id="pckSize" ng-model="ctrl.pckSizeData" theme="selectize" on-select="ctrl.fetchProductList(ctrl.pckSizeData.prodId)">
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
  				 <div class="col-sm-2 text-right"><label class="control-label" for="prodCode">Product Code:</label></div>
  				 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.pckSizeData.prodCode" id="prodCode" disabled="disabled"/></div>
		 </div>
  	  	</div>
  	  	<div class="row lineHeight">
		 	<div class="form-group">
  				 <div class="col-sm-2 text-right"><label class="control-label" for="dlgMrp">MRP:</label></div>
  				 <div class="col-sm-3 text-left">
  				 	<ui-select id="dlgMRP" ng-model="ctrl.productData" theme="selectize">
				    <ui-select-match placeholder="Select MRP">{{$select.selected.mrp}}</ui-select-match>
				    <ui-select-choices repeat="mrp in ctrl.prodList | filter: $select.search">
				    	<span ng-bind-html="mrp.mrp | highlight: $select.search"></span>
					</ui-select-choices>
			 	</ui-select>
			 </div>
			 <input type="hidden" id="dlgProdDtlId" ng-model="ctrl.productData.prodDtlId"/>
 			 <div class="col-sm-2 text-right"><label class="control-label" for="dlgRate">Rate:</label></div>
  			 <div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.rate" id="dlgRate"/></div>
		 </div>
  	   </div>
  	   <div class="row lineHeight">
		 <div class="form-group">
		 	<div class="col-sm-2 text-right"><label class="control-label" for="dlgStock">Stock:</label></div>
			<div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.stock" id="dlgStock" disabled="disabled"/></div>
  			<div class="col-sm-2 text-right"><label class="control-label" for="qtyMinus">Quantity Minus:</label></div>
  			<div class="col-sm-3 text-left"><input class="form-control" ng-model="ctrl.productData.qtyMinus" id="qtyMinus" /></div>
		 </div>
  	   </div>
  	   <div class="row lineHeight">
		 <div class="form-group">
  			<div class="col-sm-4 text-right"><button name="save" id="save" ng-click="ctrl.stockMinus()" class="btn btn-default">Save</button></div>
		 </div>
  	   </div>
</div>