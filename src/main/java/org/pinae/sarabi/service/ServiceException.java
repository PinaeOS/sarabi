package org.pinae.sarabi.service;

/**
 * 服务执行异常
 * 
 * @author Huiyugeng
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -6089068431679048125L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable e) {
		super(e);
	}

	public ServiceException(String requestId, Throwable e) {
		super(e);
	}

}
