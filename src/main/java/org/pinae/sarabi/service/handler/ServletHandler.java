package org.pinae.sarabi.service.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.ServerConfig;
import org.pinae.sarabi.service.ServiceConfig;
import org.pinae.sarabi.service.ServiceContainer;
import org.pinae.sarabi.service.ServiceException;
import org.pinae.sarabi.service.ServiceExecutor;
import org.pinae.sarabi.service.ServiceOutputor;
import org.pinae.sarabi.service.ServiceResponse;
import org.pinae.sarabi.service.util.ResponseUtils;

public class ServletHandler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ServiceContainer container;
	private ServiceExecutor executor;
	private ServiceOutputor outputor;
	
	public ServletHandler(final ServerConfig serverCfg, final ServiceContainer container) {
		this.container = container;
		
		this.executor = new ServiceExecutor();
		this.outputor = new ServiceOutputor(serverCfg);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
		execute(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		execute(request, response);
	}
	
	public void doHead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		execute(request, response);
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		execute(request, response);
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		execute(request, response);
	}
	
	private void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		String uri = request.getRequestURI();
		String method = request.getMethod();
		
		ServiceConfig srvCfg = this.container.getService(uri, method);
		if (srvCfg != null) {
			ServiceResponse srvResponse = null;
			try {
				srvResponse = this.executor.execute(srvCfg, request);
			} catch (ServiceException e) {
				ResponseUtils.output(response, Http.HTTP_INTERNAL_SERVER_ERROR, e.getMessage());
			}
			this.outputor.output(request, response, srvResponse);
		} else {
			ResponseUtils.output(response, Http.HTTP_NOT_FOUND, String.format("Service NOT Found: %s:%s ", uri, method));
		}
	}
}
