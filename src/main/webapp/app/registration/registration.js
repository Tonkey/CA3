'use strict';

angular.module("myApp.registration", ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/registration', {
                    templateUrl: 'app/registration/registration.html',
                    controller: 'RegistrationCtrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('RegistrationCtrl', function ($http, $scope, $location) {

//            $scope.uNameNotAvailable = false;

            $scope.registerUser = function () {
                this.user = {'userName': $scope.username, 'passwordHash': $scope.password};

                console.log(this.user.userName);
                $http({
                    method: 'POST',
                    url: 'api/login/createUser',
                    data: this.user
                }).then(function successCallback(response) {
                    console.log(response);
                    $location.path('#/view1');

                }, function errorCallback(response) {
                    document.getElementById("usernameField").style.borderColor = "red";
                    $scope.uNameNotAvailable = true;
                    console.log(response);

                });


            };

        });




