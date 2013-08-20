package com.crossper.representations;

import com.crossper.representations.security.UserRememberMeToken;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * User: dpol
 * Date: 5/30/13
 * Time: 7:14 PM
 */
public class UserToken {
    private String type;
    private String userId;
    private long expiresAfter;
    private String username;
    private String series;
    private String tokenValue;
    private Date date;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonIgnore
    public UserRememberMeToken convertToRememberMeToken(){
        UserRememberMeToken userRememberMeToken = new UserRememberMeToken(getUsername(), getSeries(), getTokenValue(), getDate());
        userRememberMeToken.setType(getType());
        userRememberMeToken.setExpiresAfter(getExpiresAfter());
        userRememberMeToken.setUserId(getUserId());

        return userRememberMeToken;
    }
}
