var mainApp = angular.module('myApp', [])

mainApp.controller('myCtrl', 
		
function($scope, $http) {
	
    $http.get('rest/quizWebService/getFirstSong').
        then(function(response) {
            $scope.round = response.data
        });
        
    $scope.getOneMoreSong = function(){
    	$http.get('rest/quizWebService/getOneMoreSong')
    	.then(function(response){
    		$scope.round = response.data
    	});
    };
});

