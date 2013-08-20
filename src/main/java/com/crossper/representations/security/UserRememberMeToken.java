package com.crossper.representations.security;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

/**
 * User: dpol
 * Date: 5/30/13
 * Time: 4:14 PM
 */
public class UserRememberMeToken extends PersistentRememberMeToken {
    @Id @ObjectId
    private String id;
    private String type;
    private String userId;
    private long expiresAfter;

    public UserRememberMeToken(){
        super("","","", new Date());
    }

    public UserRememberMeToken(String username, String series, String tokenValue, Date date) {
        super(username, series, tokenValue, new Date());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(long expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}
