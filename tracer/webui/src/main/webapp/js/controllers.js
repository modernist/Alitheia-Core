/* Angjular.js controllers */



// checks if tool is contained in toolArray (based on id property) and if found returns the index
function containsToolWithSameId(tool, toolArray) {
    var i;

    if (tool.id) {
        for (i = 0; i < toolArray.length; i++) {
            if (toolArray[i].id && toolArray[i].id == tool.id)
                return i;
        }
    }

    return -1;
}

// removes from list2 all the objects contained in list1
function mergeTwoLists(list1, list2) {
    var i;

    for (i = 0; i < list1.length; i++) {
        var idx = containsToolWithSameId(list1[i], list2);
        if (idx > -1)
            list2.splice(idx, 1);
    }

    return list2;
}

// list1 -> $scope.selectedTools
// list2 -> selectedAvailTools
// fullList2 -> $scope.profileAvailableTools
function addSelection(list1, list2, fullList2){

    var i;
    for(i = 0; i < list2.length; i++){
        list2[i].checked = false;
        list1.push(list2[i]);
        var index = containsToolWithSameId(list2[i], angular.copy(fullList2));
        if (index > -1)
            fullList2.splice(index, 1);
    }
}

// list1 ->  selectedProfTools
// list1Model ->   $scope.selectedTools
// list2 -> $scope.profileAvailableTools
function removeSelection(list1, list1Model, list2) {

    var i;
    for (i = 0; i < list1.length; i++) {
        list1[i].checked = false;
        var index = containsToolWithSameId(list1[i], angular.copy(list1Model));
        if (index > -1) {
            list2.push(list1[i]);
            list1Model.splice(index, 1);
        }
    }
}

function NewUserCtrl($scope, $routeParams){
    // Controller that handles the registration form of a new user.
    // addUser would make the JSON request
    $scope.addUser = function(user) {
        alert(user.fullname);
        console.log(user);
    }

}

