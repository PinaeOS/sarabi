package org.pinae.sarabi.service.demo;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServiceResponse;
import org.pinae.sarabi.service.filter.ServiceFilter;

public class CustomFilter implements ServiceFilter {
	
	private static Logger logger = Logger.getLogger(CustomFilter.class);

	public String getName() {
		return "CustomFilter";
	}

	public ServiceResponse filter(HttpServletRequest request) {
		logger.info("Invoke custom filter and set session");
		request.setAttribute("session", "custom");
		return new ServiceResponse(Http.HTTP_OK);
	}

}
