package gamestudio.service.impl.sorm;

import gamestudio.entity.Rating;
import gamestudio.service.RatingService;
import orm.SORM;

public class RatingServiceSORM implements RatingService {
	private SORM sorm = new SORM();
	
	@Override
	public void setRating(Rating rating) {
		sorm.insert(rating);
	}

	@Override
	public double getAverageRating(String game) {
		// TODO Auto-generated method stub
		return 0;
	}

}
