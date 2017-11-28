package org.pinae.sarabi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class ServerConfig {
	
	public static final int KB = 1024;
	
	private String name = "sarabi";
	
	private String version = "1.0";
	
	private String address = "0.0.0.0";
	
	private int port = 80;
	
	private long timeout = TimeUnit.SECONDS.toMillis(60);
	
	private int outputBufferSize = 32 * KB;
	
	private int requestHeaderSize = 256 * KB;
	
	private int responseHeaderSize = 256 * KB;
	
	private Properties serverProp;
	
	public ServerConfig() {
		
	}
	
	public ServerConfig(Properties serverProp) {
		if (serverProp != null) {
			this.serverProp = serverProp;
			
			this.name = getString("server.name", "sarabi");
			this.version = getString("server.version", "1.0");
		
			this.address = getString("server.address", "0.0.0.0");
			this.port = getInteger("server.port", 80);
		
			this.timeout = TimeUnit.SECONDS.toMillis(getLong("server.timeout", 60));
		
			this.outputBufferSize = getInteger("server.output-buffer-size", 32) * KB;
			this.requestHeaderSize = getInteger("server.request-header-size", 256) * KB;
			this.responseHeaderSize = getInteger("server.response-header-size", 256) * KB;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public int getOutputBufferSize() {
		return outputBufferSize;
	}

	public void setOutputBufferSize(int outputBufferSize) {
		this.outputBufferSize = outputBufferSize;
	}

	public int getRequestHeaderSize() {
		return requestHeaderSize;
	}

	public void setRequestHeaderSize(int requestHeaderSize) {
		this.requestHeaderSize = requestHeaderSize;
	}

	public int getResponseHeaderSize() {
		return responseHeaderSize;
	}

	public void setResponseHeaderSize(int responseHeaderSize) {
		this.responseHeaderSize = responseHeaderSize;
	}
	
	private String getString(String key, String defaultValue) {
		return this.serverProp.getProperty(key, defaultValue);
	}
	
	private int getInteger(String key, int defaultValue) {
		int val = 0;
		try {
			val = Integer.parseInt(this.serverProp.getProperty(key));
		} catch (Exception e) {
			val = defaultValue;
		}
		return val;
	}
	
	private long getLong(String key, long defaultValue) {
		long val = 0;
		try {
			val = Long.parseLong(this.serverProp.getProperty(key));
		} catch (Exception e) {
			val = defaultValue;
		}
		return val;
	}
	
	public ServerConnector getConnector(Server server) {
		ServerConnector connector = new ServerConnector(server);
		connector.setHost(this.address);
		connector.setPort(this.port);
		connector.setIdleTimeout(TimeUnit.SECONDS.toMillis(this.timeout));
		connector.setConnectionFactories(getFactories());
		
		return connector;
	}
	
	private Collection<ConnectionFactory> getFactories() {
		List<ConnectionFactory> factories = new ArrayList<ConnectionFactory>();
		factories.add(new HttpConnectionFactory(getHttpConfig()));
		return factories;
	}
	
	private HttpConfiguration getHttpConfig() {
		HttpConfiguration httpConfig = new HttpConfiguration();
		httpConfig.setOutputBufferSize(this.outputBufferSize);
		httpConfig.setRequestHeaderSize(this.requestHeaderSize);
		httpConfig.setResponseHeaderSize(this.responseHeaderSize);
		return httpConfig;
	}
	
}
