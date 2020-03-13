package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



public interface IBuisnessLogicEntityApi {
	
	@ApiResponses(value = {
			@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BUISNESS_LOGIC_ENTITY__CREATED_SUCCESSFULLY, response = ViewBuisnessLogicEntityDto.class),
			@ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
			@ApiOperation(value = "Update an existing client with ID", response = ViewProjectDto.class)
	@RequestMapping(value = "/buisnessLogicEntity", method = RequestMethod.POST)
	ResponseEntity<Object> saveBuisnessLogicEntity(CreateBuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult);

	@RequestMapping(value = "/buisnessLogicEntity/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getBuisnessLogicEntity(int id);

	@RequestMapping(value = "/buisnessLogicEntitys", method = RequestMethod.GET)
	ResponseEntity<Object> getAllBuisnessLogicEntitys();
	
	@RequestMapping(value = "/buisnessLogicEntity/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteBuisnessLogicEntity(int id);
	
	@RequestMapping(value = "/buisnessLogicEntity/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateBuisnessLogicEntity(int id,BuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult);

}
