package com.tourist.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "bookings")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id", nullable = false)
    
    private Tour tour;

    @Temporal(TemporalType.DATE)
    @Column(name = "booking_date", nullable = false)
    private Date bookingDate;

    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "number_of_adults", nullable = false)
    private int number_of_adults;
    
    @Column(name = "number_of_children", nullable = false)
    private int number_of_children;

    @Column(name = "total_price", nullable = false, precision = 20, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    @Column(name = "start_date", nullable = true)
    private Timestamp start_date;

	public Booking(Integer bookingId, User user, Tour tour, Date bookingDate, String status, int number_of_adults,
			int number_of_children, BigDecimal totalPrice, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.bookingId = bookingId;
		this.user = user;
		this.tour = tour;
		this.bookingDate = bookingDate;
		this.status = status;
		this.number_of_adults = number_of_adults;
		this.number_of_children = number_of_children;
		this.totalPrice = totalPrice;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNumber_of_adults() {
		return number_of_adults;
	}

	public void setNumber_of_adults(int number_of_adults) {
		this.number_of_adults = number_of_adults;
	}

	public int getNumber_of_children() {
		return number_of_children;
	}

	public void setNumber_of_children(int number_of_children) {
		this.number_of_children = number_of_children;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Timestamp getStart_date() {
		return start_date;
	}

	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}

   
}
