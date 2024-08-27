package com.tourist.Service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Configs.VNPAYConfig;
import com.tourist.DTO.PaymentDTO;
import com.tourist.Entity.Payment;
import com.tourist.Until.VNPayUtil;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//	 private VNPAYConfig vnPayConfig;
//
//	    // Constructor để tiêm phụ thuộc
//	    @Autowired
//	    public PaymentService(VNPAYConfig vnPayConfig) {
//	        this.vnPayConfig = vnPayConfig;
//	    }
//
//	    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//	        String amountStr = request.getParameter("amount");
//	        
//	        if (amountStr == null || amountStr.isEmpty()) {
//	            throw new IllegalArgumentException("Thiếu tham số amount");
//	        }
//
//	        long amount;
//	        try {
//	            amount = Long.parseLong(amountStr) * 100L;
//	        } catch (NumberFormatException e) {
//	            throw new IllegalArgumentException("Định dạng amount không hợp lệ");
//	        }
//
//	        // Tiếp tục với logic còn lại
//	        String bankCode = request.getParameter("bankCode");
//	        String bookingId = request.getParameter("bookingId");
//	        String userId = request.getParameter("userId");
//	        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
//	        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
//	        vnpParamsMap.put("bookingId", bookingId);
//	        vnpParamsMap.put("userId", userId);
//	        if (bankCode != null && !bankCode.isEmpty()) {
//	            vnpParamsMap.put("vnp_BankCode", bankCode);
//	        }
//
//	        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//	        
//	        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
//	        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
//	        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//	        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
//	        
//	        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//	        
//	        return new PaymentDTO.VNPayResponse("ok", "success", paymentUrl);
//	    }
//	    }
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;

    @Autowired
    public PaymentService(VNPAYConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        String amountStr = request.getParameter("amount");
        String bankCode = request.getParameter("bankCode");
        String bookingId = request.getParameter("bookingId");
        String userId = request.getParameter("userId");

        if (amountStr == null || amountStr.isEmpty()) {
            throw new IllegalArgumentException("Thiếu tham số amount");
        }

        long amount;
        try {
            amount = Long.parseLong(amountStr) * 100L;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Định dạng amount không hợp lệ");
        }

        if (bookingId == null || bookingId.isEmpty()) {
            throw new IllegalArgumentException("Thiếu tham số bookingId");
        }

        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Thiếu tham số userId");
        }

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        try {
            String callbackUrl = "http://localhost:8080/api/v1/payment/vn-pay-callback"
                    + "?bookingId=" + URLEncoder.encode(bookingId, StandardCharsets.US_ASCII.toString())
                    + "&userId=" + URLEncoder.encode(userId, StandardCharsets.US_ASCII.toString());
            System.out.println("Callback URL: " + callbackUrl);

            // Chỉ mã hóa giá trị của các tham số
            vnpParamsMap.put("vnp_ReturnUrl", callbackUrl);

            String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
            String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
            String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

            String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
            return new PaymentDTO.VNPayResponse("ok", "success", paymentUrl);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported", e);
        }
    }

    public boolean validateVNPayResponse(HttpServletRequest request) {
        // Logic để xác minh phản hồi từ VNPay (bao gồm kiểm tra chữ ký bảo mật)
        // Trả về true nếu phản hồi hợp lệ, ngược lại trả về false
        return true;
    }

    public void updateBookingPaymentStatus(String bookingId, boolean isPaid) {
        // Logic để cập nhật trạng thái thanh toán của booking dựa trên bookingId
    }
}


