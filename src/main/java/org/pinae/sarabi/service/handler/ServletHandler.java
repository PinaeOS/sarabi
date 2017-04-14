package org.pinae.sarabi.service.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pinae.sarabi.service.ServiceConfig;
import org.pinae.sarabi.service.ServiceContainer;

public class ServletHandler extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private ServiceContainer container;
	
	public ServletHandler(ServiceContainer container) {
		this.container = container;
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
			
		}
	}
}
