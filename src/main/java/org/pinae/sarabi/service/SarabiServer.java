package org.pinae.sarabi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.pinae.sarabi.service.filter.ServiceFilter;
import org.pinae.sarabi.service.handler.JettyHandler;
import org.pinae.sarabi.service.security.ServiceSecurity;
import org.pinae.sarabi.service.util.ClassLoaderUtils;
import org.pinae.zazu.service.annotation.Controller;

public class SarabiServer {
	
	private static Logger logger = Logger.getLogger(SarabiServer.class);

	private List<Class<?>> classList = new ArrayList<Class<?>>();

	private List<ServiceFilter> filterList = new ArrayList<ServiceFilter>();
	
	private Properties serverCfg = new Properties();

	public void registerService(Class<?> clazz) {
		this.classList.add(clazz);
	}

	public void registerFilter(ServiceFilter filter) {
		this.filterList.add(filter);
	}

	public SarabiServer() {

	}

	public SarabiServer(File configFile) throws ServerException {
		try {
			this.serverCfg.load(new FileInputStream(configFile));
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

			ServerConnector connector = createConnector(server);

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

	private ServerConnector createConnector(Server server) throws ServerException {
		
		int port = 80;
		long timeout = 30;
		try {
			port = Integer.parseInt(serverCfg.getProperty("server.port", "80"));
			timeout = Long.parseLong(serverCfg.getProperty("server.timeout", "30"));
		} catch (NumberFormatException e) {
			throw new ServerException(e);
		}
		
		String host = serverCfg.getProperty("server.adderss", "0.0.0.0");
		
		logger.info(String.format("Startup Sarabi, listen: %s:%d", host, port));
		logger.info(String.format("Set Sarabi, timeout: %d ms", timeout));
		
		ServerConnector connector = new ServerConnector(server);
		connector.setHost(host);
		connector.setPort(port);
		connector.setIdleTimeout(TimeUnit.SECONDS.toMillis(timeout));

		return connector;
	}

}
