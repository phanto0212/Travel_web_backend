package com.tourist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.User_Role;
import com.tourist.Respository.UserRoleRespository;

@Service
public class UserRoleServiceimpl implements UserRoleService{

	@Autowired
	private UserRoleRespository userRoleRespository;
	
	@Override
	public void save(User_Role user_role) {
		try {
			userRoleRespository.save(user_role);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}

}
