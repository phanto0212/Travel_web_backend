package com.tourist.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Payment;
import com.tourist.Respository.PaymentRespository;
@Service
public class PaymentServerServiceimpl implements PaymentServerService{

	@Autowired
	private PaymentRespository paymentRespository;
	@Override
	public List<Payment> getList() {
		// TODO Auto-generated method stub
		return paymentRespository.getList();
	}

	@Override
	public void AddorUpdate(Payment payment) {
		paymentRespository.AddorUpdate(payment);
		
	}

	@Override
	public Payment FindPaymentById(int id) {
		// TODO Auto-generated method stub
		return paymentRespository.FindPaymentById(id);
	}

	@Override
	public void deleteById(int id) {
		paymentRespository.deleteById(id);
		
	}

}
