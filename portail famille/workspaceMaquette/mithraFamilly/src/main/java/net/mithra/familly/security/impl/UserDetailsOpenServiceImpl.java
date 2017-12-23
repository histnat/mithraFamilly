package net.mithra.familly.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.security.UserDetailsOpenService;

@Service("UserDetailsOpenService")
public class UserDetailsOpenServiceImpl implements UserDetailsOpenService {
	
	@Autowired
	DBUserService dbUserService;
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = dbUserService.getUserOpenByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found.");
		}
		return user;
	}
	
	
	


	public DBUserService getUserService() {
		return dbUserService;
	}


	public void setUserService(DBUserService userService) {
		this.dbUserService = userService;
	}

}
