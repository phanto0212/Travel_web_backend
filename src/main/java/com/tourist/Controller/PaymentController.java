package com.tourist.Controller;


import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.DTO.PaymentDTO;
import com.tourist.DTO.ResponseObject;
import com.tourist.Entity.Booking;
import com.tourist.Entity.Invoice;
import com.tourist.Entity.Payment;
import com.tourist.Entity.Tour;
import com.tourist.Entity.User;
import com.tourist.Service.BookingService;
import com.tourist.Service.EmailService;
import com.tourist.Service.InvoiceService;
import com.tourist.Service.PaymentServerService;
import com.tourist.Service.PaymentService;
import com.tourist.Service.TourService;
import com.tourist.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("${spring.application.api-prefix}/payment")
public class PaymentController {
    private final PaymentService paymentService;
    
    @Autowired
	private PaymentServerService paymentServerService;
    
    @Autowired
	private UserService userService;

    @Autowired
	private BookingService bookingService;
    
    @Autowired
	private InvoiceService invoiceService;
    
    @Autowired
	private TourService tourService;
    
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
	private EmailService emailService;
    
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String reqUserid = request.getParameter("userId");
        String reqBookingid = request.getParameter("bookingId");
        String reqAmount = request.getParameter("vnp_Amount");
  
        if (status != null) {
            if ("00".equals(status)) {
            	try {
            		Payment payment = new Payment();             
                    int userid = Integer.parseInt(reqUserid);                                                                   
                    int bookingid = Integer.parseInt(reqBookingid);
                    payment.setBookingId(bookingid);                              
                    BigDecimal amount = new BigDecimal(reqAmount);
                    payment.setAmount(amount);
                    payment.setPaymentMethod("NCB Banking");
                    payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
                	payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                	payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                	payment.setStatus("Successful");               	
                	Invoice invoice = invoiceService.getInvoiceByBookingId(bookingid);
                	payment.setInvoiceId(invoice.getInvoiceId());
                	Booking bookingSuccess = bookingService.getBookingById(bookingid);
                	bookingSuccess.setStatus("COMPLETED");
                	paymentServerService.AddorUpdate(payment);
                	bookingService.AddorUpdate(bookingSuccess);
                	User user = userService.getUserById(userid);
                	double doubleAmount = Double.parseDouble(reqAmount);
                	Booking booking = bookingService.getBookingById(bookingid);
                	Tour tour = tourService.getTourById(booking.getTour().getTourId());
                	Date today = new Date(System.currentTimeMillis());
                	String todayString = String.valueOf(today);
                	String startDate = String.valueOf(tour.getStartDate());
                	String endDate = String.valueOf(tour.getEndDate());
                	emailService.processPaymentAndSendEmail(user.getEmail(), user.getFullName(), invoice.getInvoiceId(), doubleAmount, todayString,
                	tour.getTitle(), tour.getDuration(), startDate, endDate, booking.getNumber_of_adults(), booking.getNumber_of_children());
                	
            	}
            	catch(Exception e) {
            		e.printStackTrace();
            		throw e;
            	}
            	
            	
            	
                return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
            } else {
            	try {
            		Payment payment = new Payment();             
                    int userid = Integer.parseInt(reqUserid);                                                                   
                    int bookingid = Integer.parseInt(reqBookingid);
                    payment.setBookingId(bookingid);                              
                    BigDecimal amount = new BigDecimal(reqAmount);
                    payment.setAmount(amount);
                    payment.setPaymentMethod("NCB Banking");
                    payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
                	payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                	payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                	payment.setStatus("FAILED");               	
                	Invoice invoice = invoiceService.getInvoiceByBookingId(bookingid);
                	payment.setInvoiceId(invoice.getInvoiceId());
                	Booking bookingSuccess = bookingService.getBookingById(bookingid);
                	bookingSuccess.setStatus("FAIlED");
                	paymentServerService.AddorUpdate(payment);
                	bookingService.AddorUpdate(bookingSuccess);
                	
            	}
            	catch(Exception e) {
            		e.printStackTrace();
            		throw e;
            	}
                return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
            }
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Response code is missing", null);
        }
    }
}

