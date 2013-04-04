/* Angjular.js controllers */

//var app = angular.module('login',[]);
//
//app.directive('credentials', function() {
//	return {
//		require: 'ngModel',
//
//	};
//});


function Controller($scope) {
	
	$scope.login = function(user)	{
		//Handle login credentials here. Currently logs credentials to console.
		console.log("Username: " + user.name);
		console.log("Password: " + user.pass);

		if (user.name!=="foo" || user.pass!=="bar")	{
				user.invalid = true;
				console.log("login-invalid");
		}
	}
}
