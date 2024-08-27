package com.tourist.Respository;

import java.util.List;




import com.tourist.Entity.Booking;

public interface BookingRespository {

	List<Booking> getList();
	void AddorUpdate(Booking booking);
	Booking getBookingById(int id);
	void deleteBookingById(int id);

	List<Booking> getListByUserId(int user_id);
	List<Booking> getListByTourId(int tour_id);
	Booking getBookingByTourAndUser(int tourId, int userId);
	List<Booking> getBookingbyStatus(Long userid, String text);
	Booking getBookingByUserIdAndTourid(Long userId, int tourId);
}
