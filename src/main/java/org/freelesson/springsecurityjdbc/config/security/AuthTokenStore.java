package org.freelesson.springsecurityjdbc.config.security;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

public class AuthTokenStore extends JdbcTokenStore {

	public AuthTokenStore(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = null;
	    try {
	        accessToken = new DefaultOAuth2AccessToken(tokenValue);
	    }
	    catch (EmptyResultDataAccessException e) {
	        System.err.println("Failed to find access token for token "+tokenValue);
	    }
	    catch (IllegalArgumentException e) {
	    	System.err.println("Failed to deserialize access token for " +tokenValue+" : "+e);
	        removeAccessToken(tokenValue);
	    }
	    return accessToken;
	}

}
