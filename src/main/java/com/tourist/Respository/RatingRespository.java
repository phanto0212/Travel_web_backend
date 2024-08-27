package com.tourist.Respository;



import java.util.List;

import com.tourist.Entity.Rate;

public interface RatingRespository {
	List<Rate> getAllRatingByTourId(int tourId);
	Rate getRatingById(int id);
	Rate getRatingByUserId(Long userId);
	void addRating(Rate rate);
	void deletRating(int id);
	Rate getRatingByUseridAndTourId(Long userId, int tour_id);
}
