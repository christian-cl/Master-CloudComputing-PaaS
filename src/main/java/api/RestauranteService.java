package api;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;

@Path("/restaurantes")
public class RestauranteService {
	
	@GET
	@Path("/populate")
	public Response populateDB() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity e1 = new Entity("Restaurante");
		e1.setProperty("nombre", "La Trastienda");
		e1.setProperty("email", "latrastienda18@gmail.com");
		e1.setProperty("direccion", "Calle Cervantes, n� 10 - M�laga");
		e1.setProperty("telefono", "951862145");
		e1.setProperty("descripcion", "El Restaurante La Trastienda se "
				+ "encuentra ubicado junto a la Plaza de Toros de la Malagueta y a escasos 50 metros del Paseo Mar�timo y de la playa, as� como del centro hist�rico de M�laga.");
		e1.setProperty("latitud", "37.206546");
		e1.setProperty("longitud", "37.206546");
		Entity e2 = new Entity("Restaurante");
		e2.setProperty("nombre", "El Reservado");
		e2.setProperty("email", "elreservado46@gmail.com");
		e2.setProperty("direccion", "Calle Acebuchal, 15, Autov�a del Mediterr�neo, Salida 256 - Rinc�n de la Victoria");
		e2.setProperty("telefono", "951234789");
		e2.setProperty("descripcion", "Cuenta con una zona de tapeo donde podr�s degustar un gran surtido de tapas tradicionales, hamburguesas gourmet o tapas dulces. ");
		e2.setProperty("latitud", "36.725604");
		e2.setProperty("longitud", "-4.255078");
		Entity e3 = new Entity("Restaurante");
		e3.setProperty("nombre", "Indian City");
		e3.setProperty("email", "indiancity76@gmail,com");
		e3.setProperty("direccion", "Avenida Antonio Machado, 44-46, 29630 Benalm�dena, M�laga, Espa�a - Benalm�dena");
		e3.setProperty("telefono", "951234951");
		e3.setProperty("descripcion", "El ex�tico Restaurante Indian City est� situado en Benalm�dena, M�laga, un enclave �nico para disfrutar del buen tiempo de la Costa del Sol, "
				+ "y a pocos pasos de Puerto Marina.");
		e3.setProperty("latitud", "36.600891");
		e3.setProperty("longitud", "-4.515973");
		ds.put(e2);
		ds.put(e1);
		ds.put(e3);
		return Response.ok("Datastore rellenado correctamente.").build();
	}

	@GET
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Restaurante> getRestaurantes(@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("20") @QueryParam("limit") int limit) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Restaurante").addSort("nombre",Query.SortDirection.ASCENDING);
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(limit);
		PreparedQuery pq = ds.prepare(query);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		int pageCount = 1;
		while(pageCount<page){
			System.out.println("Hola");
			fetchOptions.startCursor(results.getCursor());
			results = pq.asQueryResultList(fetchOptions);
			pageCount++;
		}
		List<Restaurante> restaurantes = new ArrayList<Restaurante>();
		for(Entity e: results){
			String nombre = (String) e.getProperty("nombre");
			String email = (String) e.getProperty("email");
			String direccion = (String) e.getProperty("direccion");
			String telefono = (String) e.getProperty("telefono");
			String descripcion = (String) e.getProperty("descripcion");
			String latitud = (String) e.getProperty("latitud");
			String longitud = (String) e.getProperty("longitud");
			restaurantes.add(new Restaurante(nombre, email, direccion, telefono, descripcion,latitud,longitud));
		}
		return restaurantes;
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response addRestaurante(Restaurante r) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, r.getEmail());
		Query query = new Query("Restaurante").setFilter(propertyFilter);
		QueryResultList<Entity> results = ds.prepare(query).asQueryResultList(FetchOptions.Builder.withLimit(1));
		if(results.isEmpty()){
			Entity e1 = new Entity("Restaurante");
			e1.setProperty("nombre", r.getNombre());
			e1.setProperty("email", r.getEmail());
			e1.setProperty("direccion", r.getDireccion());
			e1.setProperty("telefono", r.getTelefono());
			e1.setProperty("descripcion",r.getDescripcion());
			e1.setProperty("latitud", r.getLatitud());
			e1.setProperty("longitud", r.getLongitud());
			ds.put(e1);
			return Response.status(Status.OK).build();
		}else{
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response updateRestaurante(Restaurante r) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, r.getEmail());
		Query query = new Query("Restaurante").setFilter(propertyFilter);
		QueryResultList<Entity> results = ds.prepare(query).asQueryResultList(FetchOptions.Builder.withLimit(1));
		if(results.isEmpty()){
			return Response.serverError().build();
		}else{
			Entity e1 = results.get(0);
			e1.setProperty("nombre", r.getNombre());
			e1.setProperty("email", r.getNewemail());
			e1.setProperty("direccion", r.getDireccion());
			e1.setProperty("telefono", r.getTelefono());
			e1.setProperty("descripcion",r.getDescripcion());
			e1.setProperty("latitud", r.getLatitud());
			e1.setProperty("longitud", r.getLongitud());
			ds.put(e1);
			return Response.status(Status.OK).build();
		}
	}
	
	@DELETE
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response removeRestaurante(Restaurante r) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("email", FilterOperator.EQUAL, r.getEmail());
		Query query = new Query("Restaurante").setFilter(propertyFilter);
		QueryResultList<Entity> results = ds.prepare(query).asQueryResultList(FetchOptions.Builder.withLimit(1));
		if(!results.isEmpty()){
			ds.delete(results.get(0).getKey());
			return Response.status(Status.OK).build();
		}else{
			return Response.serverError().build();
		}
	}
}
