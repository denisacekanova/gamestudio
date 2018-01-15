package gamestudio.service;

import java.util.List;

import gamestudio.entity.Favourite;

public interface FavouriteService {
	void addFavourite(Favourite favourite);
	boolean isFavourite(Favourite favourite);
	List<Favourite> getFavourite(String username);
}
