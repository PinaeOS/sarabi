package org.pinae.sarabi.service;

import javax.servlet.http.HttpServletRequest;

import org.pinae.sarabi.service.listener.RequestListener;

public abstract class AbstractService implements RequestListener {
	
	private HttpServletRequest request;

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getRequest() {
		return this.request;
	}

}
