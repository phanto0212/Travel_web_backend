package com.tourist.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Configs.JwtTokenUtil;
import com.tourist.Entity.Booking;
import com.tourist.Entity.Rate;
import com.tourist.Entity.Tour;
import com.tourist.Entity.User;
import com.tourist.Service.BookingService;
import com.tourist.Service.RatingService;
import com.tourist.Service.TourService;
import com.tourist.Service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/rating")
public class ApiRatingController {

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private TourService tourService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired 
	private JwtTokenUtil jwtTokenUtil;
	
	@GetMapping("/tour/{tour_id}")
	public ResponseEntity<?> getRatingByTourid(@PathVariable("tour_id") int tour_id){
	    try {
	        Tour tour = tourService.getTourById(tour_id);
	        if(tour == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found with id " + tour_id);
	        }
	        List<Booking> listBooking = bookingService.getListByTourId(tour_id);
	        List<Rate> list = ratingService.getAllRatingByTourId(tour_id);
	        Map<String, Object> response = new HashMap<>();
	        if(list.isEmpty()) {
	            response.put("rate_size", 0);
	            response.put("rate_list", list);
	            response.put("rate_score", 0);
	            response.put("count_booked", listBooking.size());
	            return ResponseEntity.ok(response);
	        }

	        int sum = list.stream().mapToInt(Rate::getRating).sum();
	        double average = (double) sum / list.size();

	        BigDecimal roundedAverage = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
	        
	        response.put("rate_score", roundedAverage.doubleValue());
	        response.put("rate_size", list.size());
	        response.put("rate_list", list);
	        response.put("count_booked", listBooking.size());
	        return ResponseEntity.ok(response);
	        
	    } catch(Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Get rating tour error!");
	    }
	}

	
	@PostMapping("/add/{tour_id}")
	public ResponseEntity<?> addRateWithTour_id(@PathVariable("tour_id") int tour_id, 
	                                            @RequestBody Map<String, String> params,
	                                            HttpServletRequest request) {
	    try {
	        // Kiểm tra tour_id
	        Tour tour = tourService.getTourById(tour_id);
	        if (tour == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found with id " + tour_id);
	        }

	        // Kiểm tra token JWT
	        String jwt = request.getHeader("Authorization");
	        if (jwt == null || !jwt.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	        }

	        jwt = jwt.substring(7);
	        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
	        java.util.Date expiration = claims.getExpiration();
	        if (expiration.before(new java.util.Date())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
	        }

	        String username = claims.getSubject();
	        if (username == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        // Kiểm tra User
	        User user = userService.getUserByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	        }

	        Long userId = user.getId();
	      
	        // Lấy comment và rate từ params
	        String comment = params.get("comment");
	        String rateStr = params.get("rate");

	        // Kiểm tra null và giá trị hợp lệ cho rate
	        if (comment == null || rateStr == null || !rateStr.matches("\\d+")) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data");
	        }

	        // Thêm rating mới
	        Rate rating = new Rate();
	        rating.setUser(user);
	        rating.setTourId(tour_id);
	        rating.setRating(Integer.parseInt(rateStr));
	        rating.setComment(comment);
	        rating.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	        rating.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	        
	        ratingService.addRating(rating);
	        return ResponseEntity.ok(rating);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}

	@DeleteMapping("/rating/{rate_id}")
	public ResponseEntity<?> deleteRating(@PathVariable("rate_id") int rateId, HttpServletRequest request) {
	    try {
	        String jwt = request.getHeader("Authorization");
	        if (jwt == null || !jwt.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	        }
	        jwt = jwt.substring(7);

	        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
	        if (claims.getExpiration().before(new java.util.Date())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
	        }

	        String username = claims.getSubject();
	        if (username == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        User user = userService.getUserByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	        }

	        Rate rating = ratingService.getRatingById(rateId);
	        if (rating == null || !rating.getUser().getId().equals(user.getId())) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found or unauthorized");
	        }

	        ratingService.deletRating(rateId);
	        return ResponseEntity.ok("Rating deleted successfully");

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting rating");
	    }
	}
	@GetMapping("/test/rating/{tour_id}")
	public ResponseEntity<?> testRating(@PathVariable("tour_id") int tour_id, HttpServletRequest request){
		try {
			String jwt = request.getHeader("Authorization");
	        if (jwt == null || !jwt.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	        }
	        jwt = jwt.substring(7);

	        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
	        if (claims.getExpiration().before(new java.util.Date())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
	        }

	        String username = claims.getSubject();
	        if (username == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        User user = userService.getUserByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	        }
	        
	        Rate rate = ratingService.getRatingByUseridAndTourId(user.getId(), tour_id);
	        Map<String, Object> response = new HashMap<>();
	        if(rate!=null) {
	        	response.put("test", true);
	        }
	        else {
	        	response.put("test", false);
	        }
	        return ResponseEntity.ok(response);
	        
	        
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when test rating");
		}
	}
	@GetMapping("/test/booked/{tour_id}")
	public ResponseEntity<?> testBooked(@PathVariable("tour_id") int tour_id, HttpServletRequest request){
		try {
			String jwt = request.getHeader("Authorization");
	        if (jwt == null || !jwt.startsWith("Bearer ")) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
	        }
	        jwt = jwt.substring(7);

	        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
	        if (claims.getExpiration().before(new java.util.Date())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
	        }

	        String username = claims.getSubject();
	        if (username == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        User user = userService.getUserByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	        }
	        
	        Booking booking = bookingService.getBookingByUserIdAndTourid(user.getId(), tour_id);
	        Map<String, Object> response = new HashMap<>();
	        if(booking!=null) {
	        	response.put("test", true);
	        }
	        else {
	        	response.put("test", false);
	        }
	        return ResponseEntity.ok(response);
	        
	        
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when test rating");
		}
	}
	@GetMapping("/get/user/{rate_id}")
	public ResponseEntity<?> getUserByRateId(@PathVariable("rate_id") int rate_id){
		try {
			Rate rate = ratingService.getRatingById(rate_id);
			if(rate==null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Rate not found"); 
			}
			Map<String, Object> reponse = new HashMap<>();
			User user = rate.getUser();
			user.setPassword("");
			reponse.put("user", user);
			
			return ResponseEntity.ok(reponse);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when get user");
		}
	}
}
