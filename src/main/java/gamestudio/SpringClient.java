package gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import gamestudio.consoleUI.ConsoleGameUI;
import gamestudio.consoleUI.ConsoleMenu;
import gamestudio.game.GuessNumber.core.GuessNumber;
import gamestudio.game.minesweeper.ConsoleUI.ConsoleUI;
import gamestudio.game.minesweeper.core.Field;
import gamestudio.service.CommentService;
import gamestudio.service.FavouriteService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.jpa.CommentServiceJPA;
import gamestudio.service.impl.jpa.FavouriteServiceJPA;
import gamestudio.service.impl.jpa.RatingServiceJPA;
import gamestudio.service.impl.jpa.ScoreServiceJPA;
import gamestudio.service.impl.rest.CommentServiceRestClient;
import gamestudio.service.impl.rest.FavouriteServiceRestClient;
import gamestudio.service.impl.rest.RatingServiceRestClient;
import gamestudio.service.impl.rest.ScoreServiceRestClient;

@Configuration
@SpringBootApplication

@ComponentScan(basePackages = { "gamestudio" },
excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "gamestudio.server.*"))

public class SpringClient {

	public static void main(String[] args) throws Exception {
				
		//SpringApplication.run(SpringClient.class, args);
		new SpringApplicationBuilder(SpringClient.class).web(false).run(args);
	}

	// miny //
	@Bean
	public CommandLineRunner runner(ConsoleMenu menu) {
		return args -> menu.show();
	}

	@Bean
	public ConsoleMenu menu(ConsoleGameUI[] games) {
		return new ConsoleMenu(games);

	}

	@Bean
	public ConsoleUI consoleUImines(Field field) {
		return new ConsoleUI(field);
	}

	@Bean
	public Field fieldMines() {
		return new Field(9, 9, 10);
	}

	// kamene //

	@Bean
	public ConsoleGameUI consolePuzzles(gamestudio.game.puzzles.Field field) {
		return new gamestudio.game.puzzles.Console(field);
	}

	@Bean
	public gamestudio.game.puzzles.Field fieldPuzzles() {
		return new gamestudio.game.puzzles.Field(4, 4);
	}

	// hadaj cislo //

	@Bean
	public ConsoleGameUI consoleNumber() {
		return new GuessNumber();
	}

	@Bean
	public RatingService ratingService() {
		return new RatingServiceRestClient();
	}

	@Bean
	public CommentService commentService() {
		return new CommentServiceRestClient();
	}

	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceRestClient();
	}

	@Bean
	public FavouriteService favouriteService() {
		return new FavouriteServiceRestClient();
	}
}