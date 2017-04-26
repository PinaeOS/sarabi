package org.pinae.sarabi.service;

import org.pinae.zazu.service.annotation.Controller;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Service;

@Controller
public class ServiceDemo {
	
	@Service(url = "/api/publish", method = {Http.HTTP_GET}, contentType = Http.APPLICATION_JSON)
	public String sayHello(@Field(name = "name") String name) {
		return "Hello " + name;
	}
	
}
