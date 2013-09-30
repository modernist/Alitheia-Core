
angular.module('tracer.services', [])
	.service('AuthService', function($http, $cookieStore) {
		var isAuthenticated = $cookieStore.get('isAuthenticated');	

		this.setUserAuthenticated = function(value) {
			isAuthenticated = value;
			$cookieStore.put('isAuthenticated', isAuthenticated);
		}

		this.getUserAuthenticated = function() {
			return isAuthenticated;
		}
	});
