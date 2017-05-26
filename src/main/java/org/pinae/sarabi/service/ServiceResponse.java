package org.pinae.sarabi.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 服务执行响应
 * 
 * @author Huiyugeng
 *
 */
public class ServiceResponse implements Serializable, Cloneable {

	private static final long serialVersionUID = 7823824400452408256L;

	/* 响应时间戳 */
	private long timestamp = System.currentTimeMillis();

	/* 响应状态 */
	private int status = Http.HTTP_NONE;

	/* 响应内容类型 */
	private String contentType = Http.TEXT_PLAIN;

	/* 响应内容字符集 */
	private String charset = "UTF-8";

	/* 响应头部 */
	private Map<String, String> headers = new HashMap<String, String>();

	/* 响应内容 */
	private Object content;

	public ServiceResponse() {

	}
	
	public ServiceResponse(int status) {
		this.status = status;
	}
	
	public ServiceResponse(int status, Object content) {
		this.status = status;
		this.content = content;
	}

	public ServiceResponse(String contentType) {
		this.contentType = contentType;
	}

	public ServiceResponse(String contentType, String charset) {
		this.contentType = contentType;
		this.charset = charset;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public static ServiceResponse getExceptionResponse(int status, String content) {
		ServiceResponse response = new ServiceResponse(Http.TEXT_PLAIN);
		response.setStatus(status);
		response.setContent(content);
		return response;
	}

}
