package org.pinae.sarabi.service.demo;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.SarabiServer;
import org.pinae.zazu.service.annotation.Controller;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Service;

@Controller
public class ServiceDemo {
	
	@Service(url = "/api/publish", method = {Http.HTTP_GET}, contentType = Http.APPLICATION_JSON)
	public String sayHello(@Field(name = "name") String name) {
		return "Hello " + name;
	}
	
	public static void main(String[] args) throws Exception {
		SarabiServer server = new SarabiServer();
		server.addService(ServiceDemo.class);
		server.startup(args);
	}
	
}
