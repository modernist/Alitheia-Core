
angular.module('tracer', ['tracer.services', 'tracer.directives', 'tracer.controllers','ngCookies'])
	.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/', {templateUrl: 'includes/welcome.html', controller: 'WelcomeCtrl', requireLogin: true}).
		when('/login', {templateUrl: "includes/login.html", controller: 'LoginController', requireLogin: false}).
		when('/logout', {template: " ", controller: 'LogoutController', requireLogin: true}).
		when('/new-user', {templateUrl: 'includes/create-user.html', controller: 'NewUserCtrl', requireLogin: true}).
		when('/createSecurityProfile', {templateUrl: 'includes/createSecurityProfile.html', controller: 'CreateSecurityProfileCtrl', requireLogin: true}).
		when('/addToolToProfile', {templateUrl: 'includes/addToolToProfile.html', controller: 'AddToolToProfileCtrl', requireLogin: true}).
		when('/addProjectToList', {templateUrl: 'includes/addProjectToList.html', controller: 'AddProjectToListCtrl', requireLogin: true}).
		when('/detectVulnerabilityToList', {templateUrl: 'includes/detectVulnerabilityToList.html', controller: 'DetectVulnerabilityToListCtrl', requireLogin: true}).
		when('/createOperationalModule', {templateUrl: 'includes/createOperationalModule.html', controller: 'CreateOperationalModuleCtrl', requireLogin: true}).
		otherwise({redirectTo: '/'});
	}])
	.run(['$rootScope', '$location', 'AuthService', function($rootScope, $location, AuthService) {
		$rootScope.$on("$routeChangeStart", function (event, next, current) {
			$rootScope.error = null;
			if (!AuthService.isAuthenticated()) $location.path('/login');
		});
		
	}]);
