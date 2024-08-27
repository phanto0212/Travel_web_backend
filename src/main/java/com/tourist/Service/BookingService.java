package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.Booking;

public interface BookingService {

	List<Booking> getList();
	void AddorUpdate(Booking booking);
	Booking getBookingById(int id);
	void deleteBookingById(int id);

	List<Booking> getListByUserId(int user_id);
	List<Booking> getListByTourId(int tour_id);
	List<Booking> getBookingByStatus(Long userId, String text);
	Booking getBookingByUserIdAndTourid(Long userId, int tourId);
	
}
