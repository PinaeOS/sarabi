package org.pinae.sarabi.service.listener;

import javax.servlet.http.HttpServletRequest;

public interface RequestListener extends ServiceListener {
	
	public void setRequest(HttpServletRequest request);
	
}
