package com.tourist.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Rate;
import com.tourist.Respository.RatingRespository;
@Service
public class RatingServiceimpl implements RatingService {

	@Autowired
	private RatingRespository ratingRespository;
	
	@Override
	public List<Rate> getAllRatingByTourId(int tourId) {
		return ratingRespository.getAllRatingByTourId(tourId);
	}

	@Override
	public Rate getRatingById(int id) {
		// TODO Auto-generated method stub
		return ratingRespository.getRatingById(id);
	}

	@Override
	public void addRating(Rate rate) {
		ratingRespository.addRating(rate);
		
	}

	@Override
	public void deletRating(int id) {
		ratingRespository.deletRating(id);
		
	}

	@Override
	public Rate getRatingByUserId(Long userId) {
		return ratingRespository.getRatingByUserId(userId);
	}

	@Override
	public Rate getRatingByUseridAndTourId(Long userId, int tour_id) {
		// TODO Auto-generated method stub
		return ratingRespository.getRatingByUseridAndTourId(userId, tour_id);
	}

}
