'use strict';

angular.module('myApp.view5', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view5', {
                    templateUrl: 'app/view5/view5.html',
                    controller: 'View5Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View5Ctrl', function ($http, $scope) {

            $http.get('api/users')
                    .success(function (data, status, headers, config) {
                        console.log(data);
                        $scope.users = data;
                    })
                    .error(function (data, status, headers, config) {

                        console.log("error occurred.");
                    });

        });