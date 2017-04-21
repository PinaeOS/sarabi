package org.pinae.sarabi.service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;

public class ServiceConfig {
	
	private String uuid;
	
	private String serviceUrl;
	
	private String serviceMethod[];
	
	private String mediaType;
	
	private Class<?> clazz;
	
	private Method method;
	
	private List<Pair<String, Parameter>> params;
	
	public ServiceConfig(String serviceUrl, String serviceMethod[], String mediaType, Class<?> clazz, Method method, List<Pair<String, Parameter>> params) {
		this.uuid = UUID.randomUUID().toString();
		
		this.serviceUrl = serviceUrl;
		this.serviceMethod = serviceMethod;
		this.mediaType = mediaType;
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

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
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
