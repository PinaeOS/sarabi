package org.pinae.sarabi.service.demo;

import javax.servlet.http.HttpServletRequest;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServiceResponse;
import org.pinae.sarabi.service.filter.ServiceFilter;

public class CustomFilter implements ServiceFilter {

	public String getName() {
		return "CustomFilter";
	}

	public ServiceResponse filter(HttpServletRequest request) {
		request.setAttribute("session", "custom");
		return new ServiceResponse(Http.HTTP_OK);
	}

}
