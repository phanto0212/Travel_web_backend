package com.tourist.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "booking_id", nullable = false)
    private int bookingId;

    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;
    
    @Column(name = "number_of_adults", nullable = false)
    private int number_of_adults;
    
    @Column(name = "number_of_children", nullable = false)
    private int number_of_children;

    @Column(name = "total_amount", nullable = false, precision = 20, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "pdf_path", length = 255)
    private String pdfPath;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

	public Invoice(Integer invoiceId, int bookingId, Date invoiceDate, int number_of_adults, int number_of_children,
			BigDecimal totalAmount, String pdfPath, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.invoiceId = invoiceId;
		this.bookingId = bookingId;
		this.invoiceDate = invoiceDate;
		this.number_of_adults = number_of_adults;
		this.number_of_children = number_of_children;
		this.totalAmount = totalAmount;
		this.pdfPath = pdfPath;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
   

	

}
