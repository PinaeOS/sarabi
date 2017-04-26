package org.pinae.sarabi.service;

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

public class SarabiServer {
	
	private static Logger logger = Logger.getLogger(SarabiServer.class);
	
	public static void main(String args[]) {
		SarabiServer server = new SarabiServer();
		try {
			server.startup(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startup(String args[]) throws Exception {
		
		ServiceContainer container = new ServiceContainer();
		container.register(org.pinae.sarabi.service.ServiceDemo.class);
		
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