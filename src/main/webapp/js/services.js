angular.module('app').factory('RestaurantService', RestaurantService);

RestaurantService.$inject = ['$http'];
function RestaurantService($http) {
    var baseUrl = '/api/restaurantes';
    var service = {};

    service.getAll = GetAll;
    service.insert = Insert;
    service.delete = Delete;
    service.update = Update;

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
            alert('No se pudo recuperar la lista de restaurantes.');
            console.log(data);
            console.log(status);
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
            alert('Algo salió mal... ¿El restaurante ya existe?')
            console.log(data);
            console.log(status);
        });
    };

    function Update(restaurant, callback) {
        $http({
            method: 'PUT',
            url: baseUrl,
            data: restaurant
        })
        .success(function (data, status, headers, config) {
            callback(status);
        })
        .error(function (data, status) {
            alert('No se pudo actualizar el restaurante');
            console.log(data);
            console.log(status);
        });
    };

    function Delete(restaurant, callback) {
        $http({
            method: 'POST',
            url: baseUrl+'/delete',
            data: restaurant
        })
        .success(function (data, status, headers, config) {
            callback(status);
        })
        .error(function (data, status) {
            alert('No se pudo eliminar el restaurante.')
            console.log(data);
            console.log(status);
        });
    }
};
