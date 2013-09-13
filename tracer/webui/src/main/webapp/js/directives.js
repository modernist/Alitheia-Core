
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
