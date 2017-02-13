var loginApp = angular.module("loginApp", []);

loginApp.controller("loginCtrl", function($scope, $http) {
	$scope.signIn = function() {
		// alert("rest/LoginService/login/"+$scope.submitUser.user+"/"+$scope.submitUser.name+"/"+$scope.submitUser.password);
		$http.get(
				"rest/LoginService/login/" + $scope.submitUser.user + "/"
						+ $scope.submitUser.name + "/"
						+ $scope.submitUser.password).then(function(response) {
							var t = "";
							t = response.data;
							window.location.assign(t);
		})
	}
});