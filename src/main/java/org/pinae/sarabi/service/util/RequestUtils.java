package org.pinae.sarabi.service.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static Map<String, String> getUrlParameter(String serviceUrl, String requestUrl) {
		
		Map<String, String> urlParams = new HashMap<String, String>();
		
		if (serviceUrl.matches(".*\\{\\w+\\}.*")) {
			
			List<String> pathParamNameList = new ArrayList<String>();
			String srvUrlRegex = serviceUrl.replaceAll("\\/", "\\\\/").replaceAll("\\{\\w+\\}", "(\\\\w+|\\\\{\\\\w+\\\\})");
			Pattern srvPattern = Pattern.compile("^" + srvUrlRegex + "$");
			
			Matcher srvMatcher = srvPattern.matcher(serviceUrl);
			if (srvMatcher.find()) {
				for(int j = 0; j <= srvMatcher.groupCount(); j++) {
					String paramName = srvMatcher.group(j);
					pathParamNameList.add(paramName);
				}
			}
			
			Matcher reqMatcher = srvPattern.matcher(requestUrl);
			if (reqMatcher.find()) {
				for(int j = 0; j <= reqMatcher.groupCount(); j++) {
					if (j <= pathParamNameList.size()) {
						String paramName = pathParamNameList.get(j);
						String paramsVal = reqMatcher.group(j);
						if (StringUtils.isNotBlank(paramName) && StringUtils.isNotBlank(paramsVal)) {
							paramName = StringUtils.substringBetween(paramName, "{", "}");
							urlParams.put(paramName, paramsVal);
						}
					}
				}
			}
			
			if (urlParams.size() == pathParamNameList.size()) {
				
			}
		}
		
		return urlParams;
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
