package org.pinae.sarabi.service.listener;

import javax.servlet.http.HttpServletRequest;

import org.pinae.sarabi.service.ServiceResponse;

public interface ContextListener extends ServiceListener {
	
	public void handleRequest(HttpServletRequest request);
	
	public void handleResponse(ServiceResponse response);
	
}
