angular.module('myApp', [])
.controller('myCtrl', function($scope, $http) {
	$http
	.get("rest/quizWebService/getSongs")
	.success(function(response) {
		})
	});