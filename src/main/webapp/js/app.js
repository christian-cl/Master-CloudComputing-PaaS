var app = angular.module('app',[]);

app.controller('MainController', function($scope, RestaurantService) {
	$scope.section = 1; //1 inicio, 2 añadir restaurante, 3 contacto
	$scope.area = 0; //0 visualización, 1 modificar datos
	$scope.flag = true; //false si estamos en modo restaurante
	$scope.restaurants = {}; //Lista de restaurantes
	$scope.restaurant = {}; //Restaurante seleccionado
	$scope.newrestaurant = {}; //Nuevo restaurante a añadir
    var rest = [
	    {
	        nombre: "El Reservado",
	        email: "elreservado46@gmail.com",
	        direccion: "Calle Acebuchal, 15, Autovía del Mediterráneo, Salida 256 - Rincón de la Victoria",
	        telefono: "951234789",
	        descripcion: "Cuenta con una zona de tapeo donde podrás degustar un gran surtido de tapas tradicionales, hamburguesas gourmet o tapas dulces. ",
	        latitud: "36.725604",
	        longitud: "-4.255078"
	    },
	    {
	        nombre: "Indian City",
	        email: "indiancity76@gmail,com",
	        direccion: "Avenida Antonio Machado, 44-46, 29630 Benalmádena, Málaga, España - Benalmádena",
	        telefono: "951234951",
	        descripcion: "El exótico Restaurante Indian City está situado en Benalmádena, Málaga, un enclave único para disfrutar del buen tiempo de la Costa del Sol, y a pocos pasos de Puerto Marina.",
	        latitud: "36.600891",
	        longitud: "-4.515973"
	    },
	    {
	        nombre: "La Trastienda",
	        email: "latrastienda18@gmail.com",
	        direccion: "Calle Cervantes, nº 10 - Málaga",
	        telefono: "951862145",
	        descripcion: "El Restaurante La Trastienda se encuentra ubicado junto a la Plaza de Toros de la Malagueta y a escasos 50 metros del Paseo Marítimo y de la playa, así como del centro histórico de Málaga.",
	        latitud: "37.206546",
	        longitud: "37.206546"
	    }
	];
    RestaurantService.getAll(function(data){
    	$scope.restaurants = data;
    });
	//$scope.restaurants = rest; //Para pruebas en modo local
    
    $scope.addRestaurant = function(){
		if($scope.add.$valid){
			var restaurantAdded = {
				'nombre':$scope.newrestaurant.nombre,
				'email':$scope.newrestaurant.email,
				'direccion':$scope.newrestaurant.direccion,
				'telefono':$scope.newrestaurant.telefono,
				'descripcion':$scope.newrestaurant.descripcion,
				'latitud':'',
				'longitud':''
			};
			RestaurantService.insert(restaurantAdded,function(status){
				alert('Restaurante añadido correctamente');
				window.location.href = "index.html";
			});
		}else{
			alert("Algunos campos no se han añadido, revise el formulario");
		}
    }

    $scope.showRestaurant = function(index){
    	$scope.flag = false;
    	$scope.restaurant = $scope.restaurants[index];
    	$scope.restaurant.newemail = $scope.restaurant.email;
    }

    $scope.removeRestaurant = function(){
    	var r = confirm("¿Seguro quieres eliminar este restaurante?");
    	if(r){
    		var restaurantRemoved = {
				'nombre':$scope.restaurant.nombre,
				'email':$scope.restaurant.email,
				'direccion':$scope.restaurant.direccion,
				'telefono':$scope.restaurant.telefono,
				'descripcion':$scope.restaurant.descripcion,
				'latitud':'',
				'longitud':''
			};
    		RestaurantService.delete(restaurantRemoved,function(status){
    			alert('Restaurante eliminado correctamente');
				window.location.href = "index.html";
    		});
    	}
    }

    $scope.updateRestaurant = function(){
		if($scope.update.$valid){
			var restaurantUpdated = {
				'nombre':$scope.restaurant.nombre,
				'email':$scope.restaurant.email,
				'newemail':$scope.restaurant.newemail,
				'direccion':$scope.restaurant.direccion,
				'telefono':$scope.restaurant.telefono,
				'descripcion':$scope.restaurant.descripcion,
				'latitud':'',
				'longitud':''
			};
			RestaurantService.update(restaurantUpdated,function(status){
				alert('Restaurante actualizado correctamente');
				window.location.href = "index.html";
			});
		}else{
			alert("Algunos campos no se han añadido, revise el formulario");
		}
    }
});

app.directive("reload", function () {
    return function (scope, element, attrs) {
        scope.$watch("restaurants", function (value) {
            var val = value || null;            
            if (val){
			    $('.hover-aware').each(function () {
			        var $hover = $(this).find('.hover');
			        if ($hover.length) {
			            var $img = $(this).find('img.projects');
			            $(this).hover(function () {
			                $hover.show();
			                $img.css('opacity', 0.2);
			            }, function () {
			                $hover.hide();
			                $img.css('opacity', 1);
			            });
			        }
			    });
            }
        });
    };
});