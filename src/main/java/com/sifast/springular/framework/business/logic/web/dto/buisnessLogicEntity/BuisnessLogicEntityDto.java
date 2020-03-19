package com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;

public class BuisnessLogicEntityDto {

	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameEntity;

	private List<AttributeDto> attributes;

	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private int project_id;

	public String getNameEntity() {
		return nameEntity;
	}

	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}

	public List<AttributeDto> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeDto> attributes) {
		this.attributes = attributes;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	@Override
	public String toString() {
		return "BuisnessLogicEntityDto [nameEntity=" + nameEntity + "]";
	}

}
