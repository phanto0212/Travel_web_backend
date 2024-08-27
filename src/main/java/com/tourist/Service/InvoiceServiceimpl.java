package com.tourist.Service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Invoice;
import com.tourist.Respository.InvoiceRespository;

@Service
public class InvoiceServiceimpl implements InvoiceService {

	@Autowired
	private InvoiceRespository invoiceRespository;
	
	@Override
	public List<Invoice> getList() {
		try {
			return invoiceRespository.getList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void AddorUpdate(Invoice invoice) {
		invoiceRespository.AddOrUpdate(invoice);
		
	}

	@Override
	public Invoice getInvoiceByBookingId(int id) {
		// TODO Auto-generated method stub
		return invoiceRespository.getInvoiceByBookingId(id);
	}
	
	
	

}
