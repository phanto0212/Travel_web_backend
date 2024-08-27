package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tourist.Entity.Invoice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class InvoiceRespositoryimpl implements InvoiceRespository{

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public void AddOrUpdate(Invoice invoice) {
		 try {
			 if (invoice.getInvoiceId() == null) {
	                // Nếu tourId không null, tức là đối tượng này đã tồn tại, ta sẽ cập nhật nó
	                entityManager.merge(invoice);
	            } else {
	                // Nếu tourId là null, tức là đối tượng này mới, ta sẽ thêm mới nó
	                entityManager.persist(invoice);
	            }
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw e;
	        }
		
	}

	@Override
	public List<Invoice> getList() {
		try {
			String hql = "From Invoice";
			return entityManager.createQuery(hql, Invoice.class).getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}

	@Override
	public Invoice getInvoiceByBookingId(int id) {
		try {
			String hql = "From Invoice WHERE bookingId = :bookingId";
			TypedQuery<Invoice> query = entityManager.createQuery(hql, Invoice.class);
            query.setParameter("bookingId", id);
            return query.getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
