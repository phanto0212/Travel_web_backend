package com.tourist.Service;

import java.util.List;



import com.tourist.Entity.User;

public interface UserService {
	
 User getUserByUsername(String username);
 List<User> getList();
 void AddOrUpdate(User user);
 User getUserById(int id);
 void deleteById(int id);
 User getUserByEmail(String email);
}
