<!DOCTYPE html>
<html ng-app="LoginApp">
  	<head>
    	<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<link rel="stylesheet" href="/Medical/collection/css/bootstrap.min.css" />
    	<link rel="stylesheet" href="/Medical/collection/css/style.css" />
    	<script src="/Medical/collection/js/jquery-2.2.2.min.js"></script>
	    <script src="/Medical/collection/js/angular.js"></script>
	    <script src="/Medical/collection/js/angular-route.js"></script>
	    <script src="/Medical/collection/js/angular-cookies.js"></script>
	    <script src="/Medical/collection/js/login.js"></script>
	    <script src="/Medical/collection/js/bootstrap.min.js"></script>
	    <script src="/Medical/collection/js/controllers/loginServices.js"></script>
	    <script src="/Medical/collection/js/controllers/loginControllers.js"></script>
  	</head>

  	<body style="background-color:#EEEEEE;" ng-controller = "LoginController">
    	<div class="container">
			<div ng-show="error" class="alert alert-danger">{{error}}</div>
			<form name="form" ng-submit="login()" role="form">
			    <div class="form-group">
			        <label for="username">Username</label>
			        <i class="fa fa-key"></i>
			        <input type="text" name="username" id="username" class="form-control" ng-model="username" required />
			        <span ng-show="form.username.$dirty && form.username.$error.required" class="help-block">Username is required</span>
			    </div>
			    <div class="form-group">
			        <label for="password">Password</label>
			        <i class="fa fa-lock"></i>
			        <input type="password" name="password" id="password" class="form-control" ng-model="password" required />
			        <span ng-show="form.password.$dirty && form.password.$error.required" class="help-block">Password is required</span>
			    </div>
			    <div class="form-actions">
			        <button type="submit" ng-disabled="form.$invalid || dataLoading" class="btn btn-danger">Login</button>
			        <img ng-if="dataLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA=="/>
			    </div>
			</form>
		</div>
	</body>
</html>