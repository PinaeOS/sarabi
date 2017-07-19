package org.pinae.sarabi.service;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.filter.ServiceFilter;
import org.pinae.sarabi.service.listener.ContextListener;
import org.pinae.sarabi.service.listener.RequestListener;
import org.pinae.sarabi.service.util.RequestUtils;

public class ServiceExecutor {

	private Map<String, Object> serviceObjectMap = new ConcurrentHashMap<String, Object>();

	private Object getServiceObject(Class<?> srvCls) throws InstantiationException, IllegalAccessException {
		Object srvObj = null;
		Controller controller = srvCls.getAnnotation(Controller.class);
		if (controller.singleton()) {
			srvObj = serviceObjectMap.get(srvCls.getName());
			if (srvObj == null) {
				srvObj = srvCls.newInstance();
				serviceObjectMap.put(srvCls.getName(), srvObj);
			}
		} else {
			srvObj = srvCls.newInstance();
		}
		return srvObj;
	}

	public ServiceResponse execute(ServiceConfig srvCfg, HttpServletRequest request) throws ServiceException {

		List<ServiceFilter> filterList = srvCfg.getFilters();
		if (filterList != null) {
			for (ServiceFilter filter : filterList) {
				if (filter != null) {
					ServiceResponse response = filter.filter(request);
					if (response.getStatus() != Http.HTTP_OK) {
						return response;
					}
				}
			}
		}

		Class<?> srvCls = srvCfg.getClazz();
		try {
			Object srvObj = getServiceObject(srvCls);

			if (srvObj instanceof RequestListener) {
				((RequestListener) srvObj).setRequest(request);
			}

			if (srvObj instanceof ContextListener) {
				((ContextListener) srvObj).handleRequest(request);
			}

			Method srvMethod = srvCfg.getMethod();

			List<Object> argList = new ArrayList<Object>();

			Map<String, String> uriParams = RequestUtils.getUrlParameter(srvCfg.getServiceUrl(), request.getRequestURI());
			Map<String, Object> queryParams = RequestUtils.getParameter(request);
			Map<String, String> httpHeaders = RequestUtils.getHeader(request);

			List<Pair<String, Parameter>> srvParams = srvCfg.getParams();
			for (Pair<String, Parameter> srvParam : srvParams) {
				String key = srvParam.getKey();
				if ("@body".equals(key)) {
					BufferedReader br = request.getReader();
					StringBuffer strBuffer = new StringBuffer();

					String str = null;
					while ((str = br.readLine()) != null) {
						strBuffer.append(str);
					}
					argList.add(strBuffer.toString());
				} else if ("@url".equals(key)) {
					argList.add(uriParams);
				} else if ("@query".equals(key) || "@parameter".equals(key)) {
					argList.add(queryParams);
				} else if ("@header".equals(key)) {
					argList.add(httpHeaders);
				} else {
					Object value = null;
					if (key.startsWith("@header")) {
						key = StringUtils.substringAfter(key, "@header.").toUpperCase();
						value = httpHeaders.get(key);
					} else {
						if (uriParams.containsKey(key)) {
							value = uriParams.get(key);
						} else {
							value = queryParams.get(key);
						}
					}

					if (value != null) {
						argList.add(value);
					} else {
						argList.add(null);
					}
				}
			}

			Object args[] = null;
			if (argList.size() > 0) {
				args = new Object[argList.size()];
				for (int i = 0; i < argList.size(); i++) {
					args[i] = argList.get(i);
				}
			}

			ServiceResponse response = null;

			Object result = null;
			try {
				result = srvMethod.invoke(srvObj, args);
			} catch (IllegalArgumentException e1) {
				response = new ServiceResponse(Http.HTTP_BAD_REQUEST, "Illegal request argument:" + e1.getMessage());
			} catch (IllegalAccessException e2) {
				response = new ServiceResponse(Http.HTTP_INTERNAL_SERVER_ERROR, "Illegal access method: " + srvMethod.getName());
			}

			if (response == null) {
				if (result instanceof ServiceResponse) {
					if (result != null) {
						response = (ServiceResponse) result;
					}
				} else {
					response = new ServiceResponse(srvCfg.getContentType(), srvCfg.getCharset());
					response.setStatus(Http.HTTP_OK);
					if (result != null) {
						response.setContent(result);
					}
				}
			}

			if (srvObj instanceof ContextListener) {
				((ContextListener) srvObj).handleResponse(response);
			}

			return response;

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
