package com.bs.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bs.beans.MyUserDetails;
import com.bs.beans.User;
import com.bs.dao.BrokerageUserDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	BrokerageUserDao brokerageUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//brokerageUserDao.findUserByUsername(username);
		Optional<User> user = brokerageUserDao.findUserByUsername(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Invalid Username" + " " + username));
		// user ->  new MyUserDetails(user); as in lambda expression
		return user.map(MyUserDetails::new).get();
		
	}

}
