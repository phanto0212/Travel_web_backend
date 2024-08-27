package com.tourist.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tourist.Entity.User_Role;
@Repository
public interface UserRoleRespository extends JpaRepository<User_Role, Integer>{

}
