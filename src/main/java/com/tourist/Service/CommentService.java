package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.Comment;

public interface CommentService {

	List<Comment> getList();
	List<Comment> getListCommentByNew_id(int new_id);
	void addComment(Comment comment);
	void deleteComment(int id);
}
