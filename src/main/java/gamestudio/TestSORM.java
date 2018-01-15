package gamestudio;

import java.sql.Timestamp;
import java.util.Date;

import gamestudio.entity.Comment;
import gamestudio.entity.Rating;
import gamestudio.entity.Score;
import gamestudio.service.CommentService;
import gamestudio.service.RatingService;
import gamestudio.service.ScoreService;
import gamestudio.service.impl.jdbc.CommentServiceJDBC;
import gamestudio.service.impl.jdbc.RatingServiceJDBC;
import gamestudio.service.impl.jdbc.ScoreServiceJDBC;
import gamestudio.service.impl.sorm.CommentServiceSORM;
import gamestudio.service.impl.sorm.ScoreServiceSORM;

public class TestSORM {

	public static void main(String[] args) throws Exception {
//		 Score score = new Score();
//		 score.setUsername("jaro");
//		 score.setGame("mines");
//		 score.setValue(100);
//		
//		 ScoreService scoreService = new ScoreServiceSORM();
//		 scoreService.addScore(score);
//		
//		 System.out.println(scoreService.getTopScores("mines"));

		 Comment comment = new Comment();
		 comment.setUsername("jan");
		 comment.setGame("mines");
		 comment.setContent("Test dddddd");
		 Date date = new Date();
		    java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
		    comment.setCreatedOn(sqlDate);
			
			 CommentService commentService = new CommentServiceSORM();
			 commentService.addComment(comment);
			// System.out.println(commentService.getComments("mines"));
		
//		 System.out.println(commentService.getComments("mines"));

//		Rating rating = new Rating();
//		rating.setUsername("fero");
//		rating.setGame("mines");
//		rating.setValue(2);
//		RatingService ratingService = new RatingServiceJDBC();
//		ratingService.setRating(rating);
//
//		 System.out.println(ratingService.getAverageRating("mines"));
	}
}
