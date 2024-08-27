package com.tourist.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Entity.Email;
import com.tourist.Service.EmailService;
import com.tourist.Until.PaymentRequest;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send")
    public ResponseEntity<String> sendEmail(@Validated @RequestBody Email email) {
        try {
            emailService.sendEmail(email);
            return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/payments")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        String customerEmail = paymentRequest.getCustomerEmail();
        String customerName = paymentRequest.getCustomerName();
        int invoiceId = paymentRequest.getInvoiceId();
        double totalAmount = paymentRequest.getTotalAmount();
         String paymentDate = paymentRequest.getPaymentDate();
	     String tourName =  paymentRequest.getTourName();
	     int tourDays = paymentRequest.getTourDays();
	     String startDate = paymentRequest.getStartDate();
	     String endDate = paymentRequest.getEndDate();
	     int adultNumber = paymentRequest.getAdultNumber();
		 int childrenNumber = paymentRequest.getChildrenNumber();

        emailService.processPaymentAndSendEmail(customerEmail, customerName, invoiceId, totalAmount, paymentDate,
        		tourName, tourDays, startDate, endDate, adultNumber, childrenNumber);

        return ResponseEntity.ok("Payment processed and email sent.");
    }
}
