package org.pinae.sarabi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.pinae.zazu.service.annotation.Body;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Header;
import org.pinae.zazu.service.annotation.Service;

public class ServiceContainer {

	private List<ServiceConfig> serviceCfgList = new ArrayList<ServiceConfig>();

	public void register(Class<?> clazz) {
		Method methods[] = clazz.getDeclaredMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Service.class)) {
				Service service = method.getAnnotation(Service.class);

				String serviceUrl = service.url();
				String serviceMethod[] = service.method();
				if (serviceMethod == null) {
					serviceMethod = new String[] { Http.HTTP_GET };
				}
				String contentType = service.contentType();
				String charset = service.charset();

				Parameter params[] = method.getParameters();

				List<Pair<String, Parameter>> paramList = new ArrayList<Pair<String, Parameter>>();

				for (Parameter param : params) {
					Field field = param.getAnnotation(Field.class);
					if (field != null) {
						String name = field.name();
						if (StringUtils.isNotBlank(name)) {
							Pair<String, Parameter> paramRef = new ImmutablePair<String, Parameter>(name, param);
							paramList.add(paramRef);
						}
					}
					
					Header header = param.getAnnotation(Header.class);
					if (header != null) {
						String name = header.name();
						if (StringUtils.isNotBlank(name)) {
							name = "@header." + name;
							Pair<String, Parameter> paramRef = new ImmutablePair<String, Parameter>(name, param);
							paramList.add(paramRef);
						}
					}
					
					Body body = param.getAnnotation(Body.class);
					if (body != null) {
						paramList.add(new ImmutablePair<String, Parameter>("@body", param));
					}
				}

				if (StringUtils.isNotBlank(serviceUrl)) {
					this.serviceCfgList.add(new ServiceConfig(serviceUrl, serviceMethod, contentType, charset, clazz, method, paramList));
				}
			}
		}
	}

	public ServiceConfig getService(String serviceUrl, String serviceMethod) {
		for (ServiceConfig serviceCfg : this.serviceCfgList) {
			if (serviceCfg.isMatched(serviceUrl, serviceMethod)) {
				return serviceCfg;
			}
		}
		return null;
	}

}
