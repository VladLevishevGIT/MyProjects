/*var adminApp = angular.module("myApp", []);

adminApp.controller("myCtrl", function($scope, $http) {
	$scope.myFunc = function() {

		var data = $scope.songs;
		$http.get("rest/quizWebService/getSongs").success(function(response) {
			$scope.companies = response.company;
		})
	}
});*/

var mainApp = angular.module('myApp', [])

mainApp.controller('myCtrl', function($scope, $http) {
    $http.get('rest/quizWebService/getSongs').
        then(function(response) {
            $scope.round = response.data;
        });
});

mainApp.controller('song01Ctrl',function () {
    $scope.inputValue = null;
    $scope.myFunc = function (value) {
        alert(inputValue);
    };
});