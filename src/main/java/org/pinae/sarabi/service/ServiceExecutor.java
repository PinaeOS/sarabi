package org.pinae.sarabi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.tuple.Pair;
import org.pinae.sarabi.service.utils.RequestUtils;

public class ServiceExecutor {

	public ServiceResponse execute(ServiceConfig srvCfg, HttpServletRequest request) throws ServiceException {

		Class<?> srvCls = srvCfg.getClazz();
		try {
			Object srvObj = srvCls.newInstance();
			Method srvMethod = srvCfg.getMethod();

			List<Object> argList = new ArrayList<Object>();

			Map<String, Object> httpParams = RequestUtils.getParameter(request);
			Map<String, String> httpHeaders = RequestUtils.getHeader(request);

			List<Pair<String, Parameter>> srvParams = srvCfg.getParams();
			for (Pair<String, Parameter> srvParam : srvParams) {
				String key = srvParam.getKey();
				Object value = httpParams.get(key);
				if (value == null) {
					value = httpHeaders.get(key);
				}

				if (value != null) {
					argList.add(value);
				} else {
					argList.add(null);
				}
			}

			Object args[] = null;
			if (argList.size() > 0) {
				args = new Object[argList.size()];
				for (int i = 0; i < argList.size(); i++) {
					args[i] = argList.get(i);
				}
			}

			Object result = srvMethod.invoke(srvObj, args);
			if (result != null) {
				if (result instanceof ServiceResponse) {
					return (ServiceResponse)result;
				} else {
					ServiceResponse response = new ServiceResponse(srvCfg.getContentType(), srvCfg.getCharset());
					response.setStatus(Http.HTTP_OK);
					if (result != null) {
						response.setContent(result);
					}
					return response;
				}
			}
			return null;
			
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	


}
