package com.tourist.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tourist.Entity.Payment;
import com.tourist.Service.PaymentServerService;

@Controller
public class PaymentController2 {

	@Autowired
	private PaymentServerService paymentServerService;
	@GetMapping("/payments")
	public String Payments(Model model) {
		List<Payment> list = paymentServerService.getList();
		model.addAttribute("payments", list);
		return ("payments");
	}
	
}
