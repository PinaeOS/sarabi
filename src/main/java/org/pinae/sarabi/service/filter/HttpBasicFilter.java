package org.pinae.sarabi.service.filter;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServiceResponse;
import org.pinae.sarabi.service.util.FileUtils;

public class HttpBasicFilter implements ServiceFilter {
	
	private Map<String, String> authInfo = new ConcurrentHashMap<String, String>();
	
	public HttpBasicFilter() {
		try {
			String authFilename = "user.properties";
			if (authFilename != null) {
				File authFile = FileUtils.getFile(null, authFilename);
				if (authFile != null) {
					Properties properties = new Properties();
					properties.load(new FileInputStream(authFile));
					Enumeration<Object> usernames = properties.keys();
					while (usernames.hasMoreElements()) {
						String username = usernames.nextElement().toString();
						String password = properties.getProperty(username);
						if (StringUtils.isNotBlank(password)) {
							this.authInfo.put(username, password);
						}
					}
				}
			}
		} catch (Exception e){
			
		}
	}
	
	public HttpBasicFilter(Map<String, String> authInfo) {
		this.authInfo = authInfo;
	}

	public ServiceResponse filter(HttpServletRequest request) {
		String authReq = request.getHeader("Authorization");
		if (authReq == null) {
			return new ServiceResponse(Http.HTTP_UNAUTHORIZED, "Couldn't find authorization config");
		}
		if (authReq.startsWith("Basic")) {
			authReq = StringUtils.substringAfter(authReq, "Basic").trim();
			String authItem = new String(Base64.decodeBase64(authReq));
			if (authItem != null && StringUtils.contains(authItem, ":")) {
				String username = StringUtils.substringBefore(authItem, ":");
				String password = StringUtils.substringAfter(authItem, ":");
				if (this.authInfo.containsKey(username) && this.authInfo.get(username).equals(password)) {
					return new ServiceResponse(Http.HTTP_OK);
				} else {
					return new ServiceResponse(Http.HTTP_UNAUTHORIZED, "Username or password is wrong");
				}
			}
		} else {
			return new ServiceResponse(Http.HTTP_UNAUTHORIZED, "Unknown authorization type");
		}

		return new ServiceResponse(Http.HTTP_UNAUTHORIZED, "Authorization fail");
	}

	
	public String getName() {
		return "HttpBasicFilter";
	}
	
}
