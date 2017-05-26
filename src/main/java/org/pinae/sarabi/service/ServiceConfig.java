package org.pinae.sarabi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;
import org.pinae.sarabi.service.filter.ServiceFilter;

public class ServiceConfig {
	
	private String uuid;
	
	private String serviceUrl;
	
	private String serviceMethod[];
	
	private String contentType;
	
	private String charset;
	
	private Class<?> clazz;
	
	private Method method;
	
	private List<Pair<String, Parameter>> params;
	
	private List<ServiceFilter> filters;
	
	public ServiceConfig(String serviceUrl, String serviceMethod[], String contentType, String charset, Class<?> clazz, 
			Method method, List<Pair<String, Parameter>> params, List<ServiceFilter> filters) {
		this.uuid = UUID.randomUUID().toString();
		
		this.serviceUrl = serviceUrl;
		this.serviceMethod = serviceMethod;
		this.contentType = contentType;
		this.charset = charset;
		
		this.clazz = clazz;
		this.method = method;
		this.params = params;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String[] getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String[] serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String mediaType) {
		this.contentType = mediaType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public List<Pair<String, Parameter>> getParams() {
		return params;
	}

	public void setParams(List<Pair<String, Parameter>> params) {
		this.params = params;
	}
	
	public List<ServiceFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<ServiceFilter> filters) {
		this.filters = filters;
	}

	public boolean isMatched(String serviceUrl, String serviceMethod) {
		boolean isMethodMatched = false;
		for (String _method : this.serviceMethod) {
			if (_method.equalsIgnoreCase(serviceMethod)) {
				isMethodMatched = true;
				break;
			}
		}
		return this.serviceUrl.equals(serviceUrl) && isMethodMatched;
	}
	
}
