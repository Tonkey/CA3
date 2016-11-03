'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View5Ctrl', function ($http, $scope, deleteUserService) {


            var getUsers = function () {
                
                $http({
                    method: 'GET',
                    url: 'api/users'
                }).then(function successCallback(response) {
                    $scope.users = response.data;
                    console.log(response);
                }, function errorCallback(response) {
                    console.log(response);
                });

            };
            getUsers();

            $scope.deleteUser = function (user) {
                
                deleteUserService.deleteUser(user,$scope.users);
                    
            };
            
            $scope.doShow = function (username){
                switch (username){
                    case "user":
                        return false;
                    case "admin":
                        return false;
                    case "user_admin":
                        return false;
                    default: return true;
                }
            };
        });