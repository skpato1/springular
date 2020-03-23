package com.sifast.springular.framework.business.logic.web.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;
import com.sifast.springular.framework.business.logic.service.IProjectService;
import com.sifast.springular.framework.business.logic.web.service.api.IProjectGeneratorApi;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class ProjectGeneratorApi implements IProjectGeneratorApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseApi.class);

	private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	private Object httpResponseBody;

	private HttpStatus httpStatus;
	
	@Autowired
	private IJDLFileGeneratorService jDLFileGeneratorService;
	
	@Autowired
	private ICommandExecutorService commandExecutorService;
	
	@Autowired
	private IProjectService projectService;

	

	@Override
	public ResponseEntity<Object> generateProjectWithJdl(@PathVariable int id) throws IOException, InterruptedException {
		LOGGER.info("Web service generateProjectWithJdl invoked with projectDto {}", id);
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		Optional<Project> project = projectService.findById(id);
		if (project.isPresent()) {
			httpStatus = HttpStatus.OK;
			jDLFileGeneratorService.generateProjectWithJdl(project.get());
			commandExecutorService.executeJdlFromTerminal(isWindows);
			commandExecutorService.cloneSpringularFrameworkSocleFromGitlab(project.get(),isWindows);
 			commandExecutorService.copyEntitiesToGeneratedProject(project.get(),isWindows);
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.DATABASE_NOT_FOUND);
			httpStatus = HttpStatus.NOT_FOUND;
			httpResponseBody = httpErrorResponse;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);	}
	
	
	

}