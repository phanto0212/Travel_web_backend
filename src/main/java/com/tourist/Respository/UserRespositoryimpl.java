package com.tourist.Respository;

import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tourist.Entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class UserRespositoryimpl implements UserRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getList() {
        try {
            String hql = "FROM User";
            return entityManager.createQuery(hql, User.class).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void addOrUpdate(User user) {
        try {
            if (user.getId() != null) {
                entityManager.merge(user);
            } else {
                entityManager.persist(user);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            return entityManager.find(User.class, id);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteUserByid(int id) {
        try {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

	@Override
	public User getUserbyEmail(String email) {
	    List<User> users = entityManager.createQuery("FROM User u WHERE u.email = :email", User.class)
	                                    .setParameter("email", email)
	                                    .getResultList();
	    if (users.isEmpty()) {
	        return null; // hoặc xử lý theo yêu cầu của bạn
	    }
	    return users.get(0); // Trả về người dùng đầu tiên trong danh sách
	}


}
