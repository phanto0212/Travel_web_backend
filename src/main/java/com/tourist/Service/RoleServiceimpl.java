package com.tourist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Entity.Role;
import com.tourist.Respository.RoleRespository;
@Service
public class RoleServiceimpl implements RoleService {

	@Autowired
	private RoleRespository roleRespository;


	@Override
	public Role findByRoleName(String name) {
		
			return roleRespository.findByName(name);
		
	}
	


}
