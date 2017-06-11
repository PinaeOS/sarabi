package org.pinae.sarabi.service.demo;

import java.util.HashMap;
import java.util.Map;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.SarabiServer;
import org.pinae.sarabi.service.filter.HttpBasicFilter;
import org.pinae.zazu.service.annotation.Body;
import org.pinae.zazu.service.annotation.Controller;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Header;
import org.pinae.zazu.service.annotation.Service;

@Controller
public class ServiceDemo {
	
	@Service(url = "/person/say", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String sayHello(@Field(name = "name") String name) {
		return "Hello " + name;
	}
	
	@Service(url = "/body/read", method = {Http.HTTP_POST}, contentType = Http.TEXT_PLAIN, filter = {"HttpBasicFilter"})
	public String readBody(@Body String body, @Field(name = "name") String name, @Header(name = "Content-Type") String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Type:" + type + "\n");
		buffer.append("Name:" + name + "\n");
		buffer.append("body:" + body + "\n");
		return buffer.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> authInfo = new HashMap<String, String>();
		authInfo.put("hui", "12345");
		
		SarabiServer server = new SarabiServer();
		server.registerFilter(new HttpBasicFilter(authInfo));
		server.registerService(ServiceDemo.class);
		server.startup();
	}
	
}
