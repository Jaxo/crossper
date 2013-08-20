package com.crossper.services;

import com.crossper.repository.UserTokenRepository;
import com.crossper.representations.security.UserRememberMeToken;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * User: dpol
 * Date: 5/30/13
 * Time: 5:16 PM
 */
public class UserRememberMeServices extends AbstractRememberMeServices {
    private UserTokenRepository tokenRepository;

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String username = successfulAuthentication.getName();

        logger.debug("Creating new persistent login for user " + username);
        UserRememberMeToken persistentToken = (UserRememberMeToken) request.getAttribute("user_token");
        persistentToken.setType((String) request.getAttribute("provider"));
        persistentToken.setUserId(persistentToken.getUserId());
        try {
            tokenRepository.createNewToken(persistentToken);
            addCookie(persistentToken, request, response);
        } catch (DataAccessException e) {
            logger.error("Failed to save persistent token ", e);
        }
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) throws RememberMeAuthenticationException, UsernameNotFoundException {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 +
                    " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        final String presentedSeries = cookieTokens[0];
        final String presentedToken = cookieTokens[1];

        UserRememberMeToken token = (UserRememberMeToken) tokenRepository.getTokenForSeries(presentedSeries);

        if (token == null) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);
        }

        // We have a match for this user/series combination
        if (!presentedToken.equals(token.getTokenValue())) {
            // Token doesn't match series value. Delete all logins for this user and throw an exception to warn them.
            tokenRepository.removeUserTokens(token.getUsername());

            throw new CookieTheftException(messages.getMessage("PersistentTokenBasedRememberMeServices.cookieStolen",
                    "Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
        }

        if (token.getDate().getTime() + getTokenValiditySeconds()*1000L < System.currentTimeMillis()) {
            throw new RememberMeAuthenticationException("Remember-me login has expired");
        }

        // Token also matches, so login is valid. Update the token value, keeping the *same* series number.
        if (logger.isDebugEnabled()) {
            logger.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '" +
                    token.getSeries() + "'");
        }

        UserRememberMeToken newToken = new UserRememberMeToken(token.getUsername(),
                token.getSeries(), token.getTokenValue(), new Date());

        try {
            tokenRepository.updateToken(newToken.getSeries(), newToken.getTokenValue(), newToken.getDate());
            addCookie(newToken, request, response);
        } catch (DataAccessException e) {
            logger.error("Failed to update token: ", e);
            throw new RememberMeAuthenticationException("Autologin failed due to data access problem");
        }

        return getUserDetailsService().loadUserByUsername(token.getUsername());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);

        if (authentication != null) {
            tokenRepository.removeUserTokens(authentication.getName());
        }
    }

    private void addCookie(UserRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[] {token.getSeries(), token.getTokenValue()}, new Long(token.getExpiresAfter()).intValue(), request, response);
    }

    public void setTokenRepository(UserTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Authentication createAutoLogin(HttpServletRequest request, String username) {
        UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);
        return createSuccessfulAuthentication(request, userDetails);
    }
}
