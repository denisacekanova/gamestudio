package gamestudio.server.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

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
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {

	private Date date = new Date(0);
	private Random rand = new Random();
	private int randomNumber;
	private int tries = 1;

	private double avgRating;
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
		avgRating = ratingService.getAverageRating("guessNumber");
		return avgRating;
	}

	@RequestMapping("/addCommentGuessNumber")
	public String comment(@RequestParam(value = "newComment", required = false) String newComment, Model model) {

		commentService
				.addComment(new Comment(userController.getLoggedPlayer().getLogin(), "guessNumber", newComment, date));

		fillModel(model);
		return "guessNumber";
	}

	@RequestMapping("/addRatingGuessNumber")
	public String addRating(@RequestParam(value = "value", required = false) String value, Model model) {
		ratingService.setRating(
				new Rating(userController.getLoggedPlayer().getLogin(), "guessNumber", Integer.parseInt(value)));

		fillModel(model);

		return "guessNumber";
	}

	@RequestMapping("/addFavouriteGuessNumber")
	public String addFavourite(Model model) {
		if (userController.isLogged())
			favouriteService.addFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "guessNumber"));

		fillModel(model);

		return "guessNumber";
	}

	@RequestMapping("/guessNumber")
	public String guessNumber(@RequestParam(value = "guessedNumber", required = false) String guessedNumber,
			Model model) {

		try {
			// long startTime = System.currentTimeMillis();
			if (tries < 5) {
				tries++;
				if (Integer.parseInt(guessedNumber) == randomNumber) {

					message = "You Won! The secret Number was " + randomNumber + ". Your score is "+ (100 - tries) + ". Let's play another game !";
					{

						if (userController.isLogged())
							scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), "guessNumber",
									(100 - tries)));
						else if (!userController.isLogged()) {
							scoreService.addScore(new Score("Guest", "guessNumber", (100 - tries)));
						}
					}
					randomNumber = rand.nextInt(100) + 1;
					tries = 1;
				} else if (Integer.parseInt(guessedNumber) < randomNumber) {
					message = "Your guess is too low!";
				} else if (Integer.parseInt(guessedNumber) > randomNumber) {
					message = "Your guess is too high!";

				}
			} else {
				message = "You Lose! The secret Number was " + randomNumber + ". Let's play another game !";
				randomNumber = rand.nextInt(100) + 1;
				tries = 1;
			}

			// long stopTime = System.currentTimeMillis();
			// long totalTime = (stopTime - startTime) / 1000;

		} catch (NumberFormatException e) {
			randomNumber = rand.nextInt(100) + 1;
			tries = 1;
		}

		fillModel(model);
		return "guessNumber";

	}

	private void fillModel(Model model) {
		model.addAttribute("guessNumberController", this);
		model.addAttribute("scores", scoreService.getTopScores("guessNumber"));
		model.addAttribute("comments", commentService.getComments("guessNumber"));
		model.addAttribute("rating", ratingService.getAverageRating("guessNumber"));
		if (userController.isLogged()) {
			model.addAttribute("favourite", favouriteService
					.isFavourite(new Favourite(userController.getLoggedPlayer().getLogin(), "guessNumber")));
		}
	}
}
