package api.flickr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.repackaged.com.google.gson.Gson;

public class FlickrService {

	public List<String> findPhotosUrlByTag(String tag) throws IOException {

		URL url = new URL(
				"https://api.flickr.com/services/rest/?method=flickr.photos.search&format=json&api_key= 5416eb9b35def4857ddddf4df48a6782&tags="
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

//	public static void main(String args[]) throws IOException{
//		List<String> links = findPhotosUrlByTag("elReservado");
//		for (int i = 0; i < links.size(); i++) {
//			System.out.println(links.get(i));
//		}
//	}
	
}
