package org.pinae.sarabi.service.demo;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.annotation.Field;
import org.pinae.sarabi.service.annotation.Service;

@Controller(singleton = true, url="/name")
public class ParentPathDemo {
	
	private String name;
	
	@Service(name="get", url = "/get", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String getName() {
		return "Name: " + this.name;
	}
	
	@Service(name="set", url = "/set", method = {Http.HTTP_POST}, contentType = Http.TEXT_PLAIN)
	public String setName(@Field(name = "name") String name) {
		this.name = name;
		return "Set name successfu;";
	}
	
}
