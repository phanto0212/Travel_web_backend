package com.tourist.Controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tourist.DTO.TourImageDTO;
import com.tourist.Entity.Booking;
import com.tourist.Entity.Invoice;
import com.tourist.Entity.Tour;
import com.tourist.Entity.User;
import com.tourist.Service.BookingService;
import com.tourist.Service.InvoiceService;
import com.tourist.Service.TourService;
import com.tourist.Service.UserService;
import com.tourist.Until.FileUtil;

import io.jsonwebtoken.io.IOException;



@Controller
public class TourController {
	@Autowired
	private TourService tourService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("/tours")
	public String Tour(Model model) {
		tourService.setAllStatusTour();
		List<Tour> tours = tourService.getList();
		model.addAttribute("tours", tours);
		return("tour");
	}
	@PostMapping("/addOrUpdateTour")
	public String addOrUpdateTour(@ModelAttribute Tour tour) {
		Date today = Date.valueOf(LocalDate.now());
		List<Tour> list = tourService.getList();
		
		for(int i=0;i<list.size();i++) {
			if(tour.getTourId() != null && tour.getTourId().equals(list.get(i).getTourId())) {
				tour.setUpdatedAt(today);
				
				tourService.AddOrUpdate(tour);
				
				return ("redirect:/tours");
				
			}
			
		}
		tour.setStatus("Scheduled");
		tour.setCreatedAt(today);
		tour.setUpdatedAt(today);
		tourService.AddOrUpdate(tour);
		return ("redirect:/tours");
	}
	@PostMapping("/editTour/{id}")
	public String editTour(@PathVariable("id") int id,Model model) {
		Tour tour = tourService.getTourById(id);
		model.addAttribute("tour", tour);
		return ("editTour");
	}
	@PostMapping("/deleteTour/{id}")
	public String deleteTour(@PathVariable("id") int id) {
		tourService.deleteById(id);
		return ("redirect:/tours");
	}
	@PostMapping("/addImageToTour/{id}")
	public String addImageToTour(@PathVariable ("id") int id, Model model) {
		Tour tour = tourService.getTourById(id);
		model.addAttribute("tour", tour);
		return ("addImageTour");
		
		
	}
	@PostMapping("/addImage")
	public String addImage(@ModelAttribute TourImageDTO tourImageDTO) {
		
		String ImageUrl = tourImageDTO.getUrlImage();
		tourService.AddImageToTour(tourImageDTO.getTourId(), ImageUrl);
	      
	               
		return ("redirect:/tours");
			
	}
	@PostMapping("/addTourToBooking/{id}")
	public String addTourToBooking(@PathVariable("id") int id, Model model) {
		try {
	        // Lấy thông tin người dùng đang đăng nhập
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            String username = userDetails.getUsername();  // Đúng là lấy username chứ không phải password
	            
	            // Lấy thông tin người dùng từ dịch vụ
	            User user = userService.getUserByUsername(username);
	            Long userId = user.getId();
	            List<Booking> listBooking = bookingService.getList();
	            boolean bookingHaved = false;
	            for(Booking booking : listBooking) {
	            	if(booking.getUser().getId() == userId && booking.getTour().getTourId() == id) {
	            		bookingHaved = true;
	            	}
	            }
	            if (bookingHaved) {
	            	return ("redirect:/bookings");
	            }
	            // Lấy thông tin tour
	            Tour tour = tourService.getTourById(id);
	            
	            // Thêm thông tin tour vào model
	            model.addAttribute("tour", tour);
	            Date today = Date.valueOf(LocalDate.now());
	            // Tạo booking mới và thêm vào model
	            Booking booking = new Booking();
	            booking.setUser(user);
	            booking.setTour(tour);
	            booking.setBookingDate(today);
	            booking.setStatus("Confirmed");  // Ví dụ trạng thái ban đầu là "Pending"

	            booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	            booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	            booking.setTotalPrice(BigDecimal.ZERO);
	            bookingService.AddorUpdate(booking);
	            model.addAttribute("booking", booking);
	            
	            return "newBooking";  // Trả về trang newBooking.html
	        }
	        return "redirect:/login";  // Nếu không có người dùng đang đăng nhập, chuyển hướng đến trang đăng nhập
	    
		} catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
		
	}

}
