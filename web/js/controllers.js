var chatControllers = angular.module('chatControllers', ['ngAnimate']);

chatControllers.controller('ChatController', ['$scope', '$http', function($scope, $http) {
//  $http.get('js/data.json').success(function(data) {
//    $scope.movies = data;
//    $scope.moviesOrder = 'title';
//  });
}]);

chatControllers.controller('RegisterController', ['$scope', '$http','$routeParams', function($scope, $http, $routeParams) {
        
}]);

function registerController ($scope,$location) {
        $scope.reg = function(){
            var form = document.getElementById("username_input").value;
            if (form === ""){
                alert("Enter username!");
            }else {
                $location.path('chat'); // path not hash
                formAddUserSubmit(form);

            }
        };
}



