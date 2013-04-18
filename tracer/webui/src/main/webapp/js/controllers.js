/* Angjular.js controllers */

var app = angular.module('tracer', [])
	.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/', {templateUrl: 'includes/welcome.html', controller: WelcomeCtrl}).
		when('/new-user', {templateUrl: 'includes/create-user.html', controller: NewUserCtrl}).
		otherwise({redirectTo: '/'});
	}])
	;

// This directive is used during a user registration and ensures that
// two password entered are equal.
app.directive('verify', function() {
	return {
		require: 'ngModel',
		link: function(scope, elm, attrs, ctrl) {
			ctrl.$parsers.unshift(function(viewValue) {
				//If value in pass2 is equal with the attribute of
				//verify (thus, pass11)
				if (viewValue === scope[attrs.verify]) {
					ctrl.$setValidity('verify', true);
					return viewValue; }
				else {
					ctrl.$setValidity('verify', false);
					return undefined; }
			});
		}
	};
});

function NewUserCtrl($scope, $routeParams){
	// Controller that handles the registration form of a new user.
	// addUser would make the JSON request
	$scope.addUser = function(user) {
		console.log(user);
	}
	
}

function WelcomeCtrl($scope, $routeParams){


}

/* Login Controller
TODO: Rename it */
function Controller($scope, $location, $rootScope) {
	$scope.login = function(user)	{
		//Handle login credentials here. Currently logs credentials to console.
		console.log("Username: " + user.name);
		console.log("Password: " + user.pass);
	
		$scope.form.$setValidity('valid', true);
		if (user.name=="foo" && user.pass=="bar") {
			//Handle here authenicated user...
			$rootScope.loggedUser = user.name;
			console.log("Authorized...");
		} else {
			console.log("login-invalid");
			$scope.form.$setValidity('valid', false);
		}
	}
}

