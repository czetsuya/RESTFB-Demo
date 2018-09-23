package com.broodcamp.restfb.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broodcamp.restfb.provider.FacebookProvider;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;

/**
 * @author Edward P. Legaspi
 */
@WebServlet("/oath_callback")
public class OauthCallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 4400146595698418400L;

	private static Logger log = LoggerFactory.getLogger(OauthCallbackServlet.class);

	@Inject
	private FacebookProvider facebookProvider;

	private String code;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException("ERROR: Didn't get code parameter in callback.");
		}
		String accessToken = facebookProvider.obtainAccessToken(code);
		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		User facebookUser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name,birthday"));
		log.debug("FB User firstName={}, lastName={}, email={}, birthday={}", facebookUser.getFirstName(), facebookUser.getLastName(), facebookUser.getEmail(),
				facebookUser.getBirthday());

		RequestDispatcher dispatcher = req.getRequestDispatcher("account.jsf?accessToken=" + accessToken);
		dispatcher.forward(req, res);
	}
}
