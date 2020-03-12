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
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.service.IBuisnessLogicEntityService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.mapper.BuisnessLogicEntityMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IBuisnessLogicEntityApi;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class BuisnessLogicEntityApi implements IBuisnessLogicEntityApi{
	
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(BuisnessLogicEntityApi.class);
	 
	    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();


	    private Object httpResponseBody;

	    private HttpStatus httpStatus;
	    
		 	@Autowired
		    private ConfiguredModelMapper modelMapper;

		    @Autowired
		    private BuisnessLogicEntityMapper buisnessLogicEntityMapper;
		    
		    @Autowired
		    private IBuisnessLogicEntityService buisnessLogicEntityService;

		
		
		@Override
		public ResponseEntity<Object> saveBuisnessLogicEntity(@RequestBody CreateBuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult) {
	        LOGGER.info("Web service saveBuisnessLogicEntity invoked with buisnessLogicEntityDto {}", buisnessLogicEntityDto);
	        try {
	        BuisnessLogicEntity savedBuisnessLogicEntity = buisnessLogicEntityService.save(buisnessLogicEntityMapper.mapCreateBuisnessLogicEntity(buisnessLogicEntityDto));
	        httpStatus = HttpStatus.OK;
	        httpResponseBody = modelMapper.map(savedBuisnessLogicEntity, ViewBuisnessLogicEntityDto.class);
	        }
	        catch (Exception e) {
	        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
	            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	        	httpResponseBody = httpErrorResponse;	        }
	        

	        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> getBuisnessLogicEntity(@PathVariable("id")int id) {
			LOGGER.info("Web service getBuisnessLogicEntity invoked with id {}", id);

	        Optional<BuisnessLogicEntity> buisnessLogicEntity = buisnessLogicEntityService.findById(id);
	        if (buisnessLogicEntity.isPresent()) {
	            httpStatus = HttpStatus.OK;
	            httpResponseBody = buisnessLogicEntityMapper.mapBuisnessLogicEntityToViewBuisnessLogicEntityDto(buisnessLogicEntity.get());
	        } else {
	        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND);
	        	httpStatus = HttpStatus.NOT_FOUND;
	        	httpResponseBody = httpErrorResponse;	        }
	        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> getAllBuisnessLogicEntitys() {
				List<BuisnessLogicEntity> buisnessLogicEntitys = buisnessLogicEntityService.findAll();
		        httpStatus = HttpStatus.OK;
		        httpResponseBody = !buisnessLogicEntitys.isEmpty() ? buisnessLogicEntitys.stream().map(BuisnessLogicEntity -> modelMapper.map(BuisnessLogicEntity, ViewBuisnessLogicEntityDto.class)).collect(Collectors.toList()) : Collections.emptyList();
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> deleteBuisnessLogicEntity(@PathVariable("id") int id) {
			LOGGER.info("Web service deleteBuisnessLogicEntity invoked with id {}", id);
			 Optional<BuisnessLogicEntity> preDeleteBuisnessLogicEntity = buisnessLogicEntityService.findById(id);
		        if (!preDeleteBuisnessLogicEntity.isPresent()) {
		            httpStatus = HttpStatus.NOT_FOUND;
		        } else {
		            
		                buisnessLogicEntityService.delete(preDeleteBuisnessLogicEntity.get());
		                httpStatus = HttpStatus.OK;
		                LOGGER.info("INFO level message: BuisnessLogicEntity with id = {} deleted ", id);
		            
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> updateBuisnessLogicEntity(@PathVariable("id") int id,@RequestBody BuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult) {
			LOGGER.info("Web service updateBuisnessLogicEntity invoked with id {}", id);
			if (!bindingResult.hasFieldErrors()) {
		            Optional<BuisnessLogicEntity> buisnessLogicEntity = buisnessLogicEntityService.findById(id);
		            if (buisnessLogicEntity.isPresent()) {
		                BuisnessLogicEntity preUpdateBuisnessLogicEntity = buisnessLogicEntity.get();
		                BuisnessLogicEntity updatedBuisnessLogicEntity = buisnessLogicEntityService.save(preUpdateBuisnessLogicEntity);
		                httpStatus = HttpStatus.OK;
		                httpResponseBody = modelMapper.map(buisnessLogicEntity, BuisnessLogicEntity.class);
		                LOGGER.info("INFO level message: BuisnessLogicEntity updated {}", updatedBuisnessLogicEntity);

		            } else {
		            	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND);
		                httpStatus = HttpStatus.NOT_FOUND;
			        	httpResponseBody = httpErrorResponse;
		            }
		        } else {
		            httpStatus = HttpStatus.BAD_REQUEST;
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}


}
