package com.tourist.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tours")
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Integer tourId;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "adult_price", precision = 20, scale = 2, nullable = false)
    private BigDecimal adult_price;
    
    @Column(name = "child_price", precision = 20, scale = 2, nullable = false)
    private BigDecimal child_price;
    
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "departure_location")
    private String departure_location;
    
    @Column(name = "destination_location")
    private String destination_location;
    
    public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	@Column(name = "kind")
    private String kind;

	public Tour(Integer tourId, String title, String description, BigDecimal adult_price, BigDecimal child_price,
			Integer duration, Date startDate, Date endDate, String image, Date createdAt, Date updatedAt, String status,
			String departure_location, String destination_location) {
		super();
		this.tourId = tourId;
		this.title = title;
		this.description = description;
		this.adult_price = adult_price;
		this.child_price = child_price;
		this.duration = duration;
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.status = status;
		this.departure_location = departure_location;
		this.destination_location = destination_location;
	}

	public Tour() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAdult_price() {
		return adult_price;
	}

	public void setAdult_price(BigDecimal adult_price) {
		this.adult_price = adult_price;
	}

	public BigDecimal getChild_price() {
		return child_price;
	}

	public void setChild_price(BigDecimal child_price) {
		this.child_price = child_price;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeparture_location() {
		return departure_location;
	}

	public void setDeparture_location(String departure_location) {
		this.departure_location = departure_location;
	}

	public String getDestination_location() {
		return destination_location;
	}

	public void setDestination_location(String destination_location) {
		this.destination_location = destination_location;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	
    


	
	

	
}
