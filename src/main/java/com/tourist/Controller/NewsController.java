package com.tourist.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		model.addAttribute("news", list);
		return ("news");

	}
	@PostMapping("/add/new")
	public String addNew(@ModelAttribute News news) {
		try {
			newService.addNews(news);
			return "redirect:/news";
		}
		catch(Exception e) {
			e.printStackTrace();
		    throw e;	
		}
	}
}
