package com.tourist.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Tour;
import com.tourist.Entity.TourImage;
import com.tourist.Respository.TourImageCustomRepository;
import com.tourist.Respository.TourImageRespository;
import com.tourist.Respository.TourRespository;

@Service
public class TourServiceimpl implements TourService {
	 
    @Autowired
    private TourRespository tourRespository;
    
    @Autowired
    private TourImageRespository tourImageRespository;
    
    @Autowired
    private TourImageCustomRepository tourImageCustomRepository;
	@Override
	public List<Tour> getList() {
		
		
		List<Tour> list = tourRespository.getList();
		
		return list;
	}

	@Override
	public void AddOrUpdate(Tour tour) {
		tourRespository.AddOrUpdate(tour);
	
		
	}

	@Override
	public Tour getTourById(int id) {
		Tour tour = tourRespository.getTourById(id);
		return tour;
	}

	@Override
	public void deleteById(int id) {
		tourRespository.deleteById(id);
		
	}

	@Override
	public void setAllStatusTour() {
		List<Tour> tours = tourRespository.getList();
        LocalDate today = LocalDate.now();
        
        for (Tour tour : tours) {
            LocalDate startDate = tour.getStartDate().toLocalDate(); // Chuyển đổi Date thành LocalDate nếu cần
            LocalDate endDate = tour.getEndDate().toLocalDate(); // Chuyển đổi Date thành LocalDate nếu cần

            if (today.isBefore(startDate)) {
                tour.setStatus("Scheduled");
            } else if (today.isAfter(endDate)) {
                tour.setStatus("Ended");
            } else {
                tour.setStatus("Ongoing");
            }
        }

        tourRespository.saveAll(tours); // Lưu tất cả các tour đã được cập nhật
		
	}



	@Override
	public void AddImageToTour(int tourId, String ImageUrl) {
		 try {
	    	   Tour tour = tourRespository.getTourById(tourId);
		        TourImage image = new TourImage();
		        image.setImageUrl(ImageUrl);
		        image.setTour(tour);
		        
		        tourImageRespository.save(image);
	
	       }
	       catch(Exception e) {
	    	   e.printStackTrace();
	    	   throw e;
	       }
		
	}

	@Override
	public List<Tour> getPageTours(int page, int size) {
		// TODO Auto-generated method stub
		return tourRespository.getPageTours(page, size);
	}

	@Override
	public Long CountTour() {
		// TODO Auto-generated method stub
		return tourRespository.CountTour();
	}

	@Override
	public Map<String, Object> getTours(Integer text, Integer price) {
		 List<Tour> tours = tourRespository.findTours(text, price);

	        Map<String, Object> result = new HashMap<>();
	        result.put("tours", tours);
	        result.put("total", tours.size()); // Tổng số tour có thể bằng kích thước của danh sách

	        return result;
	}

	@Override
	public List<TourImage> getAllImagebyTourId(int tour_id) {
		// TODO Auto-generated method stub
		return tourImageCustomRepository.getAllByTour_id(tour_id);
	}

	@Override
	public List<Tour> getListByKey(String key) {
		// TODO Auto-generated method stub
		return tourRespository.getListByKey(key);
	}
}
