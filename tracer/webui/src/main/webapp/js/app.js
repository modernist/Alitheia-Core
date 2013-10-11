
angular.module('tracer', ['tracer.services', 'tracer.directives', 'tracer.controllers','ngCookies'])
	.config(['$routeProvider', function($routeProvider) {
	//Redefine roles here...
	var roles = {
		'user' : 1, 		// 001
		'programmer' : 2, 	// 010
		'admin' : 4			// 100
	};

	$routeProvider.
		when('/', {
			templateUrl: 'includes/welcome.html', 
			controller: 'WelcomeCtrl', 
			requireLogin: true,
			access: roles.user,
		}).
		when('/login', {
			templateUrl: "includes/login.html", 
			controller: 'LoginController', 
			requireLogin: false,
			access: 0
		}).
		when('/logout', {
			template: " ", 
			controller: 'LogoutController', 
			requireLogin: true,
			access: roles.user
		}).
		when('/new-user', {
			templateUrl: 'includes/create-user.html', 
			controller: 'NewUserCtrl', 
			requireLogin: true,
			access : roles.admin
		}).
		when('/createSecurityProfile', {
			templateUrl: 'includes/createSecurityProfile.html', 
			controller: 'CreateSecurityProfileCtrl', 
			requireLogin: true,
			access: roles.user
		}).
		when('/addToolToProfile', {
			templateUrl: 'includes/addToolToProfile.html', 
			controller: 'AddToolToProfileCtrl', 
			requireLogin: true,
			access: roles.user
		}).
		when('/addProjectToList', {
			templateUrl: 'includes/addProjectToList.html', 
			controller: 'AddProjectToListCtrl', 
			requireLogin: true,
			access: roles.user
		}).
		when('/detectVulnerabilityToList', {
			templateUrl: 'includes/detectVulnerabilityToList.html', 
			controller: 'DetectVulnerabilityToListCtrl', 
			requireLogin: true,
			access: roles.user
		}).
		when('/createOperationalModule', {
			templateUrl: 'includes/createOperationalModule.html', 
			controller: 'CreateOperationalModuleCtrl', 
			requireLogin: true,
			access: roles.user
		}).
		otherwise({redirectTo: '/'});
	}])
	.run(['$rootScope', '$location', 'AuthService', function($rootScope, $location, AuthService) {
		$rootScope.$on("$routeChangeStart", function (event, next, current) {
			$rootScope.error = null;
			if (!AuthService.isAuthenticated()) $location.path('/login');
			//Restrict access to pages
			else if (AuthService.isAuthenticated() && 
					AuthService.getCurrentRole()<parseInt(next.access) ) $location.path('/');
		});
		
	}]);
