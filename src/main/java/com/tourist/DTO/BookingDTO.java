package com.tourist.DTO;

import com.tourist.Entity.Booking;
import com.tourist.Entity.Tour;

public class BookingDTO {

	private Tour tour;
	private Booking booking;
	public BookingDTO(Tour tour, Booking booking) {
		super();
		this.tour = tour;
		this.booking = booking;
	}
	public BookingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
}
