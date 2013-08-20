package com.crossper.models;

import com.crossper.representations.FacebookProfile;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

public class User extends AuditBase {
        @Id @ObjectId
        String id;
	private String name;
	private String email;
	private String password;
	private String facebookId;
	private String defaultCity;
	private Date registrationDate;
	private Date lastLoginDate;
	private String userType;
	private boolean emailAlert;
        private boolean agreeTerms;
        private List<String> roles;
        private FacebookProfile fbProfile;
        private Date loginFailedDate;
        public enum USER_ROLES { ROLE_USER, ROLE_ADMIN, ROLE_BUSINESS };
    public User() {
        
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public boolean isEmailAlert() {
        return emailAlert;
    }

    public void setEmailAlert(boolean emailAlert) {
        this.emailAlert = emailAlert;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public FacebookProfile getFbProfile() {
        return fbProfile;
    }

    public void setFbProfile(FacebookProfile fbProfile) {
        this.fbProfile = fbProfile;
    }

    public boolean isAgreeTerms() {
        return agreeTerms;
    }

    public void setAgreeTerms(boolean termsAgreed) {
        this.agreeTerms = termsAgreed;
    }

    public Date getLoginFailedDate() {
        return loginFailedDate;
    }

    public void setLoginFailedDate(Date loginFailedDate) {
        this.loginFailedDate = loginFailedDate;
    }
        

}
