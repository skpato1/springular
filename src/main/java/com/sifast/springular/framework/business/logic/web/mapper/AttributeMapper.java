package com.sifast.springular.framework.business.logic.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.ViewAttributeDto;
@Component
public class AttributeMapper {
	
	
	@Autowired
    private ConfiguredModelMapper modelMapper;
	
	
	public Attribute mapCreateAttribute(CreateAttributeDto attributeDto) {
        Attribute mappedAttribute = modelMapper.map(attributeDto, Attribute.class);
        return mappedAttribute;
    }
	
	 public ViewAttributeDto mapAttributeToViewAttributeDto(Attribute attribute) {
	        ViewAttributeDto viewAttributeDto = modelMapper.map(attribute, ViewAttributeDto.class);
	        return viewAttributeDto;
	    }


}