angular.module('tracer.controllers', []).
	controller('CreateSecurityProfileCtrl', ['$scope', '$routeParams', '$filter', 'AuthService', function ($scope, $routeParams, $filter, AuthService){
		$scope.securityProfile = {};

		//$scope.securityProfile.tracingTools = [ { name: "FindBugs", id: "FindBugs", checked: true } , {name: "FRAMA-C", id: "FRAMA-C", checked: false } ];
		$scope.securityProfile.tracingTools = [ { name: "FindBugs", id: "FindBugs" } , {name: "FRAMA-C", id: "FRAMA-C" } ];

		$scope.selectedTools = function () {
			return $filter('filter')($scope.securityProfile.tracingTools, {checked: true});
		};

		$scope.createSecurityProfile = function(securityProfile, allSelectedTools){
			alert(securityProfile.securityprofilename);
			alert(securityProfile.type);
			var i;
			for (i = 0; i< allSelectedTools.length; i++)
				alert(allSelectedTools[i].name);


			console.log(securityProfile);
		} ;
		$scope.clear = function(securityProfile){


		}
	}])

	.controller('AddProjectToListCtrl', ['$scope', '$routeParams', '$filter', function ($scope,$routeParams, $filter){
		$scope.originalAvailableProjects = [ {"name": "Έργο 1", "id": "Έργο 1"},
			{ "name": "Έργο 2", "id": "Έργο 2" },
			{"name": "Έργαλείο 3", "id": "Έργαλείο 3"},
			{ "name": "Έργο 4", "id": "Έργο 4" },
			{ "name": "Έργο 5", "id": "Έργο 5" },
			{ "name": "Νέο", "id": "Νέο" } ];

		$scope.originalAvailableLists = [
			{name:'Λίστα 1', projects: [{"name": "Έργο 1", "id": "Έργο 1"}, { "name": "Έργο 2", "id": "Έργο 2" },{"name": "Έργαλείο 3", "id": "Έργαλείο 3"}]},
			{name:'Λίστα 2', projects: [{"name": "Έργο 1", "id": "Έργο 1"}, { "name": "Έργο 2", "id": "Έργο 2" } ]}
		];
		$scope.availableLists = $scope.originalAvailableLists;
		$scope.availableList = $scope.availableLists[0];

		$scope.availableProjects = function () {
			return $filter('filter')($scope.availableProjectList, {checked: true});
		};

		$scope.selectedProjects = function () {
			return $filter('filter')($scope.projectsOfSelectedList, {checked: true});
		};

		$scope.addProject = function(selectedAvailProjects){
			addSelection($scope.projectsOfSelectedList, selectedAvailProjects, $scope.availableProjectList);
		}

		$scope.removeProject = function(selectedProjectsOfList) {
			removeSelection(selectedProjectsOfList, $scope.projectsOfSelectedList, $scope.availableProjectList);
		}

		$scope.updateLists = function(index) {

			var i;
			var found = -1;
			for (i = 0; i < $scope.originalAvailableLists.length; i++) {
				if ($scope.originalAvailableLists[i].name == index.name) {
					$scope.projectsOfSelectedList = angular.copy($scope.originalAvailableLists[i].projects);
					found = i;
				}
			}
			if (found < 0 && $scope.originalAvailableLists[0])
				$scope.projectsOfSelectedList = angular.copy($scope.originalAvailableLists[0].projects);

			$scope.availableProjectList = mergeTwoLists($scope.projectsOfSelectedList, angular.copy($scope.originalAvailableProjects));
		}

		$scope.update = function(){
			var i;
			for (i = 0; i < $scope.projectsOfSelectedList.length; i++)
				console.log("project-" + i + ": " + $scope.projectsOfSelectedList[i].name);
		}

		$scope.updateLists($scope.availableList);



		$scope.addNewProject = function(newProject){
			console.log("newProject.name: " + newProject.name);
			console.log("newProject.pathoffiles: " + newProject.pathoffiles);
			console.log("newProject.pathofsourcefiles: " + newProject.pathofsourcefiles);
			$scope.originalAvailableProjects.push( {"name": newProject.name, "id": newProject.name});
			$scope.updateLists($scope.availableList);
			console.log($scope);
			$scope.newproject.name = "";
		}


	}])
	.controller('AddToolToProfileCtrl',
		['$scope', '$routeParams', '$filter', function ($scope,$routeParams, $filter){

		$scope.originalProfileAvailableTools = [ { "name": "Εργαλείο 1", "id": "Εργαλείο 1" },
			{ "name": "Εργαλείο 2", "id": "Εργαλείο 2" },
			{ "name": "Εργαλείο 3", "id": "Εργαλείο 3" },
			{ "name": "FindBugs", "id": "FindBugs" },
			{ "name": "FRAMA-C", "id": "FRAMA-C" }];

		$scope.originalProfileLists = [
			{name:'Προφίλ 1', type:"Αυτόματο" ,tools: [{"name": "FindBugs", "id": "FindBugs" }, { "name": "FRAMA-C", "id": "FRAMA-C" }]},
			{name:'Προφίλ 2', type:"Χειροκίνητο" ,tools: [{"name": "FindBugs", "id": "FindBugs" },{"name": "Εργαλείο 3", "id": "Εργαλείο 3" } ]}
		];

		$scope.profileLists = angular.copy($scope.originalProfileLists);
		$scope.profileList = $scope.profileLists[1];

		$scope.selectedAvailableTools = function () {
			return $filter('filter')($scope.profileAvailableTools, {checked: true});
		};

		$scope.selectedProfileTools = function () {
			return $filter('filter')($scope.selectedTools, {checked: true});
		};

		$scope.addTool = function(selectedAvailTools){
			addSelection($scope.selectedTools, selectedAvailTools, $scope.profileAvailableTools);
		}

		$scope.removeTool = function(selectedProfTools) {
			removeSelection(selectedProfTools, $scope.selectedTools, $scope.profileAvailableTools);
		}

		$scope.updateLists = function(index) {
			var i;
			var found = -1;
			for (i = 0; i < $scope.originalProfileLists.length; i++) {
				if ($scope.originalProfileLists[i].name == index.name) {
					$scope.selectedTools = angular.copy($scope.originalProfileLists[i].tools);
					found = i;
				}
			}
			if (found < 0 && $scope.originalProfileLists[0])
				$scope.selectedTools = angular.copy($scope.originalProfileLists[0].tools);

			$scope.profileAvailableTools = mergeTwoLists($scope.selectedTools, angular.copy($scope.originalProfileAvailableTools));
		}

		$scope.updateProfile = function(){
			var i;
			for (i = 0; i < $scope.selectedTools.length; i++)
				console.log("tool-" + i + ": " + $scope.selectedTools[i].name);
		}

		$scope.updateLists($scope.profileList);

	}])
	.controller('DetectVulnerabilityToListCtrl', ['$scope', '$routeParams', '$filter', function ($scope, $routeParams, $filter){

		$scope.detectList = {};
		$scope.detectList.vulnerabilityList = [{ "name": "Τρωτότητα 1", "id": "Τρωτότητα 1"},
											   { "name": "Τρωτότητα 2", "id": "Τρωτότητα 2"},
											   { "name": "Τρωτότητα 3", "id": "Τρωτότητα 3"} ];
		$scope.selectedTools = function () {
			return $filter('filter')($scope.detectList.vulnerabilityList, {checked: true});
		};

		$scope.run = function(detectList,allSelectedTools){
			for (i = 0; i< allSelectedTools.length; i++)
				console.log("selected checkboxes: "+  allSelectedTools[i].name);
		}

	}])
	.controller('CreateOperationalModuleCtrl', ['$scope', '$routeParams', '$filter', function ($scope, $routeParams,$filter){
   $scope.operationalmodule = {};


   $scope.inputTypeList =  [{ "name": "Τύπος εισόδου 1", "id": "inputType1" },
							{ "name": "Τύπος εισόδου 2", "id": "inputType2" }];

   $scope.outputTypeList =  [{ "name": "Τύπος εξόδου 1", "id": "outputType1" },
							{ "name": "Τύπος εξόδου 2", "id": "outputType2" }];

   $scope.operationalmodule.availableVulnerabilityList = [{ "name": "Τρωτότητα 1", "id": "Τρωτότητα 1 id", "decription":"Τρωτότητα 1", "library":"Libraty 1" },
														  { "name": "Τρωτότητα 2", "id": "Τρωτότητα 2 id", "decription":"Τρωτότητα 2", "library":"Libraty 2" },
														  { "name": "Τρωτότητα 3", "id": "Τρωτότητα 3 id", "decription":"Τρωτότητα 3", "library":"Libraty 3" }];

   $scope.addNewVulnerability = function(newVulnerability){
	   $scope.operationalmodule.availableVulnerabilityList.push(newVulnerability);
	   console.log($scope);
	   $scope.newVulnerability={};
   }

	$scope.selectedTools = function () {
	   return $filter('filter')($scope.operationalmodule.availableVulnerabilityList, {checked: true});
	};

   $scope.create = function(operationalmodule,allSelectedTools){
		if(operationalmodule.toolname ==null){
			alert("Δεν έχετε συμπληρώσει τα πεδία"  );

		}else{
			for (i = 0; i< allSelectedTools.length; i++)
				console.log("selected Vulnerability: " + allSelectedTools[i].name);
			console.log(operationalmodule);
		}

	}
	}])
	.controller('WelcomeCtrl', ['$scope', '$routeParams', function ($scope, $routeParams){

		}])
	.controller('LoginController' ,['$scope', '$location', '$routeParams', 'AuthService', function ($scope, $location, $rootScope, AuthService) {
		$scope.login = function(user)	{
			//Handle login credentials here. Currently logs credentials to console.
			console.log("Username: " + user.name);
			console.log("Password: " + user.pass);

			$scope.form.$setValidity('valid', true);
			if (user.name=="foo" && user.pass=="bar") {
				//Handle here authenicated user...
				$rootScope.loggedUser = user.name;
				console.log("Authorized...");
				AuthService.setUserAuthenticated(true);
				$location.path('/');
			} else {
				console.log("login-invalid");
				$scope.form.$setValidity('valid', false);
				AuthService.setUserAuthenticated(false);
			}
		}
	}
	])
	.controller('LogoutController' ,['$scope', '$location', '$routeParams', 'AuthService', function ($scope, $location, $rootScope, AuthService) {
			console.log("fsdsdf");
			//Handle HTTP logout.
			//Disable cookie
			AuthService.setUserAuthenticated(false);
			$location.path('/');
	}
	])
	.controller('navCtrl' ,['$scope', '$location', '$routeParams', 'AuthService', function ($scope, $location, $rootScope, AuthService) {

	}]);
