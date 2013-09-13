
angular.module('tracer', ['tracer.services', 'tracer.directives', 'tracer.controllers'])
	.config(['$routeProvider', function($routeProvider) {
	$routeProvider.
		when('/', {templateUrl: 'includes/welcome.html', controller: 'WelcomeCtrl'}).
		when('/login', {templateUrl: "includes/login.html", controller: 'LoginController'}).
		when('/new-user', {templateUrl: 'includes/create-user.html', controller: 'NewUserCtrl'}).
		when('/createSecurityProfile', {templateUrl: 'includes/createSecurityProfile.html', controller: 'CreateSecurityProfileCtrl'}).
		when('/addToolToProfile', {templateUrl: 'includes/addToolToProfile.html', controller: 'AddToolToProfileCtrl'}).
		when('/addProjectToList', {templateUrl: 'includes/addProjectToList.html', controller: 'AddProjectToListCtrl'}).
		when('/detectVulnerabilityToList', {templateUrl: 'includes/detectVulnerabilityToList.html', controller: 'DetectVulnerabilityToListCtrl'}).
		when('/createOperationalModule', {templateUrl: 'includes/createOperationalModule.html', controller: 'CreateOperationalModuleCtrl'}).
		otherwise({redirectTo: '/'});
	}]);
