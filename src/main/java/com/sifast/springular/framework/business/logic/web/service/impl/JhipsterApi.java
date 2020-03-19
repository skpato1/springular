package com.sifast.springular.framework.business.logic.web.service.impl;

import java.io.IOException;

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
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;
import com.sifast.springular.framework.business.logic.service.IJhipsterService;
import com.sifast.springular.framework.business.logic.web.service.api.IJhipsterApi;

import io.swagger.annotations.ApiParam;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class JhipsterApi implements IJhipsterApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseApi.class);

	private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	private Object httpResponseBody;

	private HttpStatus httpStatus;
	
	@Autowired
	private IJhipsterService jhipsterService;
	
	@Autowired
	private ICommandExecutorService commandExecutorService;

	

	@Override
	public ResponseEntity<Object> generateProjectWithJdl(@ApiParam(value = "baseName of project that needs to be created", required = true, allowableValues = "range[1,infinity]") @PathVariable String baseName,@PathVariable String packageName,@PathVariable String applicationType,@PathVariable String serverPort) throws IOException, InterruptedException {
		LOGGER.info("Web service generateProjectWithJdl invoked with baseName {}", baseName);
		if (baseName != null) {
			httpStatus = HttpStatus.OK;
			jhipsterService.generateProjectWithJdl(baseName,packageName,applicationType,serverPort);
			commandExecutorService.executeJdlFromTerminal();
		} else {
			httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.DATABASE_NOT_FOUND);
			httpStatus = HttpStatus.NOT_FOUND;
			httpResponseBody = httpErrorResponse;
		}
		return new ResponseEntity<>(httpResponseBody, httpStatus);	}
	
	
	

}
