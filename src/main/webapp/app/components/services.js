'use strict';
/* Place your Global Services in this File */

angular.module('myApp.services', [])
        .service('CVRService', ['$http', function ($http) {

                this.search = function (searchString, success, failure) {

                    $http({
                        method: 'GET',
                        params: {
                            "search": searchString,
                            "country": "DK"
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
