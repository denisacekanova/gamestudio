package gamestudio.server.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Player;
import gamestudio.service.FavouriteService;
import gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
	@Autowired
	private PlayerService playerService;
	@Autowired
	private FavouriteService favouriteService;
	private Player loggedPlayer;
	private String message;

	@RequestMapping("/")
	public String index(Model model) {
		if (isLogged()) {
			model.addAttribute("favourite", favouriteService.getFavourite(getLoggedPlayer().getLogin()));
		}
		return "index";
	}

	@RequestMapping("/user")
	public String user(Model model) {
		if (isLogged()) {
			model.addAttribute("favourite", favouriteService.getFavourite(getLoggedPlayer().getLogin()));
		}
		return "login";
	}

	@RequestMapping("/login")
	public String login(Player player, Model model) {
		if (isLogged()) {
			model.addAttribute("favourite", favouriteService.getFavourite(getLoggedPlayer().getLogin()));
		}
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		return isLogged() ? "index" : "login";
	}

	@RequestMapping("/register")
	public String register(Player player, Model model, String login) {
		if (isLogged()) {
			model.addAttribute("favourite", favouriteService.getFavourite(getLoggedPlayer().getLogin()));
		}

		try {
			if (!playerService.isLogin(player.getLogin())) {
				playerService.register(player);
				loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
				model.addAttribute("message", "");
				return "index";

			} else {
				model.addAttribute("message", "Username allready exist! Try another one !");

			}

		} catch (NoResultException e) {

		}
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(Model model) {
		if (isLogged()) {
			model.addAttribute("favourite", favouriteService.getFavourite(getLoggedPlayer().getLogin()));
		}
		loggedPlayer = null;
		return "login";
	}

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}

	public String getMessage() {
		return message;
	}

}
