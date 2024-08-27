package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.Rate;

public interface RatingService {

	List<Rate> getAllRatingByTourId(int tourId);
	Rate getRatingByUserId(Long userId);
	Rate getRatingById(int id);
	void addRating(Rate rate);
	void deletRating(int id);
	Rate getRatingByUseridAndTourId(Long userId, int tour_id);
	
}
