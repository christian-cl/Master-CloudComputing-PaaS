var app = angular.module('app',[]);

app.controller('MainController', function($scope, RestaurantService) {
	$scope.flag = true;
    var rest = [{name: 'la trastienda', subname : ' bla bla bla'},
        {name: 'la trastienda', subname : ' bla bla bla'},
        {name: 'la trastienda', subname : ' bla bla bla'},
        {name: 'la trastienda', subname : ' bla bla bla'}];
    //RestaurantService.GetAll(functio)
    $scope.restaurants = rest;
});