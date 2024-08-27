package com.tourist.Controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourist.Entity.News;
import com.tourist.Service.NewService;

import jakarta.persistence.Column;

@RestController
@RequestMapping("/api/news")
public class ApiNewsController {

	@Autowired 
	private NewService newService;
	
	@GetMapping("/")
	public ResponseEntity<?> getNews(){
		try {
			List<News> list = newService.getList();
			return ResponseEntity.ok(list);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("get new fail");
		}
	}
	@GetMapping ("/add")
	public ResponseEntity<?> addNew(@RequestBody Map<String, String> params){
		try {
		  
		    String title = params.get("title");
		    String content = params.get("content");
		    String idStr = params.get("author_id");
		    String image_url = params.get("image_url");
		    System.out.print(title);
		    int author_id = Integer.parseInt(idStr);
		    News news = new News();
		    news.setTitle(title);
		    news.setContent(content);
		    news.setAuthorId(author_id);
		    news.setImage_url(image_url);
		    news.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		    news.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		    newService.addNews(news);
		    return ResponseEntity.ok(news);
		    
		}
		catch(Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("get new fail");
		}
	}
}
