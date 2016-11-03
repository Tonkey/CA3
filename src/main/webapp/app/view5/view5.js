'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View5Ctrl', function ($http, $scope, $location) {


            this.getUsers = function () {

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
            this.getUsers();

            $scope.deleteUser = function (user) {
                console.log(user);
                var index = $scope.users.indexOf(user);
                $http({
                    url: 'api/users',
                    method: 'DELETE',
                    data: {
                        userName: user.userName
                    },
                    headers: {
                        "Content-Type": "application/json;charset=utf-8"
                    }
                }).then(function (res) {
                    console.log(res.data);
                    
                    $scope.users.splice(index,1);
                }, function (error) {
                    console.log(error);
                });


            };
        });