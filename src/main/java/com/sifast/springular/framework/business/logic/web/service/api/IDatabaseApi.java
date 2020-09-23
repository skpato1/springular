package com.sifast.springular.framework.business.logic.web.service.api;

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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface IDatabaseApi {

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.DATABASE_CREATED_SUCCESSFULLY, response = ViewDatabaseDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "create database", response = ViewProjectDto.class)
    @PostMapping(value = "/database")
    ResponseEntity<?> saveDatabase(CreateDatabaseDto databaseDto, BindingResult bindingResult);

    @GetMapping(value = "/database/{id}")
    ResponseEntity<?> getDatabase(int id);

    @GetMapping(value = "/databases")
    ResponseEntity<?> getAllDatabases();

    @DeleteMapping(value = "/database/{id}")
    ResponseEntity<?> deleteDatabase(int id);

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.DATABASE_UPDATED_SUCCESSFULLY, response = ViewDatabaseDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "update database", response = ViewProjectDto.class)
    @PutMapping(value = "/database/{id}")
    ResponseEntity<?> updateDatabase(int id, DatabaseDto databaseDto, BindingResult bindingResult);

}
