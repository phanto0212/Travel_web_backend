package com.tourist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Email;
import com.tourist.Template.EmailTemplate;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
           
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException e) {
        	e.printStackTrace();
        	throw e;
        }
      
    }
    public void sendThankYouEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates the content is HTML

        ClassPathResource logo = new ClassPathResource("static/images/Screenshot 2024-07-31 113310.png");
        helper.addInline("logo", logo);
        javaMailSender.send(mimeMessage);
    }

    public void processPaymentAndSendEmail(String customerEmail, String customerName, 
		    int invoiceId, 
		    double totalAmount, 
		    String paymentDate, 
		    String tourName, 
		    int tourDays, 
		    String startDate, 
		    String endDate,
		    int adultNumber,
		    int childrenNumber) {
        // Tạo nội dung email
        String subject = "Thank You for Your Payment";
        String htmlContent = EmailTemplate.buildThankYouEmail(  customerName, 
    		     invoiceId, 
    		     totalAmount, 
    		     paymentDate, 
    		     tourName, 
    		     tourDays, 
    		     startDate, 
    		     endDate,
    		     adultNumber,
    			 childrenNumber);

        try {
            sendThankYouEmail(customerEmail, subject, htmlContent);
        } catch (MessagingException e) {
            e.printStackTrace(); // Xử lý lỗi gửi email nếu cần
        }
    }
}
