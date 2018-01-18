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
import gamestudio.game.puzzleImage.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleImageController {

	private Field field;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date(0);
	private int clickedTile;
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
		avgRating = ratingService.getAverageRating("puzzleImage");
		return avgRating;
	}

	@RequestMapping("/addCommentPuzzleImage")
	public String comment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {

		commentService
				.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "puzzleImage", newComment, date));

		fillModel(model);
		return "puzzleImage";
	}

	@RequestMapping("/addRatingPuzzleImage")
	public String addRating(@RequestParam(value = "value", required = false) String value, Model model) {
		ratingService.setRating(
				new Rating(userController.getLoggedPlayer().getLogin(), "puzzleImage", Integer.parseInt(value)));

		fillModel(model);

		return "puzzleImage";
	}

	@RequestMapping("/addFavouritePuzzleImage")
	public String addFavourite(Model model) {
		if (userController.isLogged())
			favouriteService.addFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "puzzleImage"));

		fillModel(model);

		return "puzzleImage";
	}

	@RequestMapping("/puzzleImage")
	public String puzzleImage(@RequestParam(value = "tile", required = false) String tile, Model model) {

		try {

			if (clickedTile == 0) {
				clickedTile = Integer.parseInt(tile);
			} else {

				field.moveTile(Integer.parseInt(tile), clickedTile);
				clickedTile = 0;

				if (field.isSolved()) {
					message = "You Won ! Your score is " + ((int) (System.currentTimeMillis() - startTime) / 1000)
							+ "!";
					if (userController.isLogged())
						scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), "puzzleImage",
								((int) (System.currentTimeMillis() - startTime) / 1000)));
					else if (!userController.isLogged()) {
						scoreService.addScore(new Score("Guest", "puzzleImage",
								((int) (System.currentTimeMillis() - startTime) / 1000)));
					}
				}
			}
		} catch (NumberFormatException e) {

			createField();
		}

		fillModel(model);

		return "puzzleImage";
	}

	public void fillModel(Model model) {
		model.addAttribute("puzzleImageController", this);
		model.addAttribute("scores", scoreService.getTopScores("puzzleImage"));
		model.addAttribute("comments", commentService.getComments("puzzleImage"));
		model.addAttribute("rating", ratingService.getAverageRating("puzzleImage"));
		if (userController.isLogged()) {
			model.addAttribute("favourite", favouriteService
					.isFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "puzzleImage")));
		}
	}

	public String render() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class='tablePuzzleImage'>\n");

		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);

				sb.append("<td>\n");

				if (!field.isSolved())
					sb.append(String.format("<a href='/puzzleImage?tile=%d'>\n", tile));
				sb.append("<img src='/images/puzzleImage/" + tile + ".jpeg'>\n");
				if (!field.isSolved())
					sb.append("</a>\n");
				sb.append("</td>\n");

			}
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");

		return sb.toString();
	}

	private void createField() {
		field = new Field(4, 4);
		message = "";
		clickedTile = 0;
		startTime = System.currentTimeMillis();

	}

}
