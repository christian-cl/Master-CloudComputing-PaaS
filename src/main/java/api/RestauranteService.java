package api;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;

import api.flickr.Flickr;
import api.flickr.Photo;


@Path("/restaurantes")
public class RestauranteService {
	
	@GET
	@Path("/populate")
	public Response populateDB() throws IOException, JSONException {
		
		JSONObject e1 = new JSONObject();
		e1.put("nombre", "La Trastienda");
		e1.put("email", "latrastienda18@gmail.com");
		e1.put("direccion", "Calle Cervantes, nº 10 - Málaga");
		e1.put("telefono", "951862145");
		e1.put("descripcion", "El Restaurante La Trastienda se "
				+ "encuentra ubicado junto a la Plaza de Toros de la Malagueta y a escasos 50 metros del Paseo Marítimo y de la playa, así como del centro histórico de Málaga.");
		e1.put("latitud", "37.046567");
		e1.put("longitud", "-5.015616");
		e1.put("etiqueta", "restauranteLaTrastienda");
		
		JSONObject e2 = new JSONObject();
		e2.put("nombre", "El Reservado");
		e2.put("email", "elreservado46@gmail.com");
		e2.put("direccion", "Calle Acebuchal, 15, Autovía del Mediterráneo, Salida 256 - Rincón de la Victoria");
		e2.put("telefono", "951234789");
		e2.put("descripcion", "Cuenta con una zona de tapeo donde podrás degustar un gran surtido de tapas tradicionales, hamburguesas gourmet o tapas dulces. ");
		e2.put("latitud", "36.725604");
		e2.put("longitud", "-4.255078");
		e2.put("etiqueta", "restauranteElReservado");
		
		JSONObject e3 = new JSONObject();
		e3.put("nombre", "Indian City");
		e3.put("email", "indiancity76@gmail.com");
		e3.put("direccion", "Avenida Antonio Machado, 44-46, 29630 Benalmádena, Málaga, España - Benalmádena");
		e3.put("telefono", "951234951");
		e3.put("descripcion", "El exótico Restaurante Indian City está situado en Benalmádena, Málaga, un enclave único para disfrutar del buen tiempo de la Costa del Sol, "
				+ "y a pocos pasos de Puerto Marina.");
		e3.put("latitud", "36.600891");
		e3.put("longitud", "-4.515973");
		e3.put("etiqueta", "restauranteIndianCity");
		
		JSONArray arr = new JSONArray();
		arr.put(e1);
		arr.put(e2);
		arr.put(e3);

		URL url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setRequestMethod("POST");
		urlConnection.setRequestProperty("Content-Type", "application/json");
		urlConnection.setDoOutput(true);
		urlConnection.setRequestProperty("Content-Length", String.valueOf(arr.toString().length()));
		byte[] postDataBytes = arr.toString().getBytes("UTF-8");
		urlConnection.getOutputStream().write(postDataBytes);
		
		InputStream input = urlConnection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = "", reply = "";
		while ((line = reader.readLine()) != null) {
			reply += line;
		}
		System.out.println(arr.toString());
		System.out.println(reply);
		
		urlConnection.disconnect();
		return Response.ok("Datastore rellenado correctamente.").build();
	}

	@GET
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public List<Restaurante> getRestaurantes(@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("20") @QueryParam("limit") int limit) throws IOException {
		URL url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ&sk="+((page-1)*limit)+"&l="+limit);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setDoOutput(true);
		
		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
		String response = "",output;
		while ((output = br.readLine()) != null) {
			response+=output;
		}
		System.out.println(response);
		
		JsonElement jelement = new JsonParser().parse(response);
		JsonArray jarray = jelement.getAsJsonArray();
		
		List<Restaurante> restaurantes = new ArrayList<Restaurante>();
		for(int i=0;i<jarray.size();i++){
			JsonObject jobject = jarray.get(i).getAsJsonObject();
			String nombre = jobject.get("nombre").toString().replace("\"", "");
			String email = jobject.get("email").toString().replace("\"", "");
			String direccion = jobject.get("direccion").toString().replace("\"", "");
			String telefono = jobject.get("telefono").toString().replace("\"", "");
			String descripcion = jobject.get("descripcion").toString().replace("\"", "");
			String latitud = jobject.get("latitud").toString().replace("\"", "");
			String longitud = jobject.get("longitud").toString().replace("\"", "");
			String etiqueta = jobject.get("etiqueta").toString().replace("\"", "");
			List<String> links = findPhotosUrlByTag(etiqueta);
			restaurantes.add(new Restaurante(nombre, email, direccion, telefono, descripcion,latitud,longitud,etiqueta,links));
		}
		
		urlConnection.disconnect();
		System.out.println(restaurantes);
		return restaurantes;
	}
	
