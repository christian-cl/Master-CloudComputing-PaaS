/**
 * Created by christian on 20/10/15.
 */
angular.module('app')
    .controller('MainController', function ($scope, RestaurantService) {

        $scope.flag = true;
        var rest = [{name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'}];
        //RestaurantService.GetAll(functio)
        $scope.restaurants = rest;
    });