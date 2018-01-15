package gamestudio.consoleUI;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import gamestudio.entity.Comment;
import gamestudio.entity.Favourite;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;

public class ConsoleMenu {
	private ConsoleGameUI[] games;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private FavouriteService favouriteService;

	private Scanner scanner = new Scanner(System.in);

	public ConsoleMenu(ConsoleGameUI[] games) {
		this.games = games;
	}

	public void show() {
		// scoreService.addScore(new Score("denisa", "mines", 250));
		// Format formatter = new SimpleDateFormat("2014-12-20 01:25:56");
		// commentService.addComment(
		// new Comment("denisa", "mines", "super game", new java.sql.Timestamp(new
		// java.util.Date().getTime())));
		// scoreService.addScore(new Score("jan", "mines", 500));
		favouriteService.addFavourite(new Favourite("denisa", "mines"));
		System.out.println(favouriteService.getFavourite("denisa"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("List of games :");
		int index = 1;

		for (ConsoleGameUI game : games) {
			double rating = ratingService.getAverageRating(game.getName());
			System.out.printf("%d. %s (%.2f/10) \n", index, game.getName(), rating);
			index++;
		}
		System.out.println("------------------------------------------------------------------------");
		do {
			System.out.println("select a game : ");
			String line = scanner.nextLine().trim();
			for (ConsoleGameUI game : games) {
				if (game.getName().toLowerCase().trim().equals(line)) {
					game.run();
				} else if ("x".equals(line)) {
					System.exit(0);
				}

				try {
					index = Integer.parseInt(line);
				} catch (NumberFormatException e) {
					index = -1;
				}
			}
		} while (index < 1 || index > games.length);
		ConsoleGameUI game = games[index - 1];
		game.run();
	}
}