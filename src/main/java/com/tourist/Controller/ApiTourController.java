package com.tourist.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Configs.JwtTokenUtil;
import com.tourist.DTO.BookingDTO;
import com.tourist.Entity.Booking;
import com.tourist.Entity.Rate;
import com.tourist.Entity.Tour;
import com.tourist.Entity.TourImage;
import com.tourist.Entity.User;
import com.tourist.Service.BookingService;
import com.tourist.Service.RatingService;
import com.tourist.Service.TourService;
import com.tourist.Service.UserService;
import com.tourist.Until.PageTourRequest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tours")
public class ApiTourController {

	@Autowired
	private TourService tourService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllTour(@RequestBody PageTourRequest pageTourRequest){
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGFudG8xIiwicm9sZXMiOlsiVVNFUiJdLCJleHAiOjE3MjI2NzEyNzF9.5wixL5fvsiCYDOvcF7Jde1k-JYcJB6sL7sJ2CGp-COg";
//
//		// Phương thức để lấy Claims từ token
//		Claims claims = jwtTokenUtil.getClaimsFromToken(token);
//
//		// Lấy các giá trị từ Claims
//		String subject = claims.getSubject(); // sub
//		List<String> roles = claims.get("roles", List.class); // roles
//		Date expiration = claims.getExpiration(); // exp
//
//		// In ra các giá trị
//		System.out.println("Subject: " + subject);
//		System.out.println("Roles: " + roles);
//		System.out.println("Expiration: " + expiration);
		List<Tour> tours = tourService.getPageTours(pageTourRequest.getPage(), pageTourRequest.getSize());
		long totalProducts = tourService.CountTour();
		Map<String, Object> response = new HashMap<>();
        response.put("tours", tours);
        response.put("totalItems", totalProducts);
        response.put("totalPages", (int) Math.ceil((double) totalProducts / pageTourRequest.getSize()));
        response.put("currentPage", pageTourRequest.getPage());
        return ResponseEntity.ok(response);
	
}
	@GetMapping("/get/all")
    public ResponseEntity<Map<String, Object>> getAllTours(
            @RequestParam(value = "text", required = false) Integer text,
            @RequestParam(value = "price", required = false) Integer price) {

        Map<String, Object> result = tourService.getTours(text, price);
        return ResponseEntity.ok(result);
    }
	@GetMapping("/getTourByid/{id}")
	public ResponseEntity<?> getTourById(@PathVariable("id") int id){
		
		Tour existingTour = tourService.getTourById(id);
		if(existingTour == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found with id: " + id);
		}
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("tour",existingTour );
		return ResponseEntity.ok(responseBody);
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateTour(@PathVariable("id") int id, @RequestBody Tour tour){
		try {
			Tour existingTour = tourService.getTourById(id);
            if (existingTour == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found with id: " + id);
            }
            existingTour.setTitle(tour.getTitle());
            existingTour.setDescription(tour.getDescription());
            existingTour.setAdult_price(tour.getAdult_price());
            existingTour.setChild_price(tour.getChild_price());
            existingTour.setDuration(tour.getDuration());
            existingTour.setStartDate(tour.getStartDate());
            existingTour.setEndDate(tour.getEndDate());
            existingTour.setImage(tour.getImage());
            tourService.AddOrUpdate(existingTour);
           return ResponseEntity.ok("Update Successfully");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the tour");
		}
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTour(@PathVariable("id")int id){
		try {
			Tour existingTour = tourService.getTourById(id);
            if (existingTour == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found with id: " + id);
            }
            tourService.deleteById(id);
            return  ResponseEntity.ok("Delete Successfully");
            
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while delete the tour");
		}
		
	}
	
	@GetMapping("/add/tour/toBooking/{id}")
	public ResponseEntity<?> addTourToBooking(@PathVariable("id") int id, HttpServletRequest request){
		try {
			String jwt = request.getHeader("Authorization");
			 
		        if (jwt == null ) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token" );
		        }
		        if (jwt.startsWith("Bearer ")) {
		            jwt = jwt.substring(7);
		        }
		        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
		        java.util.Date expiration = claims.getExpiration();
		        if(expiration.before(new java.util.Date())) {
		        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token exprired");
		        }
		        String username = claims.getSubject(); // sub
	       
		        if (username == null) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
		        }
	
		        User user = userService.getUserByUsername(username);
		        if (user == null) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
		        }
	        Long userId = user.getId();
	        List<Booking> listBooking = bookingService.getList();
	        boolean bookingHaved = false;
	        for(Booking booking : listBooking) {
	        	if(booking.getUser().getId() == userId && booking.getTour().getTourId() == id) {
	        		bookingHaved = true;
	        	}
	        }
	        if (bookingHaved) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("booked");
	        }
	        Tour tour = tourService.getTourById(id);        
	        Date today = Date.valueOf(LocalDate.now());

	        Booking booking = new Booking();
	        booking.setUser(user);
	        booking.setTour(tour);
	        booking.setBookingDate(today);
	        booking.setStatus("Confirmed");  // Ví dụ trạng thái ban đầu là "Pending"

	        booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	        booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	        booking.setTotalPrice(BigDecimal.ZERO);
	        bookingService.AddorUpdate(booking);
	        BookingDTO bookingDTO = new BookingDTO(tour, booking);
	        return ResponseEntity.ok(bookingDTO);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when add booking");
		}
		  
	}
	@GetMapping("/getRateAndBooking/{tour_id}")
	public ResponseEntity<?> getRateAndBooking(@PathVariable("tour_id") int tour_id) {
	    try {
	        List<Rate> list = ratingService.getAllRatingByTourId(tour_id);

	        // Kiểm tra nếu không có đánh giá nào
	        double average = 0;
	        if (!list.isEmpty()) {
	            int sum = 0;
	            for (Rate rating : list) {
	                sum += rating.getRating();
	            }
	            average = (double) sum / list.size();
	            // Làm tròn giá trị trung bình
	            BigDecimal roundedAverage = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
	            average = roundedAverage.doubleValue();
	        }

	        List<Booking> listBooking = bookingService.getListByTourId(tour_id);
	        Map<String, Object> response = new HashMap<>();
	        response.put("rate_score", average);
	        response.put("total_count_book", listBooking.size());

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when fetching rate and booking data");
	    }
	}
    
	@GetMapping("/get/all/image/{tour_id}")
	public ResponseEntity<?> getAllImage(@PathVariable("tour_id") int tour_id){
		try {
			Tour tour = tourService.getTourById(tour_id);
			if(tour==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found tour with id" + tour_id);
			}
			List<TourImage> listImage = tourService.getAllImagebyTourId(tour_id);
			Map<String, Object> reponse = new HashMap<>();
			reponse.put("list_image", listImage);
			return ResponseEntity.ok(reponse);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when fetching rate and booking data");
		}
	}
	
}
