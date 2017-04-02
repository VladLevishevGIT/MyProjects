var quizMainApp = angular.model("QuizApp", [])

quizMainApp.controller("QuizAppCtrl", function ($http, $scope) {
    $http.get('rest/quizWebService/getFirstSong').
    then(function(response) {
        alert("TEST")
        $scope.round = response.data
    });
})