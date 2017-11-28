package org.pinae.sarabi.service.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServerConfig;
import org.pinae.sarabi.service.ServiceConfig;
import org.pinae.sarabi.service.ServiceContainer;
import org.pinae.sarabi.service.ServiceException;
import org.pinae.sarabi.service.ServiceExecutor;
import org.pinae.sarabi.service.ServiceOutputor;
import org.pinae.sarabi.service.ServiceResponse;
import org.pinae.sarabi.service.util.ResponseUtils;

public class JettyHandler extends AbstractHandler {
	
	private ServiceContainer container;
	private ServiceExecutor executor;
	private ServiceOutputor outputor;
	
	public JettyHandler(final ServerConfig serverCfg, final ServiceContainer container) {
		this.container = container;
		
		this.executor = new ServiceExecutor();
		this.outputor = new ServiceOutputor(serverCfg);
	}
	
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		String uri = request.getRequestURI();
		String method = request.getMethod();
		
		ServiceConfig srvCfg = this.container.getService(uri, method);
		if (srvCfg != null) {
			ServiceResponse srvResponse = null;
			try {
				srvResponse = this.executor.execute(srvCfg, request);
				this.outputor.output(request, response, srvResponse);
			} catch (ServiceException e) {
				ResponseUtils.output(response, Http.HTTP_INTERNAL_SERVER_ERROR, e.getMessage());
			}
		}
	}
	
}
