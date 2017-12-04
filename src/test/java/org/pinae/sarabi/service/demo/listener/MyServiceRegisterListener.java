package org.pinae.sarabi.service.demo.listener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.pinae.sarabi.service.listener.RegisterListener;

public class MyServiceRegisterListener implements RegisterListener {

	private static Logger logger = Logger.getLogger(MyServiceRegisterListener.class);
	
	public void register(String name, String description, String url, String[] method) {
		if (StringUtils.isNotBlank(description)) {
			logger.info(String.format("name=%s, description=%s, url=%s, method=%s", 
					name, description, url, StringUtils.join(method, ";")));
		} else {
			logger.info(String.format("name=%s, url=%s, method=%s", 
					name, url, StringUtils.join(method, ";")));
		}
	}

}
