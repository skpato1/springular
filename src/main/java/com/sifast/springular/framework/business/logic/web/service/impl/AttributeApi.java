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
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.service.IAttributeService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.ViewAttributeDto;
import com.sifast.springular.framework.business.logic.web.mapper.AttributeMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IAttributeApi;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class AttributeApi implements IAttributeApi {

	 private static final Logger LOGGER = LoggerFactory.getLogger(AttributeApi.class);

	    private Object httpResponseBody;

	    private HttpStatus httpStatus;
	    
	    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

	    
		 	@Autowired
		    private ConfiguredModelMapper modelMapper;

		    @Autowired
		    private AttributeMapper attributeMapper;
		    
		    @Autowired
		    private IAttributeService attributeService;


		@Override
		public ResponseEntity<Object> getAttribute(@PathVariable("id") int id) {
			LOGGER.info("Web service getattribute invoked with id {}", id);

	        Optional<Attribute> attribute = attributeService.findById(id);
	        if (attribute.isPresent()) {
	            httpStatus = HttpStatus.OK;
	            httpResponseBody = attributeMapper.mapAttributeToViewAttributeDto(attribute.get());
	        } else {
	        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.ATTRIBUTE_NOT_FOUND);
	        	httpStatus = HttpStatus.NOT_FOUND;
	        	httpResponseBody = httpErrorResponse;	        }
	        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> getAllAttributes() {
				List<Attribute> attributes = attributeService.findAll();
		        httpStatus = HttpStatus.OK;
		        httpResponseBody = !attributes.isEmpty() ? attributes.stream().map(attribute -> modelMapper.map(attribute, ViewAttributeDto.class)).collect(Collectors.toList()) : Collections.emptyList();
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> deleteAttribute(@PathVariable("id") int id) {
			LOGGER.info("Web service deleteattribute invoked with id {}", id);
			 Optional<Attribute> preDeleteattribute = attributeService.findById(id);
		        if (!preDeleteattribute.isPresent()) {
		        	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.ATTRIBUTE_NOT_FOUND);
	                httpStatus = HttpStatus.NOT_FOUND;
		        	httpResponseBody = httpErrorResponse;	  
		        } else {
		            
		                attributeService.delete(preDeleteattribute.get());
		                httpStatus = HttpStatus.OK;
		                LOGGER.info("INFO level message: attribute with id = {} deleted ", id);
		            
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> updateAttribute(@PathVariable("id") int id,@RequestBody AttributeDto attributeDto, BindingResult bindingResult) {
			LOGGER.info("Web service updateattribute invoked with id {}", id);
			if (!bindingResult.hasFieldErrors()) {
		            Optional<Attribute> attribute = attributeService.findById(id);
		            if (attribute.isPresent()) {
		            	Attribute preUpdateattribute = attribute.get();
		            	Attribute updatedAttribute = attributeService.save(preUpdateattribute);
		                httpStatus = HttpStatus.OK;
		                httpResponseBody = modelMapper.map(attribute, Attribute.class);
		                LOGGER.info("INFO level message: attribute updated {}", updatedAttribute);

		            } else {
		            	httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.ATTRIBUTE_NOT_FOUND);
		                httpStatus = HttpStatus.NOT_FOUND;
			        	httpResponseBody = httpErrorResponse;
		            }
		        } else {
		            httpStatus = HttpStatus.BAD_REQUEST;
		        }
		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}

		@Override
		public ResponseEntity<Object> saveAttribute(@RequestBody CreateAttributeDto attributeDto, BindingResult bindingResult) {
			 LOGGER.info("Web service saveattribute invoked with AttributeDto {}", attributeDto);
		        try {
		        Attribute savedattribute = attributeService.save(attributeMapper.mapCreateAttribute(attributeDto));
		        httpStatus = HttpStatus.OK;
		        httpResponseBody = modelMapper.map(savedattribute, ViewAttributeDto.class);
		        }
		        catch (Exception e) {
		            httpStatus = HttpStatus.BAD_REQUEST;
		        }
		        

		        return new ResponseEntity<>(httpResponseBody, httpStatus);
		}
	
	

}