package com.tourist.Until;

	public class PaymentRequest {

	    private String customerEmail;
	    private String customerName;
	    private int invoiceId;
	    private double totalAmount;
	    private String paymentDate; 
	    private String tourName; 
	    private int tourDays;
	    private String startDate; 
	    private String endDate;
	    private int adultNumber;
	    private int childrenNumber;
		public PaymentRequest(String customerEmail, String customerName, int invoiceId, double totalAmount,
				String paymentDate, String tourName, int tourDays, String startDate, String endDate, int adultNumber,
				int childrenNumber) {
			super();
			this.customerEmail = customerEmail;
			this.customerName = customerName;
			this.invoiceId = invoiceId;
			this.totalAmount = totalAmount;
			this.paymentDate = paymentDate;
			this.tourName = tourName;
			this.tourDays = tourDays;
			this.startDate = startDate;
			this.endDate = endDate;
			this.adultNumber = adultNumber;
			this.childrenNumber = childrenNumber;
		}
		public PaymentRequest() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public int getInvoiceId() {
			return invoiceId;
		}
		public void setInvoiceId(int invoiceId) {
			this.invoiceId = invoiceId;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public String getPaymentDate() {
			return paymentDate;
		}
		public void setPaymentDate(String paymentDate) {
			this.paymentDate = paymentDate;
		}
		public String getTourName() {
			return tourName;
		}
		public void setTourName(String tourName) {
			this.tourName = tourName;
		}
		public int getTourDays() {
			return tourDays;
		}
		public void setTourDays(int tourDays) {
			this.tourDays = tourDays;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public int getAdultNumber() {
			return adultNumber;
		}
		public void setAdultNumber(int adultNumber) {
			this.adultNumber = adultNumber;
		}
		public int getChildrenNumber() {
			return childrenNumber;
		}
		public void setChildrenNumber(int childrenNumber) {
			this.childrenNumber = childrenNumber;
		}
		
		
	    
	}


