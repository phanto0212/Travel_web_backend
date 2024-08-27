package com.tourist.Respository;


import java.util.List;



import com.tourist.Entity.Tour;


public interface TourRespository {

	List<Tour> getList();
	void AddOrUpdate(Tour tour);
	Tour getTourById(int id);
	void deleteById(int id);
	void saveAll(List<Tour> tours);
	List<Tour> getPageTours(int page,int size);
	Long CountTour();
	List<Tour> findTours(Integer text, Integer price);
}
