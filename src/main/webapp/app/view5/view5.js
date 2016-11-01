'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'app/view5/view5.html',
    controller: 'View5Ctrl',
    controllerAs : 'ctrl'
  });
}])

   .controller('View5Ctrl', function($http,$scope) {
  $http.get('api/demoadmin')
            .success(function (data, status, headers, config) {
              $scope.data = data;
            })
            .error(function (data, status, headers, config) {
              
             });
 
});