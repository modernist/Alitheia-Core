
angular.module('tracer.services', [])
	.service('AuthService', function($http, $rootScope, $cookieStore) {
		
		// Use one-hot encoding for roles. Inject roles to $rootScope to be
		// publicly visible
		var roles = {
			'user' : 1, 		// 001
			'programmer' : 2, 	// 010
			'admin' : 4			// 100
		};
		$rootScope.roles = roles;

		// Service that handles authentication/authorization.
		// FIXME: This array is temporary...
		var allowed = {
			'foo': {'user':'foo','name':"Foo User",'pass':'bar', 'role':roles.user},
			'programmer': {'user':'programmer','name':"Programmer",'pass':'bar', 'role':roles.programmer},
			'admin' : {'user':'admin','name':"Administrator",'pass':'bar','role':roles.admin},
		};
		
		var authenticated = $cookieStore.get('isAuthenticated'),
			currentUser = $cookieStore.get('currentUser');
			currentRole = $cookieStore.get('currentRole');
		// Login: Takes as input user credentials, connects to the server.
		// Upon successful login, return the loggedUser, otherwise returns
		// undefined	
		this.login = function(username, pass) {
			//Call REST for login
			//Substitute with this code. 
			if (allowed[username] !== undefined && allowed[username]['pass']== pass) {
				console.log("Authenticated!");
				authenticated = true;
				currentUser = allowed[username]['name']; 
				currentRole = allowed[username]['role'];
				$cookieStore.put('isAuthenticated', authenticated);
				$cookieStore.put('currentUser', currentUser);
				$cookieStore.put('currentRole', currentRole);


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
			$cookieStore.remove('isAuthenticated');
			$cookieStore.remove('currentUser');
			$cookieStore.remove('currentRole');
		}

		//isAuthenticated: Returns true or false
		this.isAuthenticated = function() {
			return authenticated;
		}
		this.getCurrentUser = function() {
			return currentUser;
		}
		this.getCurrentRole = function() {
			return currentRole;
		}
	});
