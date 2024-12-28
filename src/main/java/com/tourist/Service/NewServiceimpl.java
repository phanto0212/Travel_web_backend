package com.tourist.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.News;
import com.tourist.Respository.NewRespository;
@Service
public class NewServiceimpl implements NewService{

	@Autowired
	private NewRespository newRespository;
	@Override
	public void addNews(News news) {
		newRespository.save(news);
		
	}

	@Override
	public List<News> getList() {
		
		return newRespository.findAll();
	}

	@Override
	public void deleteNews(int id) {
		newRespository.deleteById(id);
		
	}

}
