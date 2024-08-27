package com.tourist.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tourist.Entity.Booking;
import com.tourist.Entity.Invoice;
import com.tourist.Service.BookingService;
import com.tourist.Service.InvoiceService;



@Controller
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	
	@GetMapping("/bookings")
	public String Bookings(Model model) {
		List<Booking> bookings = bookingService.getList();
	    model.addAttribute("bookings", bookings);
	    return("bookings");
	}
	@PostMapping("/addOrUpdateBooking")
	public String addOrUpdateBooking(@ModelAttribute Booking booking) {
		List<Booking> list = bookingService.getList();	
		for(int i=0;i<list.size();i++) {
			if (booking.getBookingId()!=null && list.get(i).getBookingId().equals(booking.getBookingId()) ) {			
				booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
				
				
				bookingService.AddorUpdate(booking);
			}

		}
		booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		bookingService.AddorUpdate(booking);
		
		return("redirect:/bookings");
		
	}
	
	@PostMapping("/deleteBooking/{id}")
	public String deleteBooking(@PathVariable("id") int id) {
		bookingService.deleteBookingById(id);
		
		return("redirect:/bookings");
	}
	
	@PostMapping("/editBooking/{id}")
	public String editBooking(@PathVariable("id") int id, Model model) {
		Booking  booking = bookingService.getBookingById(id);
		model.addAttribute("booking", booking);
		return("editBooking");
	}
	@PostMapping("/returnBooking")
	private String returnBooking(@ModelAttribute Booking booking) {
		booking.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        booking.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        bookingService.AddorUpdate(booking);
        Invoice invoice = new Invoice();
        invoice.setBookingId(booking.getBookingId());
        invoice.setInvoiceDate(new Date(System.currentTimeMillis()));
        invoice.setTotalAmount(booking.getTotalPrice()); // hoặc giá trị khác từ booking
        invoice.setPdfPath("path/to/pdf"); // Đặt đường dẫn PDF nếu có
        invoice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        invoice.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        invoiceService.AddorUpdate(invoice);
		return "redirect:/bookings";
	}
}
