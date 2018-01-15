package gamestudio.server.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.postgresql.util.LruCache.CreateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.entity.Player;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.game.puzzles.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {
	private Field field;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date(0);

	long startTime;
	double avgRating;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private FavouriteService favouriteService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserController userController;
	private String message;

	public String getMessage() {
		return message;

	}

	public double getAvgRating() {
		avgRating = ratingService.getAverageRating("puzzle");
		return avgRating;
	}

	@RequestMapping("/comment")
	public String comment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {

		commentService.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "puzzle", newComment, date));

		fillModel(model);
		return "puzzle";
	}

	@RequestMapping("/addRating")
	public String addRating(@RequestParam(value = "value", required = false) String value, Model model) {
		ratingService
				.setRating(new Rating(userController.getLoggedPlayer().getLogin(), "puzzle", Integer.parseInt(value)));

		fillModel(model);

		return "puzzle";
	}

	@RequestMapping("/addFavouritePuzzle")
	public String addFavourite(Model model) {
		if (userController.isLogged())
			favouriteService.addFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "puzzle"));

		fillModel(model);

		return "puzzle";
	}

	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(value = "tile", required = false) String tile, Model model) {

		try {

			field.moveTile(Integer.parseInt(tile));

			if (field.isSolved()) {
				message = "Vyhral si";
				if (userController.isLogged())
					scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), "puzzle",
							((int) (System.currentTimeMillis() - startTime) / 1000)));
				else if (!userController.isLogged()) {
					scoreService.addScore(
							new Score("Guest", "puzzle", ((int) (System.currentTimeMillis() - startTime) / 1000)));
				}
			}

		} catch (NumberFormatException e) {

			createField();

		}

		fillModel(model);

		return "puzzle";
	}

	public void fillModel(Model model) {
		model.addAttribute("puzzleController", this);
		model.addAttribute("scores", scoreService.getTopScores("puzzle"));
		model.addAttribute("comments", commentService.getComments("puzzle"));
		model.addAttribute("rating", ratingService.getAverageRating("puzzle"));
		if (userController.isLogged()) {
			model.addAttribute("favourite", favouriteService.isFavourite( new Favourite(userController.getLoggedPlayer().getLogin(), "puzzle")));
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table id='TablePuzzle'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr id='puzzleTr'>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				sb.append("<td>\n");

				if (!field.isSolved())
					sb.append(String.format("<a href='/puzzle?tile=%d'>\n", tile));
				{
					if (tile != Field.EMPTY_TILE)
						sb.append(tile);
				}
				sb.append("</a>\n");
				sb.append("</td>\n");

			}
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");

		return sb.toString();
	}

	private void createField() {
		field = new Field(3, 3);
		message = "";
		startTime = System.currentTimeMillis();
	}
}
