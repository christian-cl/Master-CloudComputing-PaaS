package api;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

@Path("/restaurantes")
public class RestauranteService {

	@GET
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Restaurante getRestaurantes() {
		//DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//Query query = new Query("Restaurante").addSort("nombre",Query.SortDirection.ASCENDING);
		//List<Entity> entities = ds.prepare(query).asList(FetchOptions.Builder.withLimit(20));
		List<Restaurante> restaurantes = new ArrayList<Restaurante>();
		/*for(Entity e: entities){
			String nombre = (String) e.getProperty("nombre");
			String email = (String) e.getProperty("email");
			String direccion = (String) e.getProperty("direccion");
			String telefono = (String) e.getProperty("telefono");
			String descripcion = (String) e.getProperty("descripcion");
			restaurantes.add(new Restaurante(nombre, email, direccion, telefono, descripcion));
		}*/
		restaurantes.add(new Restaurante("nombre", "email", "direccion", "telefono", "descripcion"));
		//return Response.ok(new Restaurante("nombre", "email", "direccion", "telefono", "descripcion")).build();
		return new Restaurante("nombre", "email", "direccion", "telefono", "descripcion");
	}
}
