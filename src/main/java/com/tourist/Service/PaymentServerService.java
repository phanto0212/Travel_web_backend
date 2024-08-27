package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.Payment;

public interface PaymentServerService {

	List<Payment> getList();
	void AddorUpdate(Payment payment);
	Payment FindPaymentById(int id);
	void deleteById(int id);
}
