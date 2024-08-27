package com.tourist.Respository;



import java.util.List;

import com.tourist.Entity.Invoice;


public interface InvoiceRespository  {

	void AddOrUpdate(Invoice invoice);
	List<Invoice> getList();
	Invoice getInvoiceByBookingId(int id);
}
