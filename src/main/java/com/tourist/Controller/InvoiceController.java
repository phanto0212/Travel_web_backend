package com.tourist.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tourist.Entity.Invoice;
import com.tourist.Service.InvoiceService;

@Controller
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/invoices")
	public String Invoices(Model model) {
		List<Invoice> list = invoiceService.getList();
		model.addAttribute("invoices", list);
		return("invoices");
		
	}
}
