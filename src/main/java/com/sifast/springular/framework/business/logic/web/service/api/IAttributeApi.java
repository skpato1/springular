package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;


public interface IAttributeApi {
	
	@RequestMapping(value = "/attribute", method = RequestMethod.POST)
	ResponseEntity<Object> saveAttribute(CreateAttributeDto attributeDto, BindingResult bindingResult);

	@RequestMapping(value = "/attribute/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getAttribute(int id);

	@RequestMapping(value = "/attributes", method = RequestMethod.GET)
	ResponseEntity<Object> getAllAttributes();
	
	@RequestMapping(value = "/attribute/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteAttribute(int id);
	
	@RequestMapping(value = "/attribute/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateAttribute(int id,AttributeDto attributeDto, BindingResult bindingResult);

}
