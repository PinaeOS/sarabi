package org.pinae.sarabi.service.util;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class RequestUtils {

	public static String getRequestURI(String prefix, String serviceUri) {
		if (prefix != null && serviceUri != null) {
			if (serviceUri.startsWith(prefix)) {
				return StringUtils.substringAfter(serviceUri, prefix);
			}
		}
		return serviceUri;
	}

	public static Map<String, String> getHeader(HttpServletRequest request) {
		Map<String, String> header = new TreeMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			header.put(headerName.toUpperCase(), headerValue);
		}

		return header;
	}

	public static Map<String, Object> getParameter(HttpServletRequest request) {
		Map<String, Object> parameters = new TreeMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotBlank(paramName) && paramValue != null) {
				if (StringUtils.isNumeric(paramValue)) {
					parameters.put(paramName, Long.parseLong(paramValue));
				} else if (ArrayUtils.contains(new String[] { "true", "false" }, paramValue.toLowerCase())) {
					parameters.put(paramName, Boolean.parseBoolean(paramValue));
				} else {
					parameters.put(paramName, paramValue);
				}
			}
		}
		return parameters;
	}

	public static String getClientAddr(HttpServletRequest request) {
		String clientIp = request.getHeader("X-Real-IP");
		if (StringUtils.isEmpty(clientIp)) {
			clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}

}
