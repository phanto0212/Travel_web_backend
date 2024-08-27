package com.tourist.Respository;

import java.util.List;

import com.tourist.Entity.Payment;

public interface PaymentRespository {

	List<Payment> getList();
	void AddorUpdate(Payment payment);
	Payment FindPaymentById(int id);
	void deleteById(int id);
	
}
