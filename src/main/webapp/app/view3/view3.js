'use strict';

angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                    controller: 'View3Ctrl'
                });
            }])

        .controller('View3Ctrl', function ($http, $scope, CVRService) {

            $scope.searchField = "search";

            $http.get('api/demouser')
                    .success(function (data, status, headers, config) {
                        $scope.data = data;
                    })
                    .error(function (data, status, headers, config) {

                    });

            $scope.result = null;

            $scope.searchCVR = function () {

                var success = function (data) {

                    $scope.result = data;

                }

                var failure = function (response) {

                    alert(response);

                }

                $scope.result = CVRService.search($scope.searchField, success, failure);
            }


        });