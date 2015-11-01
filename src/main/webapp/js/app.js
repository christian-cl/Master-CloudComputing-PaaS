var app = angular.module('app',[]);

app.controller('MainController', function($scope, RestaurantService) {
	$scope.section = 1; //1 inicio, 2 añadir restaurante, 3 contacto
	$scope.area = 0; //0 visualización, 1 modificar datos
	$scope.flag = true; //false si estamos en modo restaurante
	$scope.restaurants = {}; //Lista de restaurantes
	$scope.restaurant = {}; //Restaurante seleccionado
	$scope.newrestaurant = {}; //Nuevo restaurante a añadir
	$scope.googleUser = {}; //Usuario loggeado
    $scope.logged = false; //True si se ha iniciado sesión
    $scope.marker = false; //Apunta a la posición del nuevo restaurante
    $scope.updateMarker = false; //Apunta a la posición del restaurante modificado
    $scope.changed = false; //True cada vez que se añade, modifica o elimina un restaurante (para recargar el mapa principal)
    var rests = [
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
    	$scope.setMap();
		$scope.setSelectorMap();
    });
	//$scope.restaurants = rests; //Para pruebas en modo local

	$scope.setMap=function(){
		$scope.map = new google.maps.Map(document.getElementById('map'), {
		    center: {lat: 36.711622, lng: -4.420445},
		    zoom: 8
		  });
		$scope.restaurants.forEach(function(rest){
			var marker = new google.maps.Marker({
    			position: new google.maps.LatLng(rest.latitud, rest.longitud),
    			map: $scope.map,
    			title: rest.nombre
    		});
    		var infowindow = new google.maps.InfoWindow({
    			content: "<span>"+rest.nombre+"</span>"
    		});
    		google.maps.event.addListener(marker, 'click', function() {
    			infowindow.open($scope.map,marker);
    		});
		})
	}
	$scope.setSelectorMap=function(){
		$scope.selMap = new google.maps.Map(document.getElementById('selectorMap'), {
		    center: {lat: 36.711622, lng: -4.420445},
		    zoom: 8
		  });
		google.maps.event.addListener($scope.selMap, 'click', function(event) {                
	        var clickedLocation = event.latLng;
	        if($scope.marker === false){
	            $scope.marker = new google.maps.Marker({
	                position: clickedLocation,
	                map: $scope.selMap,
	            });
	        } else{
	            $scope.marker.setPosition(clickedLocation);
	        }
	        $scope.newrestaurant.latitud = $scope.marker.getPosition().lat();
	        $scope.newrestaurant.longitud = $scope.marker.getPosition().lng();
	        $scope.$apply();
	    });
	}
	$scope.$watch('section', function () {
		if($scope.section == 2) {
			window.setTimeout(function(){
	        	google.maps.event.trigger($scope.selMap, 'resize');
	        	$scope.selMap.setCenter(new google.maps.LatLng(36.711622, -4.420445));
	        },100);
		}
    });
    $scope.$watch('flag', function () {
		if(!$scope.flag) {
			window.setTimeout(function(){
	        	google.maps.event.trigger($scope.infoMap, 'resize');
	        	$scope.infoMap.setCenter(new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud));
	        },100);
		}
    });
    $scope.$watch('changed',function(){
    	if($scope.changed){
    		window.setTimeout(function(){
	        	google.maps.event.trigger($scope.map, 'resize');
	        	$scope.map.setCenter(new google.maps.LatLng(36.711622, -4.420445));
	        },100);
	        $scope.changed=false;
    	}
    })
    $scope.$watch('area', function () {
		if($scope.area == 0 && !$scope.flag) {
			window.setTimeout(function(){
	        	google.maps.event.trigger($scope.infoMap, 'resize');
	        	$scope.infoMap.setCenter(new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud));
	        },100);
		}
    });
    $scope.$watch('area', function () {
		if($scope.area == 1) {
			window.setTimeout(function(){
	        	google.maps.event.trigger($scope.updateMap, 'resize');
	        	$scope.updateMap.setCenter(new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud));
	        },100);
		}
    });
    $scope.addRestaurant = function(){
		if($scope.add.$valid){
			var restaurantAdded = {
				'nombre':$scope.newrestaurant.nombre,
				'email':$scope.newrestaurant.email,
				'direccion':$scope.newrestaurant.direccion,
				'telefono':$scope.newrestaurant.telefono,
				'descripcion':$scope.newrestaurant.descripcion,
				'latitud':$scope.newrestaurant.latitud,
				'longitud':$scope.newrestaurant.longitud
			};
			RestaurantService.insert(restaurantAdded,function(status){
				RestaurantService.getAll(function(data){
			    	$scope.restaurants = data;
			    	alert('Restaurante añadido correctamente');
			    	$scope.newrestaurant = {};
			    	$scope.marker = false;
					$scope.showRestaurantsList();
			    });
			});
		}else{
			alert("Algunos campos no se han añadido, revise el formulario");
		}
    }
    $scope.showRestaurant = function(index){
    	$scope.flag = false;
    	$scope.restaurant = $scope.restaurants[index];
    	$scope.restaurant.newemail = $scope.restaurant.email;
    	$scope.infoMap = new google.maps.Map(document.getElementById('infoMap'), {
		    center: {lat: parseInt($scope.restaurant.latitud), lng: parseInt($scope.restaurant.longitud)},
		    zoom: 8
		  });
    	var marker = new google.maps.Marker({
			position: new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud),
			map: $scope.infoMap,
			title: $scope.restaurant.nombre
		});
		$scope.updateMap = new google.maps.Map(document.getElementById('updateMap'), {
		    center: {lat: parseInt($scope.restaurant.latitud), lng: parseInt($scope.restaurant.longitud)},
		    zoom: 8
		  });
    	var marker2 = new google.maps.Marker({
			position: new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud),
			map: $scope.updateMap,
			title: $scope.restaurant.nombre
		});
		google.maps.event.addListener($scope.updateMap, 'click', function(event) {                
	        var clickedLocation = event.latLng;
	        marker2.setMap(null);
	        if($scope.updateMarker === false){
	            $scope.updateMarker = new google.maps.Marker({
	                position: clickedLocation,
	                map: $scope.updateMap,
	            });
	        } else{
	            $scope.updateMarker.setPosition(clickedLocation);
	        }
	        $scope.restaurant.latitud = $scope.updateMarker.getPosition().lat();
	        $scope.restaurant.longitud = $scope.updateMarker.getPosition().lng();
	        $scope.$apply();
	    });
    }
    $scope.showRestaurantsList = function(){
    	if($scope.area==1 && !$scope.flag){
    		RestaurantService.getAll(function(data){
		    	$scope.restaurants = data;
		    	$scope.setMap();
		    	$scope.section = 1;
				$scope.area = 0;
		    	$scope.flag = true;
		    });
    	}else{
    		$scope.setMap();
	    	$scope.section = 1;
			$scope.area = 0;
	    	$scope.flag = true;
    	}
    	$scope.changed=true;
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
				'latitud':$scope.restaurant.latitud,
				'longitud':$scope.restaurant.longitud
			};
    		RestaurantService.delete(restaurantRemoved,function(status){
    			RestaurantService.getAll(function(data){
			    	$scope.restaurants = data;
			    	alert('Restaurante eliminado correctamente');
					$scope.showRestaurantsList();
			    });
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
				'latitud':$scope.restaurant.latitud,
				'longitud':$scope.restaurant.longitud
			};
			RestaurantService.update(restaurantUpdated,function(status){
				RestaurantService.getAll(function(data){
			    	$scope.restaurants = data;
			    	alert('Restaurante actualizado correctamente');
			    	$scope.infoMap = new google.maps.Map(document.getElementById('infoMap'), {
					    center: {lat: parseInt($scope.restaurant.latitud), lng: parseInt($scope.restaurant.longitud)},
					    zoom: 8
					  });
			    	var marker = new google.maps.Marker({
						position: new google.maps.LatLng($scope.restaurant.latitud, $scope.restaurant.longitud),
						map: $scope.infoMap,
						title: $scope.restaurant.nombre
					});
					$scope.updateMarker = false;
			    	$scope.restaurant.email=$scope.restaurant.newemail;
					$scope.area = 0;
			    });
			});
		}else{
			alert("Algunos campos no se han añadido, revise el formulario");
		}
    }
    $scope.startApp = function() {
        gapi.load('auth2', function(){
          // Retrieve the singleton for the GoogleAuth library and set up the client.
          auth2 = gapi.auth2.init({
          	client_id: '882920806176-m1b6tbg86vgme4se7pauaor6lsfhi0d3.apps.googleusercontent.com',
          	cookiepolicy: 'single_host_origin',
            // Request scopes in addition to 'profile' and 'email'
            //scope: 'additional_scope'
        });
          $scope.attachSignin(document.getElementById('login1'));
          $scope.attachSignin(document.getElementById('login2'));
      });
    }
    $scope.attachSignin =  function(element){
        auth2.attachClickHandler(element, {},
            function(googleUser) {
              $scope.onSignIn(googleUser);
          }, function(error) {
              alert(JSON.stringify(error, undefined, 2));
          });
    }
    $scope.onSignIn=function(googleUser) {
    	$(".avatar").each(function() {
			$(this).attr("src",googleUser.getBasicProfile().getImageUrl());
		});
    	$scope.logged = true;
    	$scope.googleUser = googleUser;
		$scope.$apply();
		console.log("Sesión iniciada.");
        console.log(googleUser);
        var profile = googleUser.getBasicProfile();
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());
    }
    $scope.signOut=function(){
    	var r = confirm("¿Deseas cerrar sesión?");
    	if(r){
    		var auth2 = gapi.auth2.getAuthInstance();
		    auth2.signOut().then(function () {
		      console.log('Sesión finalizada.');
		      $scope.logged=false;
		      $scope.$apply();
		    });
    	}
    }
    $scope.startApp();
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