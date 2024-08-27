package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tourist.Entity.Rate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Repository
@Transactional
public class RatingRespositoryimpl implements RatingRespository {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Rate> getAllRatingByTourId(int tourId) {
		try {
			String hql = "From Rate r WHERE r.tourId = :tourId";
			TypedQuery<Rate> query = entityManager.createQuery(hql, Rate.class);
			query.setParameter("tourId", tourId );
			return query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Rate getRatingById(int id) {
		try {
			return entityManager.find(Rate.class, id);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void addRating(Rate rate) {
		try {
			 if (rate.getRatingId() == null) {
	                // Nếu tourId không null, tức là đối tượng này đã tồn tại, ta sẽ cập nhật nó
	                entityManager.merge(rate);
	            } else {
	                // Nếu tourId là null, tức là đối tượng này mới, ta sẽ thêm mới nó
	                entityManager.persist(rate);
	            }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deletRating(int id) {
		try {
			Rate rate = entityManager.find(Rate.class, id);
			if(rate == null) {
				System.out.print("không có rate này để xóa");
			}
			entityManager.remove(rate);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public Rate getRatingByUserId(Long userId) {
		try {
			String hql = "From Rate r  WHERE r.user.id = :userId";
			TypedQuery<Rate> query = entityManager.createQuery(hql, Rate.class);
			query.setParameter("userId", userId);
			List<Rate> results = query.getResultList();
			if (results.isEmpty()) {
			    return null;
			} else {
			    return results.get(0);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Rate getRatingByUseridAndTourId(Long userId, int tour_id) {
		try {
			String hql = "From Rate r WHERE r.tourId = :tourId AND r.user.id = :userId";
			TypedQuery<Rate> query = entityManager.createQuery(hql, Rate.class);
			query.setParameter("tourId", tour_id );
			query.setParameter("userId", userId );
			List<Rate> results = query.getResultList();
			if (results.isEmpty()) {
			    return null;
			} else {
			    return results.get(0);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
