package gamestudio;

import java.util.Date;

import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import gamestudio.service.impl.jdbc.CommentServiceJDBC;
import gamestudio.service.impl.jdbc.RatingServiceJDBC;

public class Test {

	public static void main(String[] args) throws Exception {

//		Rating rating = new Rating();
//		rating.setUsername("denisa");
//		rating.setGame("mines");
//		rating.setValue(6);
//
//		RatingService ratingService = new RatingServiceJDBC();
//		ratingService.setRating(rating);
//		System.out.println(ratingService.getAverageRating("mines"));
//		
		
		 Comment comment = new Comment();
		 comment.setUsername("denisa");
		 comment.setGame("mines");
		 comment.setContent("uzasna hra");
		// comment.setCreatedOn(new Date());
		Date date = new Date();
	    java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
	    comment.setCreatedOn(sqlDate);
		
		 CommentService commentService = new CommentServiceJDBC();
		 commentService.addComment(comment);
		 System.out.println(commentService.getComments("mines"));

		
		// Score score = new Score();
		// score.setUsername("denisa");
		// score.setGame("mines");
		// score.setValue(100);
		//
		// ScoreService scoreService = new ScoreServiceJDBC();
		// scoreService.addScore(score);
		//
		// System.out.println(scoreService.getTopScores("mines"));
		// }

	}
}
