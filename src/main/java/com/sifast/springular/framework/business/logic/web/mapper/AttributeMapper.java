package com.sifast.springular.framework.business.logic.web.mapper;

import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.ViewAttributeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttributeMapper {


    @Autowired
    private ConfiguredModelMapper modelMapper;


    public Attribute mapCreateAttribute(CreateAttributeDto attributeDto) {
        return modelMapper.map(attributeDto, Attribute.class);
    }

    public ViewAttributeDto mapAttributeToViewAttributeDto(Attribute attribute) {
        return modelMapper.map(attribute, ViewAttributeDto.class);
    }


}
