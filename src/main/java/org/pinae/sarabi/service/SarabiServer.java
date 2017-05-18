package org.pinae.sarabi.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.pinae.sarabi.service.handler.JettyHandler;
import org.pinae.sarabi.service.utils.ClassLoaderUtils;
import org.pinae.zazu.service.annotation.Controller;

public class SarabiServer {
	
	private List<Class<?>> classList = new ArrayList<Class<?>>();
	
	private static Logger logger = Logger.getLogger(SarabiServer.class);
	
	public void register(Class<?> clazz) {
		this.classList.add(clazz);
	}
	
	public void startup(String args[]) throws Exception {
		ServiceContainer container = new ServiceContainer();
				
		try {
			if (this.classList.size() == 0) {
				this.classList = ClassLoaderUtils.loadClass();
			}
			for (Class<?> clazz : this.classList) {
				if (clazz.isAnnotationPresent(Controller.class)) {
					container.register(clazz);
				}
			}
		} catch (IOException e) {
			
		}

		Server server = new Server();
		
		ServerConnector connector = createConnector(server);
		
		server.addConnector(connector);
		server.setHandler(new JettyHandler(container));

		server.start();
		server.join();

	}
	
	private ServerConnector createConnector(Server server) {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(80);
		connector.setIdleTimeout(TimeUnit.SECONDS.toMillis(60));

		return connector;
	}
	
	private static CommandLine parse(String args[]) throws ParseException {
		Options options = new Options();
		options.addOption("f", "file", true, "Pumbaa Server config file");

		CommandLineParser parser = new DefaultParser();
		return parser.parse(options, args);
	}
}
