/**
 * Created by christian on 20/10/15.
 */
angular.module('app')
    .controller('MainController', function ($scope, RestaurantService) {
        $scope.hola = "----------";
        this.hola = "hola";
        $scope.flag = true;
        var rest = [{name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'}];
        //RestaurantService.GetAll(functio)
        $scope.restaurants = rest;
    })
    .controller('RestaurantsController', function ($scope) {
        $scope.hola = "----------";
        this.hola = "hola";
        $scope.flag = true;
        var rest = [{name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'},
            {name: 'la trastienda', subname : ' bla bla bla'}];
        $scope.restaurants = rest;

    })
    .controller('DetailsController', function ($scope) {
        $scope.hola = "----------";
        this.hola = "hola";
    })