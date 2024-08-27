package com.tourist.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tourist.Entity.News;
@Repository
public interface NewRespository extends JpaRepository<News, Integer> {

}
