package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;



public interface IBuisnessLogicEntityApi {
	
	
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
