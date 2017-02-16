var adminApp = angular.module("myApp", []);

adminApp.controller("myCtrl", function($scope, $http) {
	$scope.myFunc = function() {

		var data = $scope.songs;
		$http.get("rest/quizWebService/getSongs").success(function(response) {
			$scope.companies = response.company;
		})
	}
});