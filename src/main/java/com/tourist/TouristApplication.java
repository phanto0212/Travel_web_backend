package com.tourist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tourist.Configs.JwtTokenUtil;

@SpringBootApplication

@ComponentScan("com.tourist") // Scan các thành phần trong gói com.tourist và các gói con của nó
public class TouristApplication {

	   
	public static void main(String[] args) {
		
	   
	        
	        // Mật khẩu gốc
	      
		SpringApplication.run(TouristApplication.class, args); 
	}

}
