package com.bs.dao;

import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bs.beans.User;

public interface BrokerageUserDao extends CrudRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM USER WHERE NAME = ?1 ", nativeQuery = true)
	Optional<User> findUserByUsername(String username);
}
