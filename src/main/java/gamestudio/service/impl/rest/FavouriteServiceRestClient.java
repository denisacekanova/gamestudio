package gamestudio.service.impl.rest;

import gamestudio.entity.Favourite;
import gamestudio.service.FavouriteService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class FavouriteServiceRestClient implements FavouriteService {
	private static final String URL = "http://localhost:8080/rest/score";

	@Override
	public void addFavourite(Favourite favourite) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(favourite, MediaType.APPLICATION_JSON), Response.class);
	}

	@Override
	public List<Favourite> getFavourite(String username) {
		Client client = ClientBuilder.newClient();
		return client.target(URL).path("/" + username).request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Favourite>>() {});
	}

	@Override
	public boolean isFavourite(Favourite favourite) {
		// TODO Auto-generated method stub
		return true;
	}

}
