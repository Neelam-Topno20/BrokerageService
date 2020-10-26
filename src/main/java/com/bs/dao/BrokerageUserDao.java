package com.bs.dao;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bs.beans.User;

public interface BrokerageUserDao extends CrudRepository<User, Integer> {
	
	@Query(value = "SELECT * FROM USER WHERE EMAIL_ID = ?1 ", nativeQuery = true)
	Optional<User> findUserByEmailId(String emailId);
}
