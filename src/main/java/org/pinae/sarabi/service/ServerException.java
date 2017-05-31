package org.pinae.sarabi.service;

/**
 * 服务执行异常
 * 
 * @author Huiyugeng
 *
 */
public class ServerException extends Exception {

	private static final long serialVersionUID = -6089068431679048125L;

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(Throwable e) {
		super(e);
	}

	public ServerException(String requestId, Throwable e) {
		super(e);
	}

}
