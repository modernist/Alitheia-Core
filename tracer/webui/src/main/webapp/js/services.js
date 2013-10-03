
angular.module('tracer.services', [])
	.service('AuthService', function($http, $rootScope, $cookieStore) {
		
		// Service that handles authentication/authorization.
		// FIXME: This array is temporary...
		var allowed = {
			'foo': {'user':'foo','name':"Foo User",'pass':'bar'},
			'admin' : {'user':'admin','name':"Administrator",'pass':'bar'}
		};
	
		var authenticated = $cookieStore.get('isAuthenticated') || false,
			currentUser = $cookieStore.get('user') || undefined;
		// Login: Takes as input user credentials, connects to the server.
		// Upon successful login, return the loggedUser, otherwise returns
		// undefined	
		this.login = function(username, pass) {
			//Call REST for login
			//Substitute with this code. 
//			if (username=="foo" && pass=="bar") {
			if (allowed[username] !== undefined && allowed[username]['pass']== pass) {
				console.log("Authenticated!");
				authenticated = true;
				currentUser = allowed[username]['name'];
				$cookieStore.put('isauthenticated', authenticated);
				$cookieStore.put('user', currentUser);

			} else {
				console.log("Login Error");
			}
			return currentUser; 
		}

		// Logout:
		this.logout = function() {
			// Call REST service for logout
			authenticated = false
			//Remove cookies and stuff
			$cookieStore.remove('isauthenticated');
			$cookieStore.remove('user');
		}

		//isAuthenticated: Returns true or false
		this.isAuthenticated = function() {
			return authenticated;
		}
		this.getCurrentUser = function() {
			return currentUser;
		}
	});
