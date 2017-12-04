package org.pinae.sarabi.service.demo;

import java.util.Date;

import org.pinae.sarabi.service.Http;
import org.pinae.sarabi.service.annotation.Controller;
import org.pinae.sarabi.service.annotation.Field;
import org.pinae.sarabi.service.annotation.Service;

@Controller(singleton = true)
public class ServiceDemoSingleton {
	
	private String tag;
	
	public ServiceDemoSingleton() {
		this.tag = "Singleton:" + this.toString();
	}
	
	@Service(name = "t1", url = "/single/t1", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String getSingletonT1(@Field(name = "name") String name) {
		return this.tag + ":" + name;
	}
	
	@Service(name = "t2", url = "/single/t2", method = {Http.HTTP_GET}, contentType = Http.TEXT_PLAIN)
	public String getSingletonT2() {
		return this.tag + ":" + new Date().toString();
	}
}
