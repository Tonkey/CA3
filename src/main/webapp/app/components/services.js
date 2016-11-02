'use strict';
/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
        .service('InfoService', [function () {
                var info = "Hello World from a Service";
                this.getInfo = function () {
                    return info;
                };
            }]);


angular.module('myApp.services', [])
        .service('CVRService', ['$http', function ($http) {

                this.search = function (searchString, country, success, failure) {

                    $http({
                        method: 'GET',
                        params: {
                            "search": searchString,
                            "country": country
                        },
                        url: "http://cvrapi.dk/api",
                        skipAuthorization: true
                    }).then(function successCallback(response) {

                        success(response.data);

                    }, function errorCallback(response) {

                        failure(response);

                    });
                }


            }]);