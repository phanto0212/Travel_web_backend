package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.Invoice;

public interface InvoiceService {

	List<Invoice> getList();
	void AddorUpdate(Invoice invoice);
	Invoice getInvoiceByBookingId(int id);
}
