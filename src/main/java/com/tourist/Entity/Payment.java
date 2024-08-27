package com.tourist.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "booking_id", nullable = false)
    private int bookingId;

    @Column(name = "payment_method", length = 30, nullable = false)
    private String paymentMethod;

    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;
  
    
    @Column(name = "number_of_adults", nullable = false)
    private int number_of_adults;
    
    @Column(name = "number_of_children", nullable = false)
    private int number_of_children;
    
    @Column(name = "amount", nullable = false, precision = 20, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

	public Payment(Integer paymentId, int bookingId, String paymentMethod, Timestamp paymentDate, int number_of_adults,
			int number_of_children, BigDecimal amount, String status, Timestamp createdAt, Timestamp updatedAt,
			int invoiceId) {
		super();
		this.paymentId = paymentId;
		this.bookingId = bookingId;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
		this.number_of_adults = number_of_adults;
		this.number_of_children = number_of_children;
		this.amount = amount;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.invoiceId = invoiceId;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Timestamp getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getNumber_of_adults() {
		return number_of_adults;
	}

	public void setNumber_of_adults(int number_of_adults) {
		this.number_of_adults = number_of_adults;
	}

	public int getNumber_of_children() {
		return number_of_children;
	}

	public void setNumber_of_children(int number_of_children) {
		this.number_of_children = number_of_children;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
    
    
    
    
}
