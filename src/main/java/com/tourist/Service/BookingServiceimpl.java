package com.tourist.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Booking;
import com.tourist.Respository.BookingRespository;

@Service
public class BookingServiceimpl implements BookingService{
	@Autowired
	private BookingRespository bookingRespository;

	@Override
	public List<Booking> getList() {
		List<Booking> list = bookingRespository.getList();
		return list;
	}

	@Override
	public void AddorUpdate(Booking booking) {
		bookingRespository.AddorUpdate(booking);
		
	}

	@Override
	public Booking getBookingById(int id) {
		return bookingRespository.getBookingById(id);
	}

	@Override
	public void deleteBookingById(int id) {
		bookingRespository.deleteBookingById(id);
		
	}

	@Override
	public List<Booking> getListByUserId(int user_id) {
		
		return bookingRespository.getListByUserId(user_id);
	}

	@Override
	public List<Booking> getListByTourId(int tour_id) {
		
		return bookingRespository.getListByTourId(tour_id);
	}

	@Override
	public List<Booking> getBookingByStatus(Long userId, String text) {

		return bookingRespository.getBookingbyStatus(userId, text);
	}

	@Override
	public Booking getBookingByUserIdAndTourid(Long userId, int tourId) {
		return bookingRespository.getBookingByUserIdAndTourid(userId, tourId);
	}

	

}
