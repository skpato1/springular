package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.DatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


public interface IDatabaseApi {
	
	@ApiResponses(value = {
			@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.DATABASE_CREATED_SUCCESSFULLY, response = ViewDatabaseDto.class),
			@ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
			@ApiOperation(value = "create database", response = ViewProjectDto.class)
	@RequestMapping(value = "/database", method = RequestMethod.POST)
	ResponseEntity<Object> saveDatabase(CreateDatabaseDto databaseDto, BindingResult bindingResult);

	@RequestMapping(value = "/database/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getDatabase(int id);

	@RequestMapping(value = "/databases", method = RequestMethod.GET)
	ResponseEntity<Object> getAllDatabases();
	
	@RequestMapping(value = "/database/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteDatabase(int id);
	
	@RequestMapping(value = "/database/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateDatabase(int id,DatabaseDto databaseDto, BindingResult bindingResult);

}
