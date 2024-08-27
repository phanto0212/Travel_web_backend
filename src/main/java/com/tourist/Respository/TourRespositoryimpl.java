package com.tourist.Respository;

import java.util.List;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tourist.Entity.Tour;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;



@Repository
@Transactional
public class TourRespositoryimpl implements TourRespository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tour> getList() {
        try {
            String hql = "FROM Tour";  // sử dụng class entity thay vì tên bảng
            return entityManager.createQuery(hql, Tour.class).getResultList();  // sử dụng TypedQuery
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void AddOrUpdate(Tour tour) {
        try {
            if (tour.getTourId() != null) {
                // Nếu tourId không null, tức là đối tượng này đã tồn tại, ta sẽ cập nhật nó
                entityManager.merge(tour);
            } else {
                // Nếu tourId là null, tức là đối tượng này mới, ta sẽ thêm mới nó
                entityManager.persist(tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Tour getTourById(int id) {
        try {
            return entityManager.find(Tour.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            Tour tour = entityManager.find(Tour.class, id);
            if (tour != null) {
                entityManager.remove(tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public void saveAll(List<Tour> tours) {
		try {
			for(Tour tour : tours) {
				entityManager.merge(tour);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Tour> getPageTours(int page, int size) {
		String queryStr = "FROM Tour";
        TypedQuery<Tour> query = entityManager.createQuery(queryStr, Tour.class);
        query.setFirstResult(page * size); // Offset - vị trí bắt đầu của dữ liệu
        query.setMaxResults(size); // Số lượng kết quả trên mỗi trang

        return query.getResultList();
	}

	@Override
	public Long CountTour() {
		String queryStr = "SELECT COUNT(t) FROM Tour t";
        TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
        return query.getSingleResult();
	}

	@Override
	public List<Tour> findTours(Integer text, Integer price) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Tour> cq = cb.createQuery(Tour.class);
	        Root<Tour> tour = cq.from(Tour.class);

	        Predicate criteria = cb.conjunction();

	        if (text != null) {
	        	switch(text) {
	        	case 1:
	        		criteria = cb.and(criteria, cb.like(tour.get("destination_location"), "%" + "Ho Chi Minh" + "%"));
	        		break;
	        	case 2:
	        		criteria = cb.and(criteria, cb.like(tour.get("destination_location"), "%" + "Đà Nẵng" + "%"));
	        		break;	
	        	case 3:
	        		criteria = cb.and(criteria, cb.like(tour.get("destination_location"), "%" + "Đà Lạt" + "%"));
	        		break;
	        	case 4:
	        		criteria = cb.and(criteria, cb.like(tour.get("destination_location"), "%" + "Ninh Bình" + "%"));
	        		break;
	        	}
	            
	        }

	        if (price != null) {
	        	switch(price) {
	        	case 1:
	        		criteria = cb.and(criteria, cb.lessThanOrEqualTo(tour.get("adult_price"), 1000000));
	        		break;
	        	case 2:
	        		criteria = cb.and(criteria, cb.greaterThanOrEqualTo(tour.get("adult_price"), 1000000));
	        		break;
	        		
	        	}
	        	
	        }

	        cq.where(criteria);
	        TypedQuery<Tour> query = entityManager.createQuery(cq);

	        return query.getResultList();
	}

	
}
