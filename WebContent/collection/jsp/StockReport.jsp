<div class="stockReportForm" ng-controller="reportController as ctrl">
	<div class="row lineHeight">
		 <div class="form-group">
			 <div class="col-sm-3 text-right"><input type="radio" name="fetchType" ng-model="ctrl.user.fetchType" value="All" checked ng-click="ctrl.activate('All')">All</div>
			 <div class="col-sm-3 text-left"><input type="radio" name="fetchType" ng-model="ctrl.user.fetchType" value="Single" ng-click="ctrl.activate('Single')">Product Wise</div>
		 </div>
   	</div>
	<div class="row lineHeight">
	 	<div class="form-group">
			 <div class="col-sm-2 text-right"><label class="control-label" for="manId">Manufacturer Name:</label></div>
			 <div class="col-sm-3 text-left">
			 	<ui-select id="manufacturer" ng-disabled="ctrl.user.actType" ng-model="ctrl.manufactData" theme="selectize" on-select="ctrl.fetchProdNameList(ctrl.manufactData.manId)">
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
			 	<ui-select id="product" ng-disabled="ctrl.user.actType" ng-model="ctrl.prodData" theme="selectize" on-select="ctrl.fetchProdPckgList(ctrl.prodData.prodName,ctrl.manufactData.manId)">
				    <ui-select-match placeholder="Select Product">{{$select.selected.prodName}}</ui-select-match>
				    <ui-select-choices repeat="prod in ctrl.prodNameList | filter: $select.search">
				    	<span ng-bind-html="prod.prodName | highlight: $select.search"></span>
					</ui-select-choices>
 				</ui-select>
 			</div>
		 	<div class="col-sm-2 text-right"><label class="control-label" for="pckSize">Packaging Size:</label></div>
 				<div class="col-sm-3 text-left">
 				 	<ui-select id="pckSize" ng-disabled="ctrl.user.actType" ng-model="ctrl.pckSizeData" theme="selectize" on-select="ctrl.fetchProductList(ctrl.pckSizeData.prodId)">
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
  			<div class="col-sm-4 text-right"><button name="fetch" id="fetch" ng-click="ctrl.fetchStock()" class="btn btn-default">Fetch Stock</button></div>
  			<div class="col-sm-4 text-right"><button name="download" id="download" ng-click="ctrl.downloadReport()" class="btn btn-default">Download</button></div>
		 </div>
  	 </div>
  	 <br>
  	 <div id="grid" ui-grid="gridOptions" ui-grid-save-state ui-grid-selection ui-grid-edit ui-grid-cellnav class="gridBig">
       	 <div class="watermark" ng-show="!gridOptions.data.length">No data available</div>
     </div>
</div>