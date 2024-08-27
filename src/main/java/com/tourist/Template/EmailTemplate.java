package com.tourist.Template;

import java.text.NumberFormat;
import java.util.Locale;

public class EmailTemplate {

	public static String buildThankYouEmail(
            String customerName, 
            int invoiceId, 
            double totalAmount, 
            String paymentDate, 
            String tourName, 
            int tourDays, 
            String startDate, 
            String endDate,
            int adultNumber,
            int childrenNumber
    ) {
        // Tạo đối tượng NumberFormat cho Việt Nam
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        
        // Định dạng số tiền
        String formattedAmount = numberFormat.format(totalAmount);
        
        return "<html>" +
                "<body style='font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4;'>" +
                "<table align='center' width='600' cellpadding='0' cellspacing='0' style='border: 1px solid #ddd; background-color: #ffffff;'>" +
                "<tr>" +
                "<td style='background-color: #007BFF; color: #ffffff; padding: 20px; text-align: center;'>" +
                "<img src='cid:logo' alt='Logo' style='width: 100px; height: auto;'><br>" +
                "<h2 style='margin: 0;'>VÉ ĐIỆN TỬ CỦA QUÝ KHÁCH TRONG TRANG NÀY</h2></td>" +
                "</tr>" +
                "<tr>" +
                "<td style='padding: 20px;'>" +
                "<p style='font-size: 16px; margin: 0 0 10px 0;'>Kính gửi quý khách " + customerName + ",</p>" +
                "<p style='font-size: 14px; line-height: 1.5; margin: 0 0 10px 0;'>Yêu cầu đặt vé của quý khách đã được xác nhận thành công. Quý khách vui lòng xem vé điện tử trong tập tin đính kèm.</p>" +
                "<h3 style='background-color: #ffffff; color: #007BFF; padding: 5px; margin: 10px 0 0 0; text-align: left;'>THÔNG TIN TOUR</h3>" +
                "<table width='100%' cellpadding='0' cellspacing='0' style='margin: 10px 0;'>" +
                "<tr><td style='padding: 10px;'><strong>Tên chuyến du lịch:</strong> " + tourName + "</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Số Ngày Của Chuyến:</strong> " + tourDays + " ngày</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Ngày Bắt Đầu:</strong> " + startDate + "</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Ngày Kết Thúc:</strong> " + endDate + "</td></tr>" +
                "</table>" +
                "<h3 style='background-color: #ffffff; color: #007BFF; padding: 5px; margin: 10px 0 0 0; text-align: left;'>THÔNG TIN HÓA ĐƠN</h3>" +
                "<table width='100%' cellpadding='0' cellspacing='0' style='margin: 10px 0;'>" +
                "<tr><td style='padding: 10px;'><strong>Mã Hóa Đơn:</strong> " + invoiceId + "</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Tổng Hóa Đơn:</strong> " + formattedAmount + " VNĐ</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Ngày Thanh Toán:</strong> " + paymentDate + "</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Số Lượng Vé Người Lớn:</strong> " + adultNumber + "</td></tr>" +
                "<tr><td style='padding: 10px;'><strong>Số Lượng Vé Trẻ Em:</strong> " + childrenNumber + "</td></tr>" +
                "</table>" +
                "<p style='font-size: 14px; line-height: 1.5; margin: 0;'>Chúc bạn có một kì nghỉ tuyệt vời bên gia đình và người thân!</p>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td style='font-size: 12px; color: #888; text-align: center; padding: 10px; background-color: #f8f8f8;'>" +
                "<p style='margin: 0;'>Best Travel,<br>Phan Tỏ Company,<br>hotline:0865701962</p>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "</body>" +
                "</html>";
    }
	}

