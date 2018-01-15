package gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Favourite {

	@Id
	@GeneratedValue
	private int ident;
	private String username;
	private String game;

	public Favourite(String username, String game) {
		super();
		this.username = username;
		this.game = game;
	}

	public Favourite() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public String getGame() {
		return game;
	}

	public void setUsername(String username) {
		this.username = System.getProperty("user.name");
	}

	public void setGame(String game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return String.format("Favourites  %d  %s  %s", ident, username, game);

	}
}