/**
 * Created by christian on 20/10/15.
 */
angular.module('app').factory('RestaurantService', RestaurantService);

RestaurantService.$inject = ['$http'];
function RestaurantService($http) {
    var baseUrl = '/api/restaurantes';
    var service = {};

    service.getAll = GetAll;
    service.insert = Insert;
    service.delete = Delete;

    return service;

    function GetAll(callback) {
        $http({
            method: 'GET',
            url: baseUrl
        })
        .success(function (data, status, headers, config) {
            callback(data);
        })
        .error(function (data, status) {
            console.log(JSON.stringify(data));
            console.log(JSON.stringify(status));
        });

    };

    function Insert(restaurant, callback) {
        $http({
            method: 'POST',
            url: baseUrl,
            data: restaurant
        })
        .success(function (data, status, headers, config) {
            callback(status);
        })
        .error(function (data, status) {
            console.log(JSON.stringify(data));
            console.log(JSON.stringify(status));
        });
    };

    function Delete(restaurant, callback) {
        $http({
            method: 'POST',
            url: baseUrl,
            data: restaurant
        })
        .success(function (data, status, headers, config) {
            callback(status);
        })
        .error(function (data, status) {
            console.log(JSON.stringify(data));
            console.log(JSON.stringify(status));
        });
    }
};