	private List<String> findPhotosUrlByTag(String tag) throws IOException {

		URL url = new URL(
				"https://api.flickr.com/services/rest/?method=flickr.photos.search&format=json&api_key=d0e0a8fae98011fc86171d78743659af&tags="
						+ tag);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));
		String line = "";
		String reply = "";
		while ((line = reader.readLine()) != null) {
			reply += line;
		}
		reader.close();

		System.out.println("Reply: " + reply);
		reply=reply.replace("jsonFlickrApi(", "");
		reply=reply.substring(0, reply.length() - 1);

		
		Gson gson = new Gson();
		Flickr fr = gson.fromJson(reply, Flickr.class);

		List<String> links = new ArrayList<String>();
		for (Photo photo : fr.getPhotos().getPhoto()) {
			Integer farm = photo.getFarm();
			String server = photo.getServer();
			String id = photo.getId();
			String secret = photo.getSecret();

			String link = "http://farm" + farm + ".staticflickr.com/" + server
					+ "/" + id + "_" + secret + "_" + "z" + ".jpg";
			links.add(link);
		}

		return links;

	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response addRestaurante(Restaurante r) throws IOException, JSONException {
		String query = URLEncoder.encode("{\"email\":\""+r.getEmail()+"\"}");
		URL url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ&q="+query);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setDoOutput(true);
		
		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
		String response = "",output;
		while ((output = br.readLine()) != null) {
			response+=output;
		}
		System.out.println(response);
		urlConnection.disconnect();
		
		JsonElement jelement = new JsonParser().parse(response);
		if(jelement.toString().equals("[]")){
			JSONObject e1 = new JSONObject();
			e1.put("nombre", r.getNombre());
			e1.put("email", r.getEmail());
			e1.put("direccion", r.getDireccion());
			e1.put("telefono", r.getTelefono());
			e1.put("descripcion", r.getDescripcion());
			e1.put("latitud", r.getLatitud());
			e1.put("longitud", r.getLongitud());
			e1.put("etiqueta", r.getEtiqueta());
			
			url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Content-Length", String.valueOf(e1.toString().length()));
			byte[] postDataBytes = e1.toString().getBytes("UTF-8");
			urlConnection.getOutputStream().write(postDataBytes);
			
			br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
			response = "";
			while ((output = br.readLine()) != null) {
				response+=output;
			}
			System.out.println(response);
			
			urlConnection.disconnect();
			return Response.status(Status.OK).build();
		}else{
			return Response.serverError().build();
		}
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response updateRestaurante(Restaurante r) throws IOException, JSONException {
		String query = URLEncoder.encode("{\"email\":\""+r.getEmail()+"\"}");
		URL url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ&q="+query);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setDoOutput(true);
		
		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
		String response = "",output;
		while ((output = br.readLine()) != null) {
			response+=output;
		}
		System.out.println(response);
		urlConnection.disconnect();
		
		JsonElement jelement = new JsonParser().parse(response);
		if(jelement.isJsonNull()){
			return Response.serverError().build();
		}else{
			JsonArray jarray = jelement.getAsJsonArray();
			String docId = jarray.get(0).getAsJsonObject().get("_id").getAsJsonObject().get("$oid").toString().replace("\"", "");
			
			JSONObject e1 = new JSONObject();
			e1.put("nombre", r.getNombre());
			e1.put("email", r.getEmail());
			e1.put("direccion", r.getDireccion());
			e1.put("telefono", r.getTelefono());
			e1.put("descripcion", r.getDescripcion());
			e1.put("latitud", r.getLatitud());
			e1.put("longitud", r.getLongitud());
			e1.put("etiqueta", r.getEtiqueta());
			
			url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes/"+docId+"?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setRequestMethod("PUT");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("Content-Length", String.valueOf(e1.toString().length()));
			byte[] postDataBytes = e1.toString().getBytes("UTF-8");
			urlConnection.getOutputStream().write(postDataBytes);
			
			br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
			response = "";
			while ((output = br.readLine()) != null) {
				response+=output;
			}
			System.out.println(response);
			
			urlConnection.disconnect();
			
			return Response.status(Status.OK).build();
		}
	}
	
	@POST
	@Path("/delete")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response removeRestaurante(Restaurante r) throws IOException, JSONException {
		String query = URLEncoder.encode("{\"email\":\""+r.getEmail()+"\"}");
		URL url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ&q="+query);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setRequestProperty("Accept", "application/json");
		urlConnection.setDoOutput(true);
		
		Charset charset = Charset.forName("UTF-8");
		BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
		String response = "",output;
		while ((output = br.readLine()) != null) {
			response+=output;
		}
		System.out.println(response);
		urlConnection.disconnect();
		
		JsonElement jelement = new JsonParser().parse(response);
		if(jelement.isJsonNull()){
			return Response.serverError().build();
		}else{
			JsonArray jarray = jelement.getAsJsonArray();
			String docId = jarray.get(0).getAsJsonObject().get("_id").getAsJsonObject().get("$oid").toString().replace("\"", "");
			
			url = new URL("https://api.mongolab.com/api/1/databases/mongodatabase/collections/restaurantes/"+docId+"?apiKey=xNBAwt0MLE1oCNjTjUbSEcZAUzeUvXnZ");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setRequestMethod("DELETE");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			
			br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream()),charset));
			response = "";
			while ((output = br.readLine()) != null) {
				response+=output;
			}
			System.out.println(response);
			
			urlConnection.disconnect();
			
			return Response.status(Status.OK).build();
		}
	}
}
