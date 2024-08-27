package com.tourist.Service;

import java.util.List;

import com.tourist.Entity.News;

public interface NewService {

	void addNews(News news);
	List<News> getList();
}
