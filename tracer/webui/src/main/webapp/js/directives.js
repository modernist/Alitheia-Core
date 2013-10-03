
var app = angular.module('tracer.directives', []);


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


// This directive retrist access on roles which are inferior to a role specified
// by access. E.g <div data-restrict acces="admin"></div> will restrict access to roles
// programmer and user. This would result in removing completely the above div.
app.directive('restrict', function(AuthService, $rootScope){
	return {
		restrict: 'A',
		priority: 10000,
		link: function (){alert("fd");},
		compile: function(element, attr, linker) {
			//Get user role from AuthService
			var userRole = AuthService.getCurrentRole();
			var restrict = $rootScope.roles[attr.access];

			if(userRole<parseInt(restrict)) {
				element.children().remove();
				element.remove();
			}	
		}
	}
});
