'use strict';

angular.module('myApp.view4', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
                });
            }])
        .controller('View4Ctrl', function ($http, $scope, valutaConverterService) {

            $scope.getRatesForToday = function () {

                $http({
                    url: 'api/currencies',
                    method: 'GET'
                }).then(function (res) {

                    $scope.dailyCurrencies = res.data;
                    $scope.valutaO = "GBR";
                    console.log(res);
                    console.log($scope.valutaOne);
                }, function (error) {

                });

            }



            $scope.getRatesForToday();
            $scope.valutaValue = $scope.valutaToCalculate;
//            $scope.valutaValue = $valutaConverterService.convertValuta($scope.valutaToCalculate , $scope.valutaOne, $scope.valutaTwo);





        });
