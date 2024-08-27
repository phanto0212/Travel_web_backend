package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tourist.Entity.Booking;
import com.tourist.Entity.Rate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class BookingRespositoryimpl implements BookingRespository{

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Booking> getList() {
		try {
			String hql = "From Booking";
			return entityManager.createQuery(hql, Booking.class).getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void AddorUpdate(Booking booking) {
		try {
	        if (booking.getBookingId() == null) {
	            // Đối tượng mới, sử dụng persist để lưu vào cơ sở dữ liệu
	            entityManager.persist(booking);
	        } else {
	            // Đối tượng đã tồn tại, sử dụng merge để cập nhật
	            entityManager.merge(booking);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	@Override
	public Booking getBookingById(int id) {
         try {
			return entityManager.find(Booking.class, id);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteBookingById(int id) {
		  try {
				Booking  booking = entityManager.find(Booking.class, id);
				if(booking == null) {
					System.out.print("không có booking để xóa!");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		
	}

	@Override
	public List<Booking> getListByUserId(int user_id) {
		  try {
				String hql = "From Booking b WHERE b.user.id = :user_id";
				TypedQuery<Booking> query = entityManager.createQuery(hql, Booking.class);
				query.setParameter("user_id", user_id);
				return query.getResultList();
			}
			catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
	}

	@Override
	public List<Booking> getListByTourId(int tour_id) {
		  try {
				String hql = "From Booking b WHERE b.tour.tourId = :tour_id";
				TypedQuery<Booking> query = entityManager.createQuery(hql, Booking.class);
				query.setParameter("tour_id", tour_id);
				return query.getResultList();
			}
			catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
	}

	@Override
	public Booking getBookingByTourAndUser(int tourId, int userId) {
		try {
			String hql = "From Booking b WHERE b.tour.tourId = :tourId AND b.user.id = :userId";
			TypedQuery<Booking> query = entityManager.createQuery(hql, Booking.class);
			query.setParameter("tourId", tourId);
			query.setParameter("userId", userId);
			return query.getSingleResult();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Booking> getBookingbyStatus(Long userid, String text) {
		try {
			String hql ="From Booking b WHERE b.user.id = :userId AND b.status = :text";
			TypedQuery<Booking> query = entityManager.createQuery(hql, Booking.class);
			query.setParameter("text", text);
			query.setParameter("userId", userid);
			return query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Booking getBookingByUserIdAndTourid(Long userId, int tourId) {
		try {
			String hql ="From Booking b WHERE b.user.id = :userId AND b.status = :text AND b.tour.tourId = :tourId";
			TypedQuery<Booking> query = entityManager.createQuery(hql, Booking.class);
			query.setParameter("text", "COMPLETED");
			query.setParameter("userId", userId);
			query.setParameter("tourId", tourId);
			List<Booking> results = query.getResultList();
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
