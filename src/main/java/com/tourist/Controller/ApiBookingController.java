package com.tourist.Controller;

import java.math.BigDecimal;
import java.sql.Date;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Configs.JwtTokenUtil;
import com.tourist.Entity.Booking;
import com.tourist.Entity.Invoice;
import com.tourist.Entity.Tour;
import com.tourist.Entity.User;
import com.tourist.Service.BookingService;
import com.tourist.Service.InvoiceService;
import com.tourist.Service.TourService;
import com.tourist.Service.UserService;
import com.tourist.Until.BookingRequest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/booking")
public class ApiBookingController {
	@Autowired
	private BookingService bookingService;

	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TourService tourService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllBooking(){
		try {
			List<Booking > list = bookingService.getList();
			return ResponseEntity.ok(list);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error when you get all booking");
		}
	}
	@GetMapping("/get/booking/{id}")
	public ResponseEntity<?> getBookingById(@PathVariable("id") int id){
		try {
			Booking booking = bookingService.getBookingById(id);
			if(booking==null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("booking not found");
			}
			return ResponseEntity.ok(booking);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error!!!!!!!!!!!");
		}
	}
	@PutMapping("/update/booking/{id}")
	public ResponseEntity<?> updateBooking(@PathVariable("id") int id,@RequestBody Booking booking){
		try {
			Booking exitBooking = bookingService.getBookingById(id);
			if(booking == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("booking not found");
			}
			exitBooking.setBookingDate(booking.getBookingDate());
			exitBooking.setNumber_of_adults(booking.getNumber_of_adults());
			exitBooking.setNumber_of_children(booking.getNumber_of_children());
			exitBooking.setTotalPrice(booking.getTotalPrice());
			exitBooking.setTour(booking.getTour());
			exitBooking.setUser(booking.getUser());
			booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			bookingService.AddorUpdate(booking);
			return ResponseEntity.ok("Updated");
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error!!!!!!!!!!!");
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBooking(@PathVariable("id") int id){
		try {
			Booking booking = bookingService.getBookingById(id);
			if(booking ==  null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("booking not found");
			}
			bookingService.deleteBookingById(id);
			return ResponseEntity.ok("delete booking successful");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error!!!!!!!!!!!");
		}
		
	}
	 @GetMapping("/success")
	    public ResponseEntity<?> bookingSuccess(@RequestBody Map<String, String> params) {
	        try {
	            // Kiểm tra sự tồn tại của các tham số cần thiết và đảm bảo chúng không rỗng
	            if (!params.containsKey("id") || params.get("id").isEmpty()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or empty booking ID");
	            }

	            String idStr = params.get("id");
	            int id = Integer.parseUnsignedInt(idStr);
	            String numberOfAdults = params.get("number_of_adults");
	            String numberOfChildren = params.get("number_of_children");
	            String totalPrice = params.get("totalPrice");

	            // Lấy booking theo ID
	             Booking booking = bookingService.getBookingById(id);
	            if (booking == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found");
	            }

	            // Kiểm tra và cập nhật số lượng người lớn
	            if (numberOfAdults != null && !numberOfAdults.isEmpty()) {
	                booking.setNumber_of_adults(Integer.parseInt(numberOfAdults));
	            }

	            // Kiểm tra và cập nhật số lượng trẻ em
	            if (numberOfChildren != null && !numberOfChildren.isEmpty()) {
	                booking.setNumber_of_children(Integer.parseInt(numberOfChildren));
	            }

	            // Kiểm tra và cập nhật tổng giá
	            if (totalPrice != null && !totalPrice.isEmpty()) {
	                booking.setTotalPrice(new BigDecimal(totalPrice));
	            }

	            // Cập nhật booking
	            bookingService.AddorUpdate(booking);

	            // Tạo invoice mới
	            Invoice invoice = new Invoice();
	            invoice.setBookingId(booking.getBookingId());
	            invoice.setInvoiceDate(new Date(System.currentTimeMillis()));
	            invoice.setTotalAmount(booking.getTotalPrice());
	            invoice.setPdfPath("path/to/pdf");
	            invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	            invoice.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

	            // Lưu invoice
	            // invoiceService.AddorUpdate(invoice);

	            return ResponseEntity.ok(invoice);
	        } catch (NumberFormatException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format: " + e.getMessage());
	        } catch (NullPointerException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Null value encountered: " + e.getMessage());
	        }
	    }
	 
	 @GetMapping("/get/booking/user")
	 public ResponseEntity<?> getAllBookingByUser_id( HttpServletRequest request){
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
			 List<Booking> list = bookingService.getListByUserId(userId.intValue());
			 if(list.size() == 0) {
				 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("dont have booking with" + userId);
			 }
			 return ResponseEntity.ok(list);
		 }
		 catch(Exception e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request!!");
		 }
	 }
	@PostMapping("/add/booking")
	public ResponseEntity<?> addNewBooking(@RequestBody BookingRequest bookingRequest, HttpServletRequest request ){
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
	       Tour tour = tourService.getTourById(bookingRequest.getTour_id());
           Booking booking = new Booking();
           booking.setTour(tour);
           booking.setUser(user);
           booking.setStatus("Confirmed");
           booking.setBookingDate(bookingRequest.getDate_booking());
           booking.setNumber_of_adults(bookingRequest.getAdult_quanlity());
           booking.setNumber_of_children(bookingRequest.getChild_quanlity());
           booking.setTotalPrice(bookingRequest.getTotal_price());
           booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
           booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
           
           bookingService.AddorUpdate(booking);
           Invoice invoice = new Invoice();
           invoice.setBookingId(booking.getBookingId());
           invoice.setInvoiceDate(new Date(System.currentTimeMillis()));
           invoice.setTotalAmount(booking.getTotalPrice());
           invoice.setPdfPath("path/to/pdf");
           invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
           invoice.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
           invoiceService.AddorUpdate(invoice);
           return ResponseEntity.ok(booking);
           
		}
		catch(Exception e) {
			e.printStackTrace(); // In ra stack trace để biết lỗi chi tiết
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request!!");
		}
	}
	@GetMapping("/get/booking/status")
    public ResponseEntity<?> getAllTours(
            @RequestParam(value = "text", required = false) String  status, HttpServletRequest request) {
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
 		        int iduser = userId.intValue();
 		        Map<String, Object> response = new HashMap<>();
 		        switch(status) {
 		        case "pending":
 		        	response.put("bookings",bookingService.getBookingByStatus(userId, "Confirmed") );
 		        	break;
 		        case "paid":
 		        	response.put("bookings", bookingService.getBookingByStatus(userId, "COMPLETED"));
 		        	break;
 		        case "cancel":
		        	response.put("bookings", bookingService.getBookingByStatus(userId, "FAILED"));
		        	break;
 		        case "all":
		        	response.put("bookings", bookingService.getListByUserId(iduser));
		        	break;
 		        	
 		        }
 		        return ResponseEntity.ok(response);
            }
            catch(Exception e) {
            	e.printStackTrace();
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request!!");
            }
        
        
    }
	
	
	
}
