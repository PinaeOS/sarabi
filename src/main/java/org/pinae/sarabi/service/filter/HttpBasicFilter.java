package org.pinae.sarabi.service.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServiceResponse;

public class HttpBasicFilter implements ServiceFilter {
	
	private Map<String, String> authInfo = new ConcurrentHashMap<String, String>();
	
	public HttpBasicFilter(Map<String, String> authInfo) {
		this.authInfo = authInfo;
	}

	public ServiceResponse filter(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if (auth != null && auth.startsWith("Basic")) {
			String token = StringUtils.substringAfter(auth, "Basic").trim();
			String authUP[] = new String(Base64.decodeBase64(token)).split(" ");
			if (authUP != null && authUP.length == 2) {
				String username = authUP[0];
				String password = this.authInfo.get(username);
				if (StringUtils.isNotBlank(password)) {
					if (password.equals(authUP[1])) {
						return new ServiceResponse(Http.HTTP_OK);
					} else {
						return new ServiceResponse(Http.HTTP_UNAUTHORIZED);
					}
				}
			}
		}
		return new ServiceResponse(Http.HTTP_NON_AUTH_INFO);
	}

}
