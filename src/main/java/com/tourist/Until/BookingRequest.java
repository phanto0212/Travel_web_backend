package com.tourist.Until;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class BookingRequest {
  private Integer tour_id;
  private int adult_quanlity;
  private int child_quanlity;
  private BigDecimal total_price;
  private Date date_booking;
public BookingRequest(Integer tour_id, int adult_quanlity, int child_quanlity, BigDecimal total_price,
		Date date_booking) {
	super();
	this.tour_id = tour_id;
	this.adult_quanlity = adult_quanlity;
	this.child_quanlity = child_quanlity;
	this.total_price = total_price;
	this.date_booking = date_booking;
}
public BookingRequest() {
	super();
	// TODO Auto-generated constructor stub
}
public Integer getTour_id() {
	return tour_id;
}
public void setTour_id(Integer tour_id) {
	this.tour_id = tour_id;
}
public int getAdult_quanlity() {
	return adult_quanlity;
}
public void setAdult_quanlity(int adult_quanlity) {
	this.adult_quanlity = adult_quanlity;
}
public int getChild_quanlity() {
	return child_quanlity;
}
public void setChild_quanlity(int child_quanlity) {
	this.child_quanlity = child_quanlity;
}
public BigDecimal getTotal_price() {
	return total_price;
}
public void setTotal_price(BigDecimal total_price) {
	this.total_price = total_price;
}
public Date getDate_booking() {
	return date_booking;
}
public void setDate_booking(Date date_booking) {
	this.date_booking = date_booking;
}



  
}
