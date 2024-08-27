package com.tourist.Respository;


import java.util.List;

import com.tourist.Entity.Comment;

public interface CommentRespository{

	List<Comment> getList();
	List<Comment> getListCommentByNew_id(int new_id);
	void addComment(Comment comment);
	void deleteComment(int id);

}
