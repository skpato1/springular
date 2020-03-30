package com.sifast.springular.framework.business.logic.web.service.api;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IProjectGeneratorApi {
	
	
	@RequestMapping(value = "/jhipster/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> generateProjectWithJdl(int id) throws IOException, InterruptedException;
	
	@RequestMapping(value = "/jhipster/zip/{id}", produces="application/zip" , method = RequestMethod.GET)
	ResponseEntity<Object> downloadZipProject(int id) throws FileNotFoundException, IOException;
}
