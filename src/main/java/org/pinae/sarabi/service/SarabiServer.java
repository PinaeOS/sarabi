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
import org.pinae.sarabi.service.listener.RegisterListener;
import org.pinae.sarabi.service.security.ServiceSecurity;
import org.pinae.sarabi.service.util.ClassLoaderUtils;

public class SarabiServer {
	
	private static Logger logger = Logger.getLogger(SarabiServer.class);

	private List<Class<?>> serviceClassList = new ArrayList<Class<?>>();
	
	private List<Object> serviceObjectList = new ArrayList<Object>();
	
	private List<ServiceFilter> serviceFilterList = new ArrayList<ServiceFilter>();
	
	private RegisterListener registerListener;
	
	private ServerConfig serverCfg;

	public void registerService(Class<?> clazz) {
		this.serviceClassList.add(clazz);
	}
	
	public void registerService(Object object) {
		this.serviceObjectList.add(object);
	}

	public void registerFilter(ServiceFilter filter) {
		this.serviceFilterList.add(filter);
	}
	
	public void setRegisterListener(RegisterListener registerListener) {
		this.registerListener = registerListener;
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
		
		ServiceContainer container = new ServiceContainer(this.registerListener);

		for (ServiceFilter filter : this.serviceFilterList) {
			container.registerFilter(filter);
		}

		try {
			if (this.serviceClassList.size() == 0 && this.serviceObjectList.size() == 0) {
				this.serviceClassList = ClassLoaderUtils.loadClass();
			}
			
			for (Class<?> srvClass : this.serviceClassList) {
				if (srvClass.isAnnotationPresent(Controller.class)) {
					container.registerService(srvClass, null);
				}
				try {
					if (srvClass.asSubclass(ServiceFilter.class) != null || srvClass.asSubclass(ServiceSecurity.class) != null) {
						container.registerFilter(srvClass);
					}
				} catch (ClassCastException e) {
					
				}
			}
			
			for (Object srvObj : this.serviceObjectList) {
				container.registerService(srvObj);
				Class<?> srvClass = srvObj.getClass();
				try {
					if (srvClass.asSubclass(ServiceFilter.class) != null || srvClass.asSubclass(ServiceSecurity.class) != null) {
						container.registerFilter(srvClass);
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
