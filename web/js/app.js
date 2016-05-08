var myApp = angular.module('myApp', [
  'ngRoute',
  'chatControllers'
]);

myApp.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
  when('/chat', {
    templateUrl: 'partials/chat.html',
    controller: 'ChatController'
  }).
  otherwise({
    templateUrl: 'partials/register.html',
    controller: 'RegisterController'
  });
}]);