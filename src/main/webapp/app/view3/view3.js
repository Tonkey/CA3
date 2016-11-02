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

            $scope.country = "DK";

            $scope.loading = false;

            $scope.result = null;

            $scope.searchCVR = function () {

                $scope.loading = true;
                $scope.error = null;
                $scope.result = null;

                var success = function (data) {

                    $scope.result = data;
                    $scope.loading = false;

                };

                var failure = function (response) {

                    if (response.status === 404) {

                        $scope.error = "No results";

                    } else {

                        $scope.error = "Unknown API error.";
                    }

                    $scope.loading = false;

                };

                $scope.result = CVRService.search($scope.searchField, $scope.country, success, failure);
            }


        });