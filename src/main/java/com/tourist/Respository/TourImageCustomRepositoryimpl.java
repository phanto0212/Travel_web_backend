package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tourist.Entity.TourImage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
@Repository
@Transactional

public class TourImageCustomRepositoryimpl implements TourImageCustomRepository  {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<TourImage> getAllByTour_id(int tour_id) {
		String hql = "From TourImage t WHERE t.tour.tourId = :tour_id ";
		
		TypedQuery<TourImage> query = entityManager.createQuery(hql, TourImage.class);
		query.setParameter("tour_id", tour_id);
		return query.getResultList();
		
		
		
		
	}

}
