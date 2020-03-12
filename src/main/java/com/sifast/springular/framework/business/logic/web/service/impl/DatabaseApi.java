package com.sifast.springular.framework.business.logic.web.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.entities.Database;
import com.sifast.springular.framework.business.logic.service.IDatabaseService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.DatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;
import com.sifast.springular.framework.business.logic.web.mapper.DatabaseMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IDatabaseApi;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class DatabaseApi implements IDatabaseApi{
	
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseApi.class);

	    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	    private Object httpResponseBody;

	    private HttpStatus httpStatus;
	    
		 	@Autowired
		    private ConfiguredModelMapper modelMapper;

		    @Autowired
		    private DatabaseMapper databaseMapper;
		    
		    @Autowired
		    private IDatabaseService databaseService;

		
		
		@Override
		public ResponseEntity<Object> saveDatabase(@RequestBody CreateDatabaseDto databaseDto, BindingResult bindingResult) {
	        LOGGER.info("Web service saveDatabase invoked with databaseDto {}", databaseDto);
	        try {
	        Database saveddatabase = databaseService.save(databaseMapper.mapCreateDatabase(databaseDto));
	        httpStatus = HttpStatus.OK;
	        httpResponseBody = modelMapper.map(saveddatabase, ViewDatabaseDto.class);
	        }
	        catch (Exception e) {
	        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
	            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	        	httpResponseBody = httpErrorResponse;

	        }
	        

	        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> getDatabase(@PathVariable("id") int id) {
			LOGGER.info("Web service getDatabase invoked with id {}", id);

	        Optional<Database> database = databaseService.findById(id);
	        if (database.isPresent()) {
	            httpStatus = HttpStatus.OK;
	            httpResponseBody = databaseMapper.mapDatabaseToViewDatabaseDto(database.get());
	        } else {
	        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.DATABASE_NOT_FOUND);
	        	httpStatus = HttpStatus.NOT_FOUND;
	        	httpResponseBody = httpErrorResponse;	        }
	        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> getAllDatabases() {
				List<Database> databases = databaseService.findAll();
		        httpStatus = HttpStatus.OK;
		        httpResponseBody = !databases.isEmpty() ? databases.stream().map(database -> modelMapper.map(database, ViewDatabaseDto.class)).collect(Collectors.toList()) : Collections.emptyList();
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> deleteDatabase(@PathVariable("id") int id) {
			LOGGER.info("Web service deleteDatabase invoked with id {}", id);
			 Optional<Database> preDeletedatabase = databaseService.findById(id);
		        if (!preDeletedatabase.isPresent()) {
		            httpStatus = HttpStatus.NOT_FOUND;
		        } else {
		            
		                databaseService.delete(preDeletedatabase.get());
		                httpStatus = HttpStatus.OK;
		                LOGGER.info("INFO level message: database with id = {} deleted ", id);
		            
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> updateDatabase(@PathVariable("id") int id,@RequestBody DatabaseDto databaseDto, BindingResult bindingResult) {
			LOGGER.info("Web service updateDatabase invoked with id {}", id);
			if (!bindingResult.hasFieldErrors()) {
		            Optional<Database> database = databaseService.findById(id);
		            if (database.isPresent()) {
		                Database preUpdatedatabase = database.get();
		                Database updateddatabase = databaseService.save(preUpdatedatabase);
		                httpStatus = HttpStatus.OK;
		                httpResponseBody = modelMapper.map(database, Database.class);
		                LOGGER.info("INFO level message: database updated {}", updateddatabase);

		            } else {
			        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.DATABASE_NOT_FOUND);
		                httpStatus = HttpStatus.NOT_FOUND;
			        	httpResponseBody = httpErrorResponse;	        
		            }
		        } else {
		            httpStatus = HttpStatus.BAD_REQUEST;
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}


}
