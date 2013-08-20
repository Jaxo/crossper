/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services;

import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.exceptions.UserServiceException;
import com.crossper.models.User;
import com.crossper.representations.UserServiceResponse;
import com.crossper.representations.UserLoginRepresentation;


public interface UserService {
   
    public UserServiceResponse registerUser(UserLoginRepresentation user) throws UserServiceException;
    public User findUser(User user) throws UserServiceException;
    public boolean updateUserLoginDetails ( User user) throws UserServiceException;
    public boolean updateLastLogin ( String email) throws UserServiceException;
    public void updateLoginFailed(String email) throws UserServiceException;
    public User findByEmail(String email) throws UnregisteredUserException;
    public User findById(String userId) throws UnregisteredUserException;
}
