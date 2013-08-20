package com.crossper.services.impl;

import com.crossper.repository.dao.impl.UserDaoImpl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author ddhanawade
 * Date : 28-May-2013
 */

public class CrossperUserDetailsService implements UserDetailsService{
        private UserDaoImpl userDao;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
                com.crossper.models.User user = userDao.findUserbyEmail(username);  
                 if (user.getRoles() != null) {
                    for (String role : user.getRoles()) {
                           authList.add(new SimpleGrantedAuthority(role));
                    }
                 }
                 return new User(username, user.getPassword(), true, true, true, true, authList);
	}
        
        public void setUserDao(UserDaoImpl dao) {
            this.userDao = dao;
        }
        public UserDaoImpl getUserDao() {
            return this.userDao;
        }

}
