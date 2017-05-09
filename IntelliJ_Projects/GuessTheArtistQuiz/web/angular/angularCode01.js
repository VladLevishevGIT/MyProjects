var songCount = 1;
var quizMainApp = angular.module("QuizApp", [])

quizMainApp.controller("QuizAppCtrl", function ($http, $scope) {
    $http.get('rest/quizWebService/getFirstSong01').
    then(function(response) {
        $scope.returnedRound = response.data;
        $scope.firstSong = $scope.returnedRound.firstSong;
    });

    $scope.getOneMoreSong = function (inputValue) {
        if(inputValue == $scope.returnedRound.artistName) {
            $scope.showSuccessAlert = true;
            $scope.successTextAlert = "Very Good!"
        }else if(songCount=1) {
            $scope.firstSong = $scope.returnedRound.firstSong;
            $scope.secondSong = $scope.returnedRound.secondSong;
            songCount=2;
        }else if(songCount=2){
            $scope.firstSong = $scope.returnedRound.firstSong;
            $scope.secondSong = $scope.returnedRound.secondSong;
            $scope.thirdSong = $scope.returnedRound.thirdSong;
            songCount=3;
        }else {
            $scope.showErrorAlert = true;
                }
    }
})