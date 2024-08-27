package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tourist.Entity.Payment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public class PaymentRespositoryimpl implements PaymentRespository {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Payment> getList() {
		try {
			String hql = "From Payment";
			return entityManager.createQuery(hql, Payment.class).getResultList();
			
			}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void AddorUpdate(Payment payment) {
		try {
		
			if(payment.getPaymentId() != null) {
				entityManager.merge(payment);
				
			}
			else {
				entityManager.persist(payment);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public Payment FindPaymentById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Payment.class, id);
	}

	@Override
	public void deleteById(int id) {
		try {
			Payment payment = entityManager.find(Payment.class, id);
			entityManager.remove(payment);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
