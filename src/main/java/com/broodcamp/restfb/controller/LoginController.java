package com.broodcamp.restfb.controller;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.broodcamp.restfb.provider.FacebookProvider;

/**
 * @author Edward P. Legaspi
 */
@Named
@ViewScoped
public class LoginController {

	@Inject
	private FacebookProvider facebookProvider;

	public String getAuthUrl() {
		return facebookProvider.getAuthUrl();
	}

}
