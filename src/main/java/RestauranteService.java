import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/restaurante")
public class RestauranteService {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON() {
		String result = "Prueba";
		return Response.status(201).entity(result).build();
	}
}
