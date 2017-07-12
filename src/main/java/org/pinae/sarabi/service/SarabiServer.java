package org.pinae.sarabi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.filter.ServiceFilter;
import org.pinae.sarabi.service.handler.JettyHandler;
import org.pinae.sarabi.service.security.ServiceSecurity;
import org.pinae.sarabi.service.util.ClassLoaderUtils;

public class SarabiServer {
	
	private static Logger logger = Logger.getLogger(SarabiServer.class);

	private List<Class<?>> classList = new ArrayList<Class<?>>();
	
	private List<ServiceFilter> filterList = new ArrayList<ServiceFilter>();
	
	private ServerConfig serverCfg;

	public void registerService(Class<?> clazz) {
		this.classList.add(clazz);
	}

	public void registerFilter(ServiceFilter filter) {
		this.filterList.add(filter);
	}

	public SarabiServer() {

	}
	
	public SarabiServer(ServerConfig serverCfg) {
		this.serverCfg = serverCfg;
	}

	public SarabiServer(File configFile) throws ServerException {
		try {
			Properties serverProp = new Properties();
			serverProp.load(new FileInputStream(configFile));
			this.serverCfg = new ServerConfig(serverProp);
		} catch (IOException e) {
			throw new ServerException(e);
		}
	}

	public void startup() throws ServerException {
		
		long startTime = System.currentTimeMillis();
		
		ServiceContainer container = new ServiceContainer();

		for (ServiceFilter filter : this.filterList) {
			container.registerFilter(filter);
		}

		try {
			if (this.classList.size() == 0) {
				this.classList = ClassLoaderUtils.loadClass();
			}
			
			for (Class<?> clazz : this.classList) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					container.registerService(clazz);
				}
				try {
					if (clazz.asSubclass(ServiceFilter.class) != null || clazz.asSubclass(ServiceSecurity.class) != null) {
						container.registerFilter(clazz);
					}
				} catch (ClassCastException e) {
					
				}
			}
			
			Server server = new Server();

			ServerConnector connector = createConnector(server, this.serverCfg);

			server.addConnector(connector);
			server.setHandler(new JettyHandler(this.serverCfg, container));

			server.start();
			
			long usedTime = System.currentTimeMillis() - startTime;
			logger.info(String.format("Startup Sarabi Finish, used: %d ms", usedTime));
			
			server.join();
		} catch (Exception e) {
			throw new ServerException(e);
		}

	}

	private ServerConnector createConnector(Server server, ServerConfig serverCfg) throws ServerException {

		logger.info(String.format("Startup Sarabi, listen: %s:%d", serverCfg.getAddress(), serverCfg.getPort()));
		logger.info(String.format("Set Timeout: %d ms", serverCfg.getTimeout()));
		logger.info(String.format("Set Output Buffer Size: %d KB", serverCfg.getOutputBufferSize() / ServerConfig.KB));
		logger.info(String.format("Set Request Header Size: %d KB", serverCfg.getRequestHeaderSize() / ServerConfig.KB));
		logger.info(String.format("Set Response Header Size: %d KB", serverCfg.getResponseHeaderSize() / ServerConfig.KB));
		
		ServerConnector connector = serverCfg.getConnector(server);
		
		return connector;
	}
	

}
