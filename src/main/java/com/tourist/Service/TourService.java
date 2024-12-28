package com.tourist.Service;

import java.util.List;
import java.util.Map;

import com.tourist.Entity.Tour;
import com.tourist.Entity.TourImage;

public interface TourService {
	List<Tour> getListByKey(String key);
	List<Tour> getList();
	void AddOrUpdate(Tour tour);
	Tour getTourById(int id);
	void deleteById(int id);
	void setAllStatusTour();
	void AddImageToTour(int tourId, String ImageUrl);
	List<Tour> getPageTours(int page,int size);
	Long CountTour();
	Map<String, Object> getTours(Integer text, Integer price);
	List<TourImage> getAllImagebyTourId(int tour_id);
}
