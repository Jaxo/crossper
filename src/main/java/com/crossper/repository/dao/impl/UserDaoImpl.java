/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crossper.repository.dao.impl;

import com.crossper.exceptions.DaoException;
import com.crossper.exceptions.UnregisteredUserException;
import com.crossper.models.User;
import com.crossper.repository.dao.UserDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * 
 */
public class UserDaoImpl extends DaoBase implements UserDao {
    static final String USER_COLLECTION = "users";
    static Logger log = Logger.getLogger( UserDaoImpl.class.getName());
    private MongoCollection users;
   
    public UserDaoImpl(Jongo jongo){
        super(jongo);
        this.users = jongo.getCollection(USER_COLLECTION);
    }
    
    private MongoCollection getCollection(){
         if( users == null)
             this.users = jongo.getCollection(USER_COLLECTION);
         return users;
    }
    @Override
    public User addUser(User user) {
        
        MessageDigestPasswordEncoder md5Encoder=new MessageDigestPasswordEncoder("md5");
        String pwd = md5Encoder.encodePassword(user.getPassword(), null);
        user.setPassword(pwd);
        user.setRegistrationDate(new Date());
        getCollection().insert(user);
        return user;
    }
    
    @Override
    public User findUser(User user) throws Exception {
        return user;
    }
    @Override
    public User findUserbyEmail (String email) throws UsernameNotFoundException, DataAccessException {
        User user;
        try {
            user = getCollection().findOne("{email:#}",email).as(User.class);
        }catch (Exception ex) {
            throw new DaoException (ex.getMessage());
        }
        if ( user == null)
            throw new UsernameNotFoundException("Name not found "+ email);
        return user;
    }
    
    @Override
    public User findUserbyUserName (String username) throws Exception {
        User user;
        user = getCollection().findOne("{username:#}",username).as(User.class);
        return user;
    }
    @Override
    public User findFacebookUser(String facebookId) throws Exception {
        User user;
        user = getCollection().findOne("{facebookId:#}",facebookId).as(User.class);
        return user;
    }
    
    @Override
    public boolean isRegisteredUser (String email) throws DataAccessException {
        boolean userRegistered = false;
        try {
            User user = getCollection().findOne("{email:#}",email).as(User.class);
            if (user != null ) {
                userRegistered = true;
            }
        }catch (Exception ex) {
            throw new DaoException (ex.getMessage());
        }
        
        return userRegistered;
    }
    
    @Override
    public boolean updateUser(User user) {
        boolean updateSuccessful = false;
        
        try {
            getCollection().save(user);
            updateSuccessful = true;
        }catch (Exception ex) {
            log.error("Error updating user login date. "+ ex.getMessage());
        }
        return updateSuccessful;
    }
    
    @Override
     public boolean updateUserLastLogin( String email) {
        boolean isSuccess = false;
        try {
            //getCollection().findAndModify("{email : #}", email).with("{ $set: { lastLogin: # }}", new Date());
            User usr = findUserbyEmail(email);
            usr.setLastLoginDate(new Date());
            isSuccess = updateUser(usr);
        }catch (Exception ex) {
            log.error("Error updating user login date. "+ ex.getMessage());
        }
        return isSuccess;
    }
    
    @Override
     public void updateLoginFailed( String email) {
        try {
            //getCollection().findAndModify("{email : #}", email).with("{ $set: {loginFailed: # }}", new Date());
            User usr = findUserbyEmail(email);
            usr.setLoginFailedDate(new Date());
            boolean isSuccess = updateUser(usr);
        }catch (Exception ex) {
            log.error("Error updating user login date. "+ ex.getMessage());
        }
        
    }
    
    @Override
    public boolean isValidUserId(String userId)  {
        User user;
        try {
            user = getCollection().findOne("{_id:#}",new ObjectId(userId)).as(User.class);
            if ( user != null ) {
               log.debug("Found user with Id: " + userId);
               return true;
            }
            else {
                log.debug("User with Id not present: " + userId);
                return false;
            }
        }catch (Exception ex) {
            log.error("Exception checking Valid User : " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public User findUserById(String userId) throws UnregisteredUserException  {
        User user;

            user = getCollection().findOne("{_id:#}",new ObjectId(userId)).as(User.class);
            if ( user != null ) {
               log.debug("Found user with Id: " + userId);
               return user;
            }
            else {
                log.debug("User with Id not present: " + userId);
                throw new UnregisteredUserException("user.unregistered");
            }
    }
}
