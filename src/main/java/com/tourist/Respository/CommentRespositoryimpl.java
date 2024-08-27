package com.tourist.Respository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tourist.Entity.Comment;
import com.tourist.Entity.Invoice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class CommentRespositoryimpl implements CommentRespository {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List<Comment> getListCommentByNew_id(int new_id) {
		try {
			String hql = "From Comment c WHERE c.newsId = :newsId";
			TypedQuery<Comment> query = entityManager.createQuery(hql, Comment.class);
            query.setParameter("newsId", new_id);
            return query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void addComment(Comment comment) {
		try {
			if (comment.getCommentId() == null) {
                // Nếu tourId không null, tức là đối tượng này đã tồn tại, ta sẽ cập nhật nó
                entityManager.merge(comment);
            } else {
                // Nếu tourId là null, tức là đối tượng này mới, ta sẽ thêm mới nó
                entityManager.persist(comment);
            }
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public List<Comment> getList() {
		try {
			String hql ="From Comment";
			return entityManager.createQuery(hql, Comment.class).getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteComment(int id) {
		try {
			Comment comment = entityManager.find(Comment.class, id);
			if(comment == null) {
				entityManager.remove(comment);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
