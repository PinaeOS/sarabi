package org.pinae.sarabi.service.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.SarabiServer;
import org.pinae.sarabi.service.security.HttpBasicAuthFilter;
import org.pinae.sarabi.service.util.FileUtils;
import org.pinae.zazu.service.annotation.Body;
import org.pinae.zazu.service.annotation.Controller;
import org.pinae.zazu.service.annotation.Field;
import org.pinae.zazu.service.annotation.Filter;
import org.pinae.zazu.service.annotation.Header;
import org.pinae.zazu.service.annotation.Security;
import org.pinae.zazu.service.annotation.Service;

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
		
		File serverProp = FileUtils.getFile("server.properties");
		
		SarabiServer server = new SarabiServer(serverProp);
		server.registerFilter(new CustomFilter());
		server.registerFilter(new HttpBasicAuthFilter(authInfo));
		server.registerService(ServiceDemo.class);
		server.startup();
	}
	
}
