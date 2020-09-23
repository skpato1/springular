package com.sifast.springular.framework.business.logic.web.dto.project;

import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.CreateBuisnessLogicEntityDto;
import java.util.List;

public class CreateProjectDto extends ProjectDto {


    List<CreateBuisnessLogicEntityDto> buisnessDto;

    public List<CreateBuisnessLogicEntityDto> getBuisnessDto() {
        return buisnessDto;
    }

    public void setBuisnessDto(List<CreateBuisnessLogicEntityDto> buisnessDto) {
        this.buisnessDto = buisnessDto;
    }

}
