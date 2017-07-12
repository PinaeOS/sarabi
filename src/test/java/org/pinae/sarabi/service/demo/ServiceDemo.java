package org.pinae.sarabi.service.demo;

import java.util.HashMap;
import java.util.Map;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.SarabiServer;
import org.pinae.sarabi.service.ServerConfig;
import org.pinae.sarabi.service.annotation.Body;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.annotation.Field;
import org.pinae.sarabi.service.annotation.Filter;
import org.pinae.sarabi.service.annotation.Header;
import org.pinae.sarabi.service.annotation.Security;
import org.pinae.sarabi.service.annotation.Service;
import org.pinae.sarabi.service.security.HttpBasicAuthFilter;

import com.alibaba.fastjson.JSON;

@Controller
public class ServiceDemo {
	
	@Service(url = "/person/say", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String sayHello(@Field(name = "name") String name) {
		return "Hello " + name;
	}
	
	@Service(url = "/body/read", method = {Http.HTTP_POST}, contentType = Http.TEXT_PLAIN)
	@Security(name = {"HttpBasic"})
	public String readBody(@Body String body, @Field(name = "name") String name, @Header(name = "Content-Type") String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Type:" + type + "\n");
		buffer.append("Name:" + name + "\n");
		buffer.append("body:" + body + "\n");
		return buffer.toString();
	}
	
	@Service(url = "/group/{groupId}/person/{personId}", method = {Http.HTTP_GET}, contentType = Http.APPLICATION_JSON)
	@Filter(name = "CustomFilter")
	public String getPerson(@Field(name = "groupId") String groupId, @Field(name = "personId") String personId) {
		Map<String, String> person = new HashMap<String, String>();
		person.put("group", groupId);
		person.put("person", personId);
		return JSON.toJSONString(person);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, String> authInfo = new HashMap<String, String>();
		authInfo.put("hui", "12345");
		
		ServerConfig serverCfg = new ServerConfig();
		serverCfg.setAddress("0.0.0.0");
		serverCfg.setPort(8080);
		serverCfg.setTimeout(180);
		
		SarabiServer server = new SarabiServer(serverCfg);
		server.registerFilter(new CustomFilter());
		server.registerFilter(new HttpBasicAuthFilter(authInfo));
		server.registerService(ServiceDemo.class);
		server.startup();
	}
	
}
