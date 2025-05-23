package com.tourist.Controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tourist.Entity.News;
import com.tourist.Service.NewService;

@Controller
public class NewsController {

	@Autowired
	private NewService newService;
	
	@GetMapping("/news")
	public String News(Model model) {
		List<News> list = newService.getList();
		model.addAttribute("newItems", list);
		return ("news");

	}
	@PostMapping("/add/new")
	public String addNew(@ModelAttribute News news) {
		try {
			news.setCreatedAt(new Timestamp(System.currentTimeMillis()));
			news.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
			news.setAuthorId(999);
			news.setTitle("Tour mới");
			newService.addNews(news);
			return "redirect:/news";
		}
		catch(Exception e) {
			e.printStackTrace();
		    throw e;	
		}
	}
	@PostMapping("/delete/new/{id}")
	public String deleteNew(@PathVariable("id") int id) {
		try {
			newService.deleteNews(id);
			return("redirect:/news");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
