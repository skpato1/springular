package com.sifast.springular.framework.business.logic.web.mapper;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuisnessLogicEntityMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public BuisnessLogicEntity mapCreateBuisnessLogicEntity(CreateBuisnessLogicEntityDto buisnessLogicEntityDto) {
        return modelMapper.map(buisnessLogicEntityDto, BuisnessLogicEntity.class);
    }

    public BuisnessLogicEntity mapBuisnessLogicEntityDtoToEntity(BuisnessLogicEntityDto buisnessLogicEntityDto) {
        return modelMapper.map(buisnessLogicEntityDto, BuisnessLogicEntity.class);
    }

    public ViewBuisnessLogicEntityDto mapBuisnessLogicEntityToViewBuisnessLogicEntityDto(BuisnessLogicEntity buisnessLogicEntity) {
        return modelMapper.map(buisnessLogicEntity, ViewBuisnessLogicEntityDto.class);
    }

}
