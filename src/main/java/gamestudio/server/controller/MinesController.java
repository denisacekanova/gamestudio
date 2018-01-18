package gamestudio.server.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.game.minesweeper.core.Clue;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.game.minesweeper.core.GameState;
import gamestudio.game.minesweeper.core.Tile;
import gamestudio.game.minesweeper.core.TileState;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.jpa.ScoreServiceJPA;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	Date date = new Date(0);
	long startTime;
	@Autowired
	private UserController userController;
	private Field field;
	double avgRating;

	@Autowired
	private RatingService ratingService;
	@Autowired
	private FavouriteService favouriteService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private CommentService commentService;
	private boolean marking;
	private String message;
	//
	// public double getAvgRating() {
	// avgRating = ratingService.getAverageRating("mines");
	// return avgRating;
	// }

	public String getMessage() {
		return message;

	}

	public boolean isMarking() {
		return marking;
	}

	@RequestMapping("/mines_mark")
	public String minesMark(Model model) {
		marking = !marking;

		fillModel(model);
		return "mines";
	}

	@RequestMapping("/addCommentMines")
	public String comment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {

		commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "mines", newComment, date));

		fillModel(model);

		return "mines";
	}

	@RequestMapping("/addRatingMines")
	public String rating(@RequestParam(value = "Rating", required = false) String rating, Model model) {
		ratingService
				.setRating(new Rating(userController.getLoggedPlayer().getLogin(), "mines", Integer.parseInt(rating)));

		fillModel(model);

		return "mines";
	}

	@RequestMapping("/addFavouriteMines")
	public String addFavourite(Model model) {
		if (userController.isLogged())
			favouriteService.addFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "mines"));

		fillModel(model);

		return "mines";
	}

	public void fillModel(Model model) {
		model.addAttribute("minesController", this);
		model.addAttribute("scores", scoreService.getTopScores("mines"));
		model.addAttribute("comments", commentService.getComments("mines"));
		model.addAttribute("rating", ratingService.getAverageRating("mines"));
		if (userController.isLogged()) {
			model.addAttribute("favourite",
					favouriteService.isFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "mines")));
		}
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {

		try {
			if (marking)
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			else
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));

			if (field.getState() == GameState.FAILED) {
				message = "You Lose !";
			} else if (field.getState() == GameState.SOLVED) {
				message = "You Won ! Your score is " + ((int) (System.currentTimeMillis() - startTime) / 1000) + "!";
				if (userController.isLogged())
					scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), "mines",
							((int) (System.currentTimeMillis() - startTime) / 1000)));
				else if (!userController.isLogged()) {
					scoreService.addScore(
							new Score("Guest", "mines", ((int) (System.currentTimeMillis() - startTime) / 1000)));
				}
			}
		} catch (NumberFormatException e) {

			createField();
		}

		fillModel(model);

		return "mines";
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='tableMines'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String image = null;

				switch (tile.getState()) {
				case CLOSED:
					image = "closed";
					break;
				case MARKED:
					image = "marked";
					break;
				case OPEN:
					if (tile instanceof Clue)
						image = "open" + ((Clue) tile).getValue();
					else
						image = "mine";
					break;
				}

				sb.append("<td>\n");
				if (tile.getState() != TileState.OPEN && field.getState() == GameState.PLAYING)
					sb.append(String.format("<a href='/mines?row=%d&column=%d'>\n", row, column));
				sb.append("<img src='/images/mines/" + image + ".png'>\n");
				if (tile.getState() != TileState.OPEN && field.getState() == GameState.PLAYING)
					sb.append("</a>\n");
				sb.append("</td>\n");

			}
			sb.append("<tr>\n");
		}

		sb.append("</table>\n");

		return sb.toString();
	}

	private void createField() {
		field = new Field(9, 9, 2);
		message = "";
		startTime = System.currentTimeMillis();
	}
}
