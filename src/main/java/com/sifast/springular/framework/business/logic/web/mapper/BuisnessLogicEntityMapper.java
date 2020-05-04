package com.sifast.springular.framework.business.logic.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;

@Component
public class BuisnessLogicEntityMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public BuisnessLogicEntity mapCreateBuisnessLogicEntity(CreateBuisnessLogicEntityDto buisnessLogicEntityDto) {
        BuisnessLogicEntity mappedBuisnessLogicEntity = modelMapper.map(buisnessLogicEntityDto, BuisnessLogicEntity.class);
        return mappedBuisnessLogicEntity;
    }

    public ViewBuisnessLogicEntityDto mapBuisnessLogicEntityToViewBuisnessLogicEntityDto(BuisnessLogicEntity buisnessLogicEntity) {
        ViewBuisnessLogicEntityDto viewBuisnessLogicEntityDto = modelMapper.map(buisnessLogicEntity, ViewBuisnessLogicEntityDto.class);
        return viewBuisnessLogicEntityDto;
    }

}
