/**
 * Created by christian on 20/10/15.
 */
app.directive('restaurantes', function(){
    return {
        restrict: 'E',
        templateUrl: 'restaurantes.html',
        controller: 'RestaurantsController'
        //controller: 'MainController'
    };
}).directive('restaurantesDetails', function(){
    return {
        restrict: 'E',
        templateUrl: 'restaurant-details.html',
        controller: 'DetailsController'
        //controller: 'MainController'
    };
});
