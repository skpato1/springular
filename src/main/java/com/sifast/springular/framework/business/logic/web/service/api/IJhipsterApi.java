package com.sifast.springular.framework.business.logic.web.service.api;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IJhipsterApi {
	
	
	@RequestMapping(value = "/jhipster/{baseName}/{packageName}/{applicationType}/{serverPort}", method = RequestMethod.GET)
	ResponseEntity<Object> generateProjectWithJdl(String baseName,String packageName,String applicationType,String serverPort) throws IOException, InterruptedException;
	
	
	


}
