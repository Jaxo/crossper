/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.services.impl;

import com.crossper.exceptions.SystemException;
import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.exceptions.UserServiceException;
import com.crossper.models.User;
import com.crossper.repository.dao.UserDao;
import com.crossper.representations.ServiceResponse;
import com.crossper.representations.UserServiceResponse;
import com.crossper.representations.UserLoginRepresentation;
import com.crossper.services.EmailService;
import com.crossper.services.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService {
    public enum PROVIDER {crossper, facebook};
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private EmailService emailService;
    
    @Override
    public UserServiceResponse registerUser(UserLoginRepresentation user)  throws UserServiceException {
        UserServiceResponse response = new UserServiceResponse();
        //TODO: remove after value is set in UI;
        /*if (user.getProvider() == null || user.getProvider().equalsIgnoreCase(PROVIDER.crossper.toString()) ){
            if ( ! isValidEmail(user.getEmail(),user.getEmail())) 
                throw new EmailValidationException("singup.invalidEmail");
        }*/
        user.setAgreeTerms(true);
        if ( ! user.isAgreeTerms()) {
            throw new UserServiceException("signup.termsNotAgreed");
        } else {
            boolean isNewUser = false;
            try {
            	User existinguser= userDao.findUserbyEmail(user.getEmail());   
            	response.setResponseObj(existinguser);
            }catch  (UsernameNotFoundException ex ) {
                isNewUser = true;
            }catch ( DataAccessException ex2) {
                throw new SystemException(ex2.getMessage());
            }catch (Exception ex3) {
                throw new SystemException(ex3.getMessage());
            }
            if (isNewUser) {
                try {
                    User registeredUser = userDao.addUser(convertToUserModel(user));
                    response.setResponseObj(registeredUser);
                    response.setStatus(ServiceResponse.SUCCESS);
                }catch (Exception ex) {
                    throw new SystemException(ex.getMessage());
                }
            } else {
                if ( user.getProvider() != null && user.getProvider().equalsIgnoreCase("crossper"))
                    throw new UserServiceException("signup.userExists");       
            }
        }
        return response;
    }
    
    @Override
    public User findUser(User user) throws UserServiceException {
        try {
            User foundUser = userDao.findUser(user);
            return foundUser;
        } catch (Exception ex){
            throw new SystemException(ex.getMessage());
        }
    }
    private boolean isValidEmail(String userName, String email) {
        boolean mailSent= emailService.sendValidationMail(email, userName);
        return mailSent;
    }
    
    @Override
     public User findByEmail(String email) throws UnregisteredUserException {
         User user = userDao.findUserbyEmail(email);
         return user;
    }
    
    @Override
    public User findById(String userId) throws UnregisteredUserException {
        User user = userDao.findUserById(userId);
        return user;
    }
    @Override
    public boolean updateUserLoginDetails ( User user) throws UserServiceException {
        user.setLastLoginDate(new Date());
        userDao.updateUser(user);
        return true;
    }
    
    public boolean updateLastLogin ( String email) throws UserServiceException {
        return userDao.updateUserLastLogin(email);
    }
    
    public void  updateLoginFailed ( String email) throws UserServiceException {
        userDao.updateLoginFailed(email);
    }
    public static User convertToUserModel(UserLoginRepresentation user) {
        User userModel = new User();
        userModel.setEmail(user.getEmail());
        userModel.setName(user.getUsername());
        userModel.setPassword(user.getPassword());
        userModel.setFbProfile(user.getFacebookProfile());
        userModel.setAgreeTerms(user.isAgreeTerms());
        List<String>roles = new ArrayList<String>();
        roles.add(User.USER_ROLES.ROLE_USER.toString());
        userModel.setRoles(roles);
        return userModel;
    }
}
