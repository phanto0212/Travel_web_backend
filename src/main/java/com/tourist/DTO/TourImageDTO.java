package com.tourist.DTO;

import org.springframework.web.multipart.MultipartFile;

public class TourImageDTO {

	private int tourId;
	private String urlImage;
	public TourImageDTO(int tourId, String urlImage) {
		super();
		this.tourId = tourId;
		this.urlImage = urlImage;
	}
	public TourImageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	
	


	
}
