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
import org.apache.log4j.Logger;
import org.pinae.sarabi.service.annotation.Body;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.annotation.Field;
import org.pinae.sarabi.service.annotation.Filter;
import org.pinae.sarabi.service.annotation.Header;
import org.pinae.sarabi.service.annotation.Register;
import org.pinae.sarabi.service.annotation.Security;
import org.pinae.sarabi.service.annotation.Service;
import org.pinae.sarabi.service.filter.ServiceFilter;
import org.pinae.sarabi.service.listener.RegisterListener;

public class ServiceContainer {

	private static Logger logger = Logger.getLogger(ServiceContainer.class);

	private List<ServiceConfig> serviceCfgList = new ArrayList<ServiceConfig>();

	private Map<String, ServiceFilter> filterMap = new HashMap<String, ServiceFilter>();
	
	private List<RegisterListener> registerListenerList;
	
	public ServiceContainer(List<RegisterListener> registerListenerList) {
		this.registerListenerList = registerListenerList;
	}

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
			registerService(serviceCls, serviceObj);
		}
	}

	public void registerService(Class<?> serviceClass, Object serviceObj) {
		
		Controller controller = serviceClass.getAnnotation(Controller.class);
		String baseUrl = controller.url();

		List<ServiceFilter> classFilterList = new ArrayList<ServiceFilter>();
		if (serviceClass.isAnnotationPresent(Security.class)) {
			Security security = serviceClass.getAnnotation(Security.class);
			classFilterList.addAll(getSecurity(security));
		}

		if (serviceClass.isAnnotationPresent(Filter.class)) {
			Filter filter = serviceClass.getAnnotation(Filter.class);
			classFilterList.addAll(getFilter(filter));
		}
		
		boolean isRegister = false;
		if (serviceClass.isAnnotationPresent(Register.class)) {
			isRegister = true;
		}

		Method methods[] = serviceClass.getDeclaredMethods();
		for (Method method : methods) {

			List<ServiceFilter> methodFilterList = new ArrayList<ServiceFilter>();
			if (method.isAnnotationPresent(Security.class)) {
				Security security = method.getAnnotation(Security.class);
				methodFilterList.addAll(getSecurity(security));
			}

			if (method.isAnnotationPresent(Filter.class)) {
				Filter filter = method.getAnnotation(Filter.class);
				methodFilterList.addAll(getFilter(filter));
			}
			
			if (method.isAnnotationPresent(Register.class)) {
				isRegister = true;
			}

			if (method.isAnnotationPresent(Service.class)) {
				Service service = method.getAnnotation(Service.class);

				String serviceUrl = service.url();
				if (StringUtils.isNotBlank(baseUrl)) {
					serviceUrl = baseUrl + serviceUrl;
				}
				
				String serviceMethod[] = service.method();
				if (serviceMethod == null) {
					serviceMethod = new String[] { Http.HTTP_GET };
				}
				String contentType = service.contentType();
				String charset = service.charset();
				
				if (this.registerListenerList != null && this.registerListenerList.size() > 0) {
					for (RegisterListener registerListener : this.registerListenerList) {
						String serviceName = service.name();
						if (StringUtils.isBlank(serviceName)) {
							serviceName = serviceUrl;
						}
						String serviceDesc = service.description();
						
						registerListener.register(isRegister, serviceName, serviceDesc, serviceUrl, serviceMethod);
					}
				}

				logger.info(String.format("Register Service: class=%s, method=%s, request-url=%s, http-method=%s, content-type=%s, charset=%s",
						serviceClass.getName(), method.getName(), serviceUrl, StringUtils.join(serviceMethod, ","), contentType, charset));

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
					List<ServiceFilter> srvFilterList = new ArrayList<ServiceFilter>();
					srvFilterList.addAll(classFilterList);
					srvFilterList.addAll(methodFilterList);

					for (ServiceFilter srvFilter : srvFilterList) {
						logger.info(String.format("Add Service Filter: %s to %s", srvFilter.getClass().getName(), serviceClass.getName()));
					}

					this.serviceCfgList
							.add(new ServiceConfig(serviceUrl, serviceMethod, contentType, charset, 
									serviceClass, serviceObj, method, paramList, srvFilterList));
				}
			}
		}
	}

	private List<ServiceFilter> getSecurity(Security security) {

		List<ServiceFilter> filterList = new ArrayList<ServiceFilter>();

		String[] filterNames = security.name();
		for (String filterName : filterNames) {
			ServiceFilter srvFilter = filterMap.get(filterName);
			if (srvFilter != null) {
				filterList.add(srvFilter);
			}
		}

		return filterList;
	}

	private List<ServiceFilter> getFilter(Filter filter) {

		List<ServiceFilter> filterList = new ArrayList<ServiceFilter>();

		String[] filterNames = filter.name();
		for (String filterName : filterNames) {
			ServiceFilter srvFilter = filterMap.get(filterName);
			if (srvFilter != null) {
				filterList.add(srvFilter);
			}
		}

		return filterList;
	}

	public ServiceConfig getService(String requestUrl, String requestMethod) {
		for (ServiceConfig serviceCfg : this.serviceCfgList) {
			if (serviceCfg.isMatched(requestUrl, requestMethod)) {
				return serviceCfg;
			}
		}
		return null;
	}

	public void registerFilter(Class<?> filterClass) throws ServiceException {
		try {
			Object filterObj = filterClass.newInstance();
			if (filterObj instanceof ServiceFilter) {
				registerFilter((ServiceFilter) filterObj);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void registerFilter(ServiceFilter filter) {
		if (filter != null) {
			String filterName = filter.getName();
			if (StringUtils.isBlank(filterName)) {
				filterName = filter.getClass().getName();
			}
			this.filterMap.put(filterName, filter);
		} else {
			throw new NullPointerException("ServiceFilter bean is Null");
		}
	}

	public ServiceFilter getFilter(String name) {
		return this.filterMap.get(name);
	}

}
