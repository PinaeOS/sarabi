package org.pinae.sarabi.service.filter;

import javax.servlet.http.HttpServletRequest;

import org.pinae.sarabi.service.ServiceResponse;

public interface ServiceFilter {
	
	public static final String HTTP_BASIC_FILTER = "HttpBasicFilter";
	
	public String getName();
	
	public ServiceResponse filter(HttpServletRequest request);
	
}
