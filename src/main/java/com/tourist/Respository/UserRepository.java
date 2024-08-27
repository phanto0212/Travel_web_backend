package com.tourist.Respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tourist.Entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

		
	
}
