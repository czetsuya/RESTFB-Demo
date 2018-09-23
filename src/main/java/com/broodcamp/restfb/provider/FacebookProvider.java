package com.broodcamp.restfb.provider;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Version;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;

/**
 * @author Edward P. Legaspi
 */
@Singleton
@Startup
public class FacebookProvider {

	private String appId = "728487190862447";
	private String appSecret = "66c6ca1d53633281cf49c27aa5d8e437";
	private String redirectUrl = "http://localhost:8080/restfb-demo/oauth_callback";
	private String loginDialogUrlString;

	@PostConstruct
	private void init() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder = scopeBuilder.addPermission(FacebookPermissions.EMAIL);
		scopeBuilder = scopeBuilder.addPermission(FacebookPermissions.PUBLIC_PROFILE);

		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		loginDialogUrlString = client.getLoginDialogUrl(appId, redirectUrl, scopeBuilder);
	}

	public String getAuthUrl() {
		return loginDialogUrlString;
	}

	public String obtainAccessToken(String verificationCode) {
		FacebookClient client = new DefaultFacebookClient(Version.LATEST);
		AccessToken accessToken = client.obtainUserAccessToken(appId, appSecret, redirectUrl, verificationCode);

		return accessToken.getAccessToken();
	}
}
