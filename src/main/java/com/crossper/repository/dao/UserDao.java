/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao;

import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * 
 */
public interface UserDao {
    
    public User addUser(User user);
    public boolean updateUser(User user) ;
    public User findUserbyEmail(String email) throws UsernameNotFoundException, DataAccessException;
    public User findUserbyUserName(String username) throws Exception;
    public User findUser(User user) throws Exception;
    public User findUserById( String userId) throws UnregisteredUserException;
    public User findFacebookUser(String fbId) throws Exception;
    public boolean isRegisteredUser(String email) throws Exception;
    public boolean updateUserLastLogin( String email);
    public void updateLoginFailed(String email);
    public boolean isValidUserId(String userId);
   
}
