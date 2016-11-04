'use strict';

angular.module('myApp.view4', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    controller: 'View4Ctrl'
                });
            }])
        .controller('View4Ctrl', function ($http, $scope) {

            $scope.getRatesForToday = function () {

                $http({
                    url: 'api/currencies',
                    method: 'GET'
                }).then(function (res) {

                    $scope.dailyCurrencies = res.data;
                    console.log(res);
                    var sortedValuta = [];
                    angular.forEach($scope.dailyCurrencies, function (value, key) {
                        this.push({
                            "code": value.code.id,
                            "desc": value.code.description,
                            "rate": value.rate
                        });
                    }, sortedValuta);
                    $scope.valutaArray = sortedValuta;
                }, function (error) {

                });

            }

            $scope.calculateValuta = function (a, b) {
                if (b === null || a === null) {

                    $scope.valutaDescription = "-- choose a valuta --";
                    $scope.result = 0;

                } else {
                    $scope.valutaDescription = b.desc;
                    $scope.result = b.rate * a / 100;
                }
            }

            $scope.getRatesForToday();

        });
