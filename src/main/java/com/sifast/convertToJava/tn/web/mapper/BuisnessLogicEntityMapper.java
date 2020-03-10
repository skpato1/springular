package com.sifast.convertToJava.tn.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.sifast.convertToJava.tn.entities.BuisnessLogicEntity;
import com.sifast.convertToJava.tn.web.config.ConfiguredModelMapper;
import com.sifast.convertToJava.tn.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;
import com.sifast.convertToJava.tn.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;

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
