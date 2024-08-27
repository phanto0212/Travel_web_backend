package com.tourist.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Comment;
import com.tourist.Respository.CommentRespository;
@Service
public class CommentServiceimpl implements CommentService {

	@Autowired 
	private CommentRespository commentRespository;
	@Override
	public List<Comment> getList() {
		
		return commentRespository.getList();
	}

	@Override
	public List<Comment> getListCommentByNew_id(int new_id) {
		// TODO Auto-generated method stub
		return commentRespository.getListCommentByNew_id(new_id);
	}

	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		commentRespository.addComment(comment);
		
	}

	@Override
	public void deleteComment(int id) {
		commentRespository.deleteComment(id);
		
	}

}
