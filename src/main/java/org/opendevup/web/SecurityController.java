package org.opendevup.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	@RequestMapping("/all")
	public String hello(){
		
		return "";
	}
	
	@RequestMapping("/secured/all")
	public String hsecuredHello(){
		
		return "";
	}

}
