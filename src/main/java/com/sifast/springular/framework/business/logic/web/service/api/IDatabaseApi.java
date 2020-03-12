package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.DatabaseDto;


public interface IDatabaseApi {
	
	
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
