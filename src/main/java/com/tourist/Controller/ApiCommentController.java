package com.tourist.Controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.eclipse.angus.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Configs.JwtTokenUtil;
import com.tourist.Entity.Comment;
import com.tourist.Entity.User;
import com.tourist.Service.CommentService;
import com.tourist.Service.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comments")
public class ApiCommentController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/add/{new_id}")
	public ResponseEntity<?> addComment(@RequestBody Map<String, String> params, HttpServletRequest request
			,@PathVariable("new_id") int new_id){
		try {
			String jwt = request.getHeader("Authorization");
			 
	        if (jwt == null ) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token" );
	        }
	        if (jwt.startsWith("Bearer ")) {
	            jwt = jwt.substring(7);
	        }
	        Claims claims = jwtTokenUtil.getClaimsFromToken(jwt);
	        java.util.Date expiration = claims.getExpiration();
	        if(expiration.before(new java.util.Date())) {
	        	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("token exprired");
	        }
	        String username = claims.getSubject(); // sub
       
	        if (username == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	        }

	        User user = userService.getUserByUsername(username);
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	        }
            Long userId = user.getId();
            String commentStr = params.get("comment");
            if(commentStr.isEmpty()) {
            	 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("comment don't value!");
            }
            Comment comment = new Comment();
            comment.setComment(commentStr);
            comment.setNewsId(new_id);
            comment.setUser(user);
            comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            comment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            commentService.addComment(comment);
            return ResponseEntity.ok(comment);
            
            
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("add comment fail!");
		
		}
		
	}
	@GetMapping("/get/comments/{new_id}")
	public ResponseEntity<?> getCommentByNew_id(@PathVariable("new_id") int new_id) {
	    try {
	        List<Comment> list = commentService.getListCommentByNew_id(new_id);
	        if (list.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("don't have another comment with " + new_id);
	        }
	        return ResponseEntity.ok(list);
	    } catch(Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("get comment fail!");
	    }
	}
		
	
	@DeleteMapping("/delete/comment/{comment_id}")
	public ResponseEntity<?> deleteComment(@PathVariable("comment_id") int comment_id){
		try {
			commentService.deleteComment(comment_id);
			return ResponseEntity.ok("delete commentsuccessful");
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("delete comment fail!");
		}
	}
}
