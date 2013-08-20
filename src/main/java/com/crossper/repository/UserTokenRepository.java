package com.crossper.repository;

import com.crossper.representations.security.UserRememberMeToken;
import com.mongodb.WriteResult;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * User: dpol
 * Date: 5/30/13
 * Time: 4:03 PM
 */
public class UserTokenRepository implements PersistentTokenRepository {
    private Jongo jongo;

    private MongoCollection persistentTokens;

    public UserTokenRepository(Jongo jongo){
        this.jongo = jongo;
        this.persistentTokens = jongo.getCollection("persistent_token");
    }

    private MongoCollection getCollection(){
        return persistentTokens;
    }
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        UserRememberMeToken userRememberMeToken = (UserRememberMeToken) token;
        getCollection().insert(userRememberMeToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        UserRememberMeToken userRememberMeToken = getCollection().findOne("{series: #}", seriesId).as(UserRememberMeToken.class);
        return userRememberMeToken;
    }

    @Override
    public void removeUserTokens(String username) {
        WriteResult result = getCollection().remove("{username:#}", username);
    }

}
