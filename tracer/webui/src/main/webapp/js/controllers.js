/* Angjular.js controllers */

function Controller($scope) {
	$scope.login = function(user)	{
		//Handle login credentials here. Currently logs credentials to console.
		console.log("Username: " + user.name);
		console.log("Password: " + user.pass);
	
		$scope.form.$setValidity('valid', true);	
		if (user.name!=="foo" || user.pass!=="bar")	{
				console.log("login-invalid");
				$scope.form.$setValidity('valid', false);
		}
	}
}
