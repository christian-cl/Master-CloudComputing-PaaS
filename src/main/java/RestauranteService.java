import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
 
@Path("/restaurantes")
public class RestauranteService {
	
	private class Restaurante {
		private String nombre, email, direccion, telefono, descripcion;
		public Restaurante(String nombre, String email, String direccion, String telefono, String descripcion) {
			super();
			this.nombre = nombre;
			this.email = email;
			this.direccion = direccion;
			this.telefono = telefono;
			this.descripcion = descripcion;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getTelefono() {
			return telefono;
		}
		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getRestaurantes() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Restaurante").addSort("nombre",Query.SortDirection.ASCENDING);
		List<Entity> entities = ds.prepare(query).asList(FetchOptions.Builder.withLimit(20));
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
		return Response.status(201).entity(restaurantes).build();
	}
}
