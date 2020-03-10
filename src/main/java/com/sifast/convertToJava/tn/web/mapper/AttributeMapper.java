package com.sifast.convertToJava.tn.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.sifast.convertToJava.tn.entities.Attribute;
import com.sifast.convertToJava.tn.web.config.ConfiguredModelMapper;
import com.sifast.convertToJava.tn.web.dto.attribute.CreateAttributeDto;
import com.sifast.convertToJava.tn.web.dto.attribute.ViewAttributeDto;

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
