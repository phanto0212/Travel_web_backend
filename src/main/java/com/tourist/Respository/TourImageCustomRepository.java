package com.tourist.Respository;

import java.util.List;

import com.tourist.Entity.TourImage;

public interface TourImageCustomRepository {

	List<TourImage> getAllByTour_id(int tour_id);
}
