package gamestudio.server.webservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Favourite;
import gamestudio.service.FavouriteService;

@Path("/favourite")
public class FavouriteRestService {

	@Autowired
	private FavouriteService favouriteService;

	// http://localhost:8080/rest/score/mines
	@GET
	@Path("/{game}")
	@Produces("application/json")
	public List<Favourite> getFavourite(@PathParam("username") String username) {
		return favouriteService.getFavourite(username);
	}

	@POST
	@Consumes
	public Response addFavourite(Favourite favourite) {
		favouriteService.addFavourite(favourite);
		return Response.ok().build();

	}

}
