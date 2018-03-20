package org.pinae.sarabi.service.demo;

import java.util.HashMap;
import java.util.Map;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.annotation.Body;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.annotation.Field;
import org.pinae.sarabi.service.annotation.Filter;
import org.pinae.sarabi.service.annotation.Header;
import org.pinae.sarabi.service.annotation.Security;
import org.pinae.sarabi.service.annotation.Service;

import com.alibaba.fastjson.JSON;

@Controller
public class ServiceDemo {
	
	@Service(name="sayHello", url = "/person/say", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String sayHello(@Field(name = "name") String name) {
		return "Hello " + name;
	}
	
	@Service(name="readBody", url = "/body/read", method = {Http.HTTP_POST}, contentType = Http.TEXT_PLAIN)
	@Security(name = {"HttpBasic"})
	public String readBody(@Body String body, @Field(name = "name") String name, @Header(name = "Content-Type") String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Type:" + type + "\n");
		buffer.append("Name:" + name + "\n");
		buffer.append("body:" + body + "\n");
		return buffer.toString();
	}
	
	@Service(name="getPersonInGroup", url = "/group/{groupId}/person/{personId}", method = {Http.HTTP_GET}, contentType = Http.APPLICATION_JSON)
	@Filter(name = "CustomFilter")
	public String getPerson(@Field(name = "groupId") String groupId, @Field(name = "personId") String personId) {
		Map<String, String> person = new HashMap<String, String>();
		person.put("group", groupId);
		person.put("person", personId);
		return JSON.toJSONString(person);
	}
	
	@Service(name="getHttpRequest", url = "/get/request", method = {Http.HTTP_GET}, contentType = Http.APPLICATION_JSON)
	public String getRequest(@Field(name = "@query") Map<String, Object> query, @Field(name = "@header") Map<String, String> header) {
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("query", query);
		request.put("header", header);
		
		return JSON.toJSONString(request);
	}
	
	@Service(name="sayChinese", url = "/person/say/chinese", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String sayChinese(@Field(name = "name") String name) {
		return "你好, " + name;
	}
	
}
