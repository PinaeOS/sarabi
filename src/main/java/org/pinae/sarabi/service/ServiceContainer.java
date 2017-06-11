package org.pinae.sarabi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.pinae.sarabi.service.filter.ServiceFilter;
import org.pinae.zazu.service.annotation.Body;
import org.pinae.zazu.service.annotation.Controller;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Header;
import org.pinae.zazu.service.annotation.Service;

public class ServiceContainer {

	private List<ServiceConfig> serviceCfgList = new ArrayList<ServiceConfig>();
	
	private Map<String, ServiceFilter> filterMap = new HashMap<String, ServiceFilter>();
	
	public void registerService(String className) throws ServiceException {
		try {
			registerService(Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new ServiceException(e);
		}
	}
	
	public void registerService(Object serviceObj) {
		Class<?> serviceCls = serviceObj.getClass();
		if (serviceCls.isAnnotationPresent(Controller.class)) {
			registerService(serviceCls);
		}
	}

	public void registerService(Class<?> clazz) {
		
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
				
				String[] filterNames = service.filter();
				List<ServiceFilter> filterList = new ArrayList<ServiceFilter>();
				for (String filterName : filterNames) {
					ServiceFilter filter = filterMap.get(filterName);
					if (filter != null) {
						filterList.add(filter);
					}
				}

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
					this.serviceCfgList.add(new ServiceConfig(serviceUrl, serviceMethod, contentType, charset, 
							clazz, method, paramList, filterList));
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
	
	public void registerFilter(String className) throws ServiceException {
		try {
			Class<?> filterClass = Class.forName(className);
			Object filterObj = filterClass.newInstance();
			if (filterObj instanceof ServiceFilter) {
				registerFilter((ServiceFilter)filterObj);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public void registerFilter(ServiceFilter filter) {
		if (filter != null) {
			String filterName = filter.getName();
			if (StringUtils.isNotBlank(filterName)) {
				this.filterMap.put(filterName, filter);
			} else {
				throw new NullPointerException("ServiceFilter name is Null");
			}
		} else {
			throw new NullPointerException("ServiceFilter bean is Null");
		}
	}

	public ServiceFilter getFilter(String name) {
		return this.filterMap.get(name);
	}

}
