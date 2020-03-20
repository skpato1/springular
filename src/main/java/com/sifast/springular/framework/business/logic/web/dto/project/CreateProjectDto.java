package com.sifast.springular.framework.business.logic.web.dto.project;

import java.util.List;

import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.CreateBuisnessLogicEntityDto;

public class CreateProjectDto extends ProjectDto{

	
	List<CreateBuisnessLogicEntityDto> buisnessDto;

	public List<CreateBuisnessLogicEntityDto> getBuisnessDto() {
		return buisnessDto;
	}

	public void setBuisnessDto(List<CreateBuisnessLogicEntityDto> buisnessDto) {
		this.buisnessDto = buisnessDto;
	}
	
}
