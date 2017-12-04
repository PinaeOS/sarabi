package org.pinae.sarabi.service.demo;

import java.util.HashMap;
import java.util.Map;

import org.pinae.sarabi.service.SarabiServer;
import org.pinae.sarabi.service.ServerConfig;
import org.pinae.sarabi.service.demo.listener.MyServiceRegisterListener;
import org.pinae.sarabi.service.security.HttpBasicAuthFilter;

public class TestServiceStart {
	public static void main(String[] args) throws Exception {
		Map<String, String> authInfo = new HashMap<String, String>();
		authInfo.put("hui", "12345");
		
		ServerConfig serverCfg = new ServerConfig();
		serverCfg.setAddress("0.0.0.0");
		serverCfg.setPort(8080);
		serverCfg.setTimeout(180);
		
		SarabiServer server = new SarabiServer(serverCfg);
		server.setRegisterListener(new MyServiceRegisterListener());
		server.registerFilter(new CustomFilter());
		server.registerFilter(new HttpBasicAuthFilter(authInfo));
		server.registerService(ServiceDemo.class);
		server.registerService(ServiceDemoSingleton.class);
		server.startup();
	}
}
