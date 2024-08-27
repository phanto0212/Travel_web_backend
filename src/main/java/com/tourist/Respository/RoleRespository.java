package com.tourist.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tourist.Entity.Role;

public interface RoleRespository extends JpaRepository<Role, Integer> {

	Role findByName(String name);
}
